//normaliza
function parseUserRoles(user) {
	if (!user) return []; 
	if (Array.isArray(user.role)) return user.role.map(String); 
	if (user.role) return (Array.isArray(user.role) ? user.role : [user.role]).map(String); 
	if (user.tipoUsuario) return [String(user.tipoUsuario)];

	return []; 
}

function requireRole(allowedRoles) {
	return (req, res, next) => {
		if (!allowedRoles || allowedRoles.length === 0) return next(); //nenhuma rota definida, qualquer um pode eacessar

		const user = req.user;

		if (!user) {
			return res.status(403).json({ error: 'Forbidden', message: 'Usuário não autenticado' }); // sem verifyJWT  
		}


		const userRoles = parseUserRoles(user).map(r => String(r).toUpperCase());

		const allowed = allowedRoles.map(r => String(r).toUpperCase());

		
		const hasPermission = userRoles.some(r => allowed.includes(r)); // userRoles quem  voce e e allowed quem pode entrar

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