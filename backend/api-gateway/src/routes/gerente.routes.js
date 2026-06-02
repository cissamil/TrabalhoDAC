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

	// GET /api/gerentes — listar gerentes 
	// POST /api/gerentes/adicionar-gerente
	// POST /api/gerentes/remover-gerente
	// POST /api/gerentes/lista-gerentes-por-id

	return router;
}

export { gerenteRouter };