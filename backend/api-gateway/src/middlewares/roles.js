function parseUserRoles(user) {
	if (!user) return [];
	if (Array.isArray(user.roles)) return user.roles.map(String);
	if (user.role) return (Array.isArray(user.role) ? user.role : [user.role]).map(String);
	if (user.tipoUsuario) return [String(user.tipoUsuario)];
	return [];
}

function requireRole(allowedRoles) {
	return (req, res, next) => {
		if (!allowedRoles || allowedRoles.length === 0) return next();

		const user = req.user;
		if (!user) {
			return res.status(403).json({ error: 'Forbidden', message: 'Usuário não autenticado' });
		}

		const userRoles = parseUserRoles(user).map(r => String(r).toUpperCase());
		const allowed = allowedRoles.map(r => String(r).toUpperCase());

		const hasPermission = userRoles.some(r => allowed.includes(r));
		if (!hasPermission) {
			return res.status(403).json({ error: 'Forbidden', message: 'Permissão insuficiente' });
		}

		return next();
	};
}

const routeRoles = {
	'/cliente': ['CLIENTE', 'GERENTE', 'ADMIN'],
	'/conta': ['CLIENTE', 'GERENTE', 'ADMIN'],
	'/gerente': ['GERENTE', 'ADMIN'],
	'/saga': ['ADMIN']
};

export { requireRole, routeRoles };