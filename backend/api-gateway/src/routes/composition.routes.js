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
			const gerenteId = req.user?.id || 'Sistema';
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
	//GET /api/melhores-clientes
	//GET /api/relatorio-clientes
	//GET /api/consultar-clientes
	//GET /api/consultar-extrato (consulta de extrato com query params dataInicio/dataFim)
	//GET /api/dashboard-admin

	return router;
}

export { compositionRouter };