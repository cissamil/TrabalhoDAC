import express from 'express';
import { createProxyRoute } from '../utils/proxy.js';
import { verifyJWT } from '../middlewares/auth.js';
import { requireRole, routeRoles } from '../middlewares/roles.js';

function clienteRouter(services) {
	const router = express.Router();

	router.post('/autocadastro', createProxyRoute({
		target: services.clienteService,
		proxyTimeout: 5000,
		timeout: 5000,
		errorMessage: '[Gateway] Erro no autocadastro:'
	}));

	router.post('/lista-clientes-por-id', verifyJWT, requireRole(routeRoles['/cliente']), createProxyRoute({
		target: services.clienteService,
		errorMessage: '[Gateway] Erro em buscar cliente por id:'
	}));

	router.get('/:clienteId', verifyJWT, requireRole(routeRoles['/cliente']), createProxyRoute({
		target: services.clienteService,
		errorMessage: '[Gateway] Erro na busca do cliente:'
	}));

	// PUT /api/clientes/{clienteId} atualizar
	//GET /api/clientes  listar clientes

	return router;
}

export { clienteRouter };