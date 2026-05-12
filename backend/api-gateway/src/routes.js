import jwt from 'jsonwebtoken';
import { createProxyMiddleware } from 'http-proxy-middleware';

//Encaminha requisicoes para ms
function createServiceProxy(target, routePrefix) {
	return createProxyMiddleware({
		target,
		changeOrigin: true,
		pathRewrite: { [`^/${routePrefix}`]: '' },
		on: {
			error: (err, req, res) => {
				console.error(`[Gateway] Erro ao encaminhar ${req.method} ${req.originalUrl}:`, err.message);

				if (!res.headersSent) {
					res.status(502).json({
						error: 'Bad Gateway',
						message: `Falha ao comunicar com o microsservico ${routePrefix}`
					});
				}
			}
		}
	});
}

// JWT
function verifyJWT(req, res, next) {
	if (req.method === 'OPTIONS') return next();

	const authHeader = req.headers['authorization'] || req.headers['Authorization'];
	if (!authHeader || typeof authHeader !== 'string' || !authHeader.startsWith('Bearer ')) {
		return res.status(401).json({ error: 'Unauthorized', message: 'Token ausente' });
	}

	const token = authHeader.split(' ')[1];
	try {
		const secret = process.env.JWT_SECRET;
		if (!secret) throw new Error('o JWT_SECRET não definido!!!!!');
		const payload = jwt.verify(token, secret);
		req.user = payload;
		return next();
	} catch (err) {
		return res.status(401).json({ error: 'Unauthorized', message: 'Token inválido' });
	}
}

//ROLES
function requireRole(allowedRoles) {
	
	function parseUserRoles(user) {
		if (!user) return [];
		if (Array.isArray(user.roles)) return user.roles.map(String);
		if (user.role) return (Array.isArray(user.role) ? user.role : [user.role]).map(String);
		if (user.tipoUsuario) return [String(user.tipoUsuario)];
		return [];
	}

	//valida se o usuário possui pelo menos uma role permitida
	return (req, res, next) => {
		if (!allowedRoles || allowedRoles.length === 0) return next();

		const user = req.user;
		if (!user) {
			return res.status(403).json({ error: 'Forbidden', message: 'Usuário não autenticado' });
		}

		const userRoles = parseUserRoles(user).map(r => String(r).toUpperCase());
		const allowed = allowedRoles.map(r => String(r).toUpperCase());

		const hasPermission = userRoles.some(r => allowed.includes(r));
		if (!hasPermission) {
			return res.status(403).json({ error: 'Forbidden', message: 'Permissão insuficiente' });
		}

		return next();
	};
}

function registerRoutes(app, services) {
	const routeRoles = {
		'/cliente': ['CLIENTE', 'GERENTE', 'ADMIN'],
		'/conta': ['CLIENTE', 'GERENTE', 'ADMIN'],
		'/gerente': ['GERENTE', 'ADMIN'],
		'/saga': ['ADMIN']
	};

	//------------------------------------------- Rotas ------------------------------------------------------//

	/* ROTA AUTOCADASTRO */
	app.post('/clientes/autocadastro', createProxyMiddleware({
		target: services.clienteService,
		changeOrigin: true,
		proxyTimeout: 5000,
		timeout: 5000,
		pathRewrite: {'^/clientes/autocadastro' : '/autocadastro'},
		on: {
			proxyReq: (proxyReq, req, res) => {
				if (req.body && Object.keys(req.body).length) {
					const bodyData = JSON.stringify(req.body);
					proxyReq.setHeader('Content-Length', Buffer.byteLength(bodyData));
					proxyReq.write(bodyData);
				}
			},
			error: (err, req, res) => {
				console.error(`Rota: ${req} [GATEWAY] Erro no autocadastro:`, err.message);
				res.status(502).json({ error: 'Bad Gateway' });
			}
		},
	}));

	/*ROTA CONTAS-PENDENTES*/
	app.get('/contas-pendentes', verifyJWT, (req, res, next) => {
		const gerenteId = req.user?.id || 'Sistema';

		return createProxyMiddleware({
			target: services.compositionService,
			changeOrigin: true,
			pathRewrite: {'^/contas-pendentes': '/contas-pendentes'},
			on: {
				proxyReq: (proxyReq, req, res) => {
					// 1. Repassando ou Criando o Header customizado
					proxyReq.setHeader('X-Gerente-Id', gerenteId);

					// 2. Não esqueça do tratamento do Body que fizemos antes!
					if (req.body && Object.keys(req.body).length) {
						const bodyData = JSON.stringify(req.body);
						proxyReq.setHeader('Content-Type', 'application/json');
						proxyReq.setHeader('Content-Length', Buffer.byteLength(bodyData));
						proxyReq.write(bodyData);
					}
				},
				error: (err, req, res) => {
					console.error(`Rota: ${req} [GATEWAY] Erro no pedido de contas:`, err.message);
					res.status(502).json({ error: 'Bad Gateway' });
				}
			}
		})(req, res, next);
	});

	/* APROVAR CONTA */
    app.post('/contas/:id/aprovar', verifyJWT, requireRole(routeRoles['/gerente']), (req, res, next) => {
        
        return createProxyMiddleware({
        target: services.contaService,
        changeOrigin: true,
        pathRewrite: {'^/contas': ''},
        on: {
			proxyReq: (proxyReq, req, res) => {
				if (req.body && Object.keys(req.body).length) {
					const bodyData = JSON.stringify(req.body);
					proxyReq.setHeader('Content-Length', Buffer.byteLength(bodyData));
					proxyReq.write(bodyData);
				}
			},
			error: (err, req, res) => {
				console.error(`Rota: ${req} [GATEWAY] Erro na aprovação:`, err.message);
				res.status(502).json({ error: 'Bad Gateway' });
			}
		},

    })(req, res, next);

});

	/* LOGAR COM A CONTA */
    app.post('/auth/login', createProxyMiddleware({
        target: services.authService,
        changeOrigin: true,
        pathRewrite: {'^/auth/login': '/login'},
        on: {
			proxyReq: (proxyReq, req, res) => {
				if (req.body && Object.keys(req.body).length) {
					const bodyData = JSON.stringify(req.body);
					proxyReq.setHeader('Content-Length', Buffer.byteLength(bodyData));
					proxyReq.write(bodyData);
				}
			},
			error: (err, req, res) => {
				console.error(`Rota: ${req} [GATEWAY] Erro ao logar:`, err.message);
				res.status(502).json({ error: 'Bad Gateway' });
			}
		},

    }));

	/* REJEITAR CONTA */
	/* BUSCAR CONTA POR CLIENTE */
	/* BUSCAR GERENTE */
	/* BUSCAR CLIENTE */
	/* LISTAR CLIENTES */
	/* CLIENTE CONTA */
	
	//----------------------------------------------- Tratativas -------------------------------------------------//
	// GET /health
	app.get('/health', (req, res) => {
		res.json({
			status: 'Gateway ta funcionando!',
			porta: Number(process.env.PORT),
			serviços: services
		});
	});

	app.use((req, res) => {
		res.status(404).json({
			error: 'Not Found',
			message: `Rota nao encontrada: ${req.method} ${req.originalUrl}`
		});
	});

	app.use((err, req, res, next) => {
		console.error('[Gateway] Erro interno:', err);

		if (res.headersSent) {
			return next(err);
		}

		res.status(500).json({
			error: 'Internal Server Error',
			message: 'Erro interno no API Gateway'
		});
	});

	return { createServiceProxy, verifyJWT, requireRole, routeRoles };
}

export { registerRoutes };