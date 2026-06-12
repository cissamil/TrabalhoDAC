import express from 'express';
import { createProxyRoute } from '../utils/proxy.js';
import { verifyJWT } from '../middlewares/auth.js';
import { requireRole, routeRoles } from '../middlewares/roles.js';

function gerenteRouter(services) {
	const router = express.Router();

	router.get('/', verifyJWT, requireRole(routeRoles['/gerente']), createProxyRoute({
		target: services.gerenteService,
		errorMessage: '[Gateway] Erro na busca dos gerentes'
	}));

	router.post('/adicionar-gerente', verifyJWT, requireRole(routeRoles['/gerente']), createProxyRoute({
		target: services.gerenteService,
		errorMessage: '[Gateway] Erro ao adicionar gerente:'
	}));

	router.post('/remover-gerente', verifyJWT, requireRole(routeRoles['/gerente']), createProxyRoute({
		target: services.gerenteService,
		errorMessage: '[Gateway] Erro ao remover gerente:'
	}));

	return router;
}

export { gerenteRouter };