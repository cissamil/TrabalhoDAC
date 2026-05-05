// recebe requisicoes do cliente e encaminha para o microservico correto.

import express from 'express';
import dotenv from 'dotenv';
import morgan from 'morgan';
import cors from 'cors';
import jwt from 'jsonwebtoken';
import { createProxyMiddleware } from 'http-proxy-middleware';


// Carrega as variaveis de ambiente
dotenv.config();


// INICIALIZA EXPRESS E PORTA

const app = express();
const port = Number(process.env.PORT || 8080);


// enderecos dos ms
const services = {
	auth: process.env.AUTH_SERVICE_URL || 'http://localhost:8081',
	cliente: process.env.CLIENTE_SERVICE_URL || 'http://localhost:8082',
	conta: process.env.CONTA_SERVICE_URL || 'http://localhost:8083',
	gerente: process.env.GERENTE_SERVICE_URL || 'http://localhost:8084',
	saga: process.env.SAGA_SERVICE_URL || 'http://localhost:8085'
};

//tratamento de erro
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


// MIDDLEWARE 1: Habilita CORS
app.use(cors({
	origin: [
		'http://localhost:4200',  
		'http://127.0.0.1:4200'
	
	],
	credentials: true,
	methods: ['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS'],
	allowedHeaders: ['Content-Type', 'Authorization']
}));

// MIDDLEWARE 2: Aceita JSON nas requisições
app.use(express.json());

// MIDDLEWARE 3: Log de requisições
app.use(morgan('dev'));


// ----------------- PROXY (ENCAMINHAMENTO) -----------------------------------//

// ROTA 1
// JWT 
function verifyJWT(req, res, next) {
	if (req.method === 'OPTIONS') return next(); 

	const authHeader = req.headers['authorization'] || req.headers['Authorization'];
	if (!authHeader || typeof authHeader !== 'string' || !authHeader.startsWith('Bearer ')) {
		return res.status(401).json({ error: 'Unauthorized', message: 'Token ausente' });
	}

	const token = authHeader.split(' ')[1];
	try {
		const secret = process.env.JWT_SECRET || 'changeme';
		const payload = jwt.verify(token, secret);
		req.user = payload;
		return next();
	} catch (err) {
		return res.status(401).json({ error: 'Unauthorized', message: 'Token inválido' });
	}
}

function requireRole(allowedRoles) {
	return (req, res, next) => {
		if (!allowedRoles || allowedRoles.length === 0) return next();
		const user = req.user;
		if (!user) return res.status(403).json({ error: 'Forbidden', message: 'Usuário não autenticado' });

			// ele aceita esses formatos de payload JWT:
			//  { role: 'GERENTE' }
			//  { roles: ['GERENTE'] }
			//  { tipoUsuario: 'GERENTE' } = ms-auth
			let userRoles = [];
			if (user.roles && Array.isArray(user.roles)) userRoles = user.roles;
			else if (user.role) userRoles = Array.isArray(user.role) ? user.role : [user.role];
			else if (user.tipoUsuario) userRoles = [user.tipoUsuario];

			// normalizar para string simples e comparar em maiúsculas
			const ok = userRoles.some(r => allowedRoles.includes(String(r).toUpperCase()));
		if (!ok) return res.status(403).json({ error: 'Forbidden', message: 'Permissão insuficiente' });
		return next();
	};
}

// mapeamento de roles por rota. ms-auth usa TipoUsuario { CLIENTE, GERENTE, ADMIN }
const routeRoles = {
	'/cliente': ['CLIENTE', 'GERENTE', 'ADMIN'],
	'/conta': ['CLIENTE', 'GERENTE', 'ADMIN'],
	'/gerente': ['GERENTE', 'ADMIN'],
	'/saga': ['ADMIN']
};

// ROTA 1 (auth não exige token)
app.use('/auth', createServiceProxy(services.auth, 'auth'));

// ROTA 2 - proteger com JWT
app.use('/cliente', verifyJWT, requireRole(routeRoles['/cliente']), createServiceProxy(services.cliente, 'cliente'));

// ROTA 3
app.use('/conta', verifyJWT, requireRole(routeRoles['/conta']), createServiceProxy(services.conta, 'conta'));

// ROTA 4
app.use('/gerente', verifyJWT, requireRole(routeRoles['/gerente']), createServiceProxy(services.gerente, 'gerente'));

// ROTA 5
app.use('/saga', verifyJWT, requireRole(routeRoles['/saga']), createServiceProxy(services.saga, 'saga'));


// GET /health, retorna status do gateway
app.get('/health', (req, res) => {
	res.json({
		status: 'Gateway ta funcionando!',
		porta: port,
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


app.listen(port, () => {
	console.log(`\n API Gateway rodando em http://localhost:${port}`);
	console.log(` Health check: http://localhost:${port}/health\n`);
});

