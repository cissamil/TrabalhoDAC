import express from 'express';
import { createProxyRoute } from '../utils/proxy.js';

function authRouter(services) {
	const router = express.Router();

	router.post('/login', createProxyRoute({
		target: services.authService,
		errorMessage: '[Gateway] Erro ao logar:'
	}));

	return router;
}

export { authRouter };