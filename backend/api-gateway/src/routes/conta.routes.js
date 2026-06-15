import express from 'express';
import { createProxyRoute } from '../utils/proxy.js';
import { verifyJWT } from '../middlewares/auth.js';
import { requireRole, routeRoles } from '../middlewares/roles.js';

function contaRouter(services) {
	const router = express.Router();

	router.get('/aprovadas', verifyJWT, requireRole(routeRoles['/conta']), createProxyRoute({
		target: services.contaService,
		errorMessage: '[Gateway] Erro na busca de contas aprovadas:'
	}));

router.get('/', verifyJWT, requireRole(routeRoles['/gerente']), createProxyRoute({
		target: services.contaService,
		errorMessage: '[Gateway] Erro na busca de conta por clienteId:'
		
}));

	router.post('/:id/aprovar', verifyJWT, requireRole(routeRoles['/gerente']), createProxyRoute({
		target: services.contaService,
		errorMessage: '[Gateway] Erro na aprovação:'
	}));

	router.post('/:id/rejeitar', verifyJWT, requireRole(routeRoles['/gerente']), createProxyRoute({
		target: services.contaService,
		errorMessage: '[Gateway] Erro na rejeição:'
	}));

	router.post('/depositar', verifyJWT, requireRole(routeRoles['/conta']), createProxyRoute({
		target: services.contaService,
		errorMessage: '[Gateway] Erro em depositar:'
	}));

	router.post('/sacar', verifyJWT, requireRole(routeRoles['/conta']), createProxyRoute({
		target: services.contaService,
		errorMessage: '[Gateway] Erro em sacar:'
	}));

	router.post('/transferir', verifyJWT, requireRole(routeRoles['/conta']), createProxyRoute({
		target: services.contaService,
		errorMessage: '[Gateway] Erro em trasnferir:'
	}));


	return router;
}

export { contaRouter };