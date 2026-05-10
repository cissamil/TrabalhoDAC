import express from 'express';
import { port, services, setupMiddlewares } from './config.js';
import { registerRoutes } from './routes.js';

// Criar instancia do servidor express
const app = express();

setupMiddlewares(app);
registerRoutes(app, services);

app.listen(port, () => {
	console.log(`\n API Gateway rodando em http://localhost:${port}`);
	console.log(` Health check: http://localhost:${port}/health\n`);
});

