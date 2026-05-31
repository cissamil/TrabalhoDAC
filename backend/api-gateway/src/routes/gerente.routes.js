import express from 'express';
import { createProxyRoute } from '../utils/proxy.js';
import { verifyJWT } from '../middlewares/auth.js';
import { requireRole, routeRoles } from '../middlewares/roles.js';

function gerenteRouter(services) {
	const router = express.Router();

	router.get('/:gerenteId', verifyJWT, requireRole(routeRoles['/gerente']), createProxyRoute({
		target: services.gerenteService,
		errorMessage: '[Gateway] Erro na busca do gerente'
	}));

	return router;
}

export { gerenteRouter };