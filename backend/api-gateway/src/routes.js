import { authRouter } from './routes/auth.routes.js';
import { clienteRouter } from './routes/cliente.routes.js';
import { contaRouter } from './routes/conta.routes.js';
import { gerenteRouter } from './routes/gerente.routes.js';
import { compositionRouter } from './routes/composition.routes.js';
import { interpreters} from './config.js';

function registerRoutes(app, services) {
	app.use(interpreters.authInterpreter, authRouter(services));
	app.use(interpreters.clienteInterpreter, clienteRouter(services));
	app.use(interpreters.contaInterpreter, contaRouter(services));
	app.use(interpreters.gerenteInterpreter, gerenteRouter(services));
	app.use(interpreters.compositionInterpreter, compositionRouter(services));

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
}

export { registerRoutes };