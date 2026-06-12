import { createProxyMiddleware } from 'http-proxy-middleware';

function forwardJsonBody(proxyReq, req) {
	if (req.body && Object.keys(req.body).length) { //tem body e nao esta vazio
		const bodyData = JSON.stringify(req.body); //converte o objeto js de volta para string json
		proxyReq.setHeader('Content-Type', 'application/json');
		proxyReq.setHeader('Content-Length', Buffer.byteLength(bodyData));
		proxyReq.write(bodyData); //reescreve o body na req pro ms
	}
}

function createProxyRoute({ target, errorMessage, pathRewrite, proxyTimeout, timeout, onProxyReq }) { //recebe as configuracoes e cria o middleware de proxy
	return createProxyMiddleware({
		target, //url do ms de destino
		changeOrigin: true,  //muda o header hos para url do ms
		pathRewrite, //reescreve o caminho se necessario
		proxyTimeout, 
		timeout,
		on: {
			proxyReq: (proxyReq, req, res) => {
				if (onProxyReq) {
					onProxyReq(proxyReq, req, res);
				}

				forwardJsonBody(proxyReq, req);
			},
			error: (err, req, res) => {
				console.error(errorMessage, err.message);

				if (!res.headersSent) {
					res.status(502).json({ error: 'Bad Gateway' }); //erro de comunicacao entre os servicos
				}
			}
		}
	});
}

export { createProxyRoute };