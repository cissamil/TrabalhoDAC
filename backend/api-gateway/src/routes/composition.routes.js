import express from 'express';
import { createProxyRoute } from '../utils/proxy.js';
import { verifyJWT } from '../middlewares/auth.js';
import { requireRole, routeRoles } from '../middlewares/roles.js';

function compositionRouter(services) {
	const router = express.Router();

	router.get('/contas-pendentes', verifyJWT, createProxyRoute({
		target: services.compositionService,
		errorMessage: '[Gateway] Erro no pedido de contas:',
		onProxyReq: (proxyReq, req) => {
			const gerenteId = req.user?.sub || 'Sistema';
			proxyReq.setHeader('X-Gerente-Id', gerenteId);
		}
	}));

	router.get('/cliente-conta', verifyJWT, requireRole(routeRoles['/cliente']), createProxyRoute({
		target: services.compositionService,
		errorMessage: '[Gateway] Erro na busca do perfil e conta do cliente ',
		onProxyReq: (proxyReq, req) => {
			const clienteId = req.user?.sub || 'Sistema';
			proxyReq.setHeader('X-Cliente-Id', clienteId);
		}
	}));

	router.get('/melhores-clientes', createProxyRoute({
		target: services.compositionService,
		errorMessage: '[Gateway] Erro ao buscar melhores clientes:'
	}));

	router.get('/relatorio-clientes', createProxyRoute({
		target: services.compositionService,
		errorMessage: '[Gateway] Erro ao buscar relatório de clientes:'
	}));


	router.get('/consultar-clientes', verifyJWT, requireRole(routeRoles['/gerente']), createProxyRoute({
		target: services.compositionService,
		errorMessage: '[Gateway] Erro ao consultar clientes:',
		onProxyReq: (proxyReq, req) => {
			const gerenteId = req.user?.id || 'Sistema';
			proxyReq.setHeader('X-Gerente-Id', gerenteId);
		}
	}));

	router.get('/consultar-extrato', verifyJWT, requireRole(routeRoles['/cliente']), createProxyRoute({
		target: services.compositionService,
		errorMessage: '[Gateway] Erro ao consultar extrato:',
		onProxyReq: (proxyReq, req) => {
			const clienteId = req.user?.sub || 'Sistema';
			proxyReq.setHeader('X-Cliente-Id', clienteId);
		}
	}));

	router.get('/dashboard-admin', verifyJWT, requireRole(routeRoles['/saga']), createProxyRoute({
		target: services.compositionService,
		errorMessage: '[Gateway] Erro ao buscar dashboard admin:'
	}));

	return router;
}

export { compositionRouter };