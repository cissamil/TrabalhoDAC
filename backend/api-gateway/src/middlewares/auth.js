import jwt from 'jsonwebtoken';

function verifyJWT(req, res, next) {
	if (req.method === 'OPTIONS') return next();

	const authHeader = req.headers['authorization'] || req.headers['Authorization'];
	if (!authHeader || typeof authHeader !== 'string' || !authHeader.startsWith('Bearer ')) {
		return res.status(401).json({ error: 'Unauthorized', message: 'Token ausente' });
	}

	const token = authHeader.split(' ')[1];

	try {
		const secret = process.env.JWT_SECRET;
		if (!secret) throw new Error('o JWT_SECRET não definido!!!!!');

		const payload = jwt.verify(token, secret);
		req.user = payload;
		return next();
	} catch (err) {
		return res.status(401).json({ error: 'Unauthorized', message: 'Token inválido' });
	}
}

export { verifyJWT };