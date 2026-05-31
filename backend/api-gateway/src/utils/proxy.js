import { createProxyMiddleware } from 'http-proxy-middleware';

function forwardJsonBody(proxyReq, req) {
	if (req.body && Object.keys(req.body).length) {
		const bodyData = JSON.stringify(req.body);
		proxyReq.setHeader('Content-Type', 'application/json');
		proxyReq.setHeader('Content-Length', Buffer.byteLength(bodyData));
		proxyReq.write(bodyData);
	}
}

function createProxyRoute({ target, errorMessage, pathRewrite, proxyTimeout, timeout, onProxyReq }) {
	return createProxyMiddleware({
		target,
		changeOrigin: true,
		pathRewrite,
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
					res.status(502).json({ error: 'Bad Gateway' });
				}
			}
		}
	});
}

export { createProxyRoute };