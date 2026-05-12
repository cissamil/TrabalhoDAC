import dotenv from 'dotenv';
import morgan from 'morgan';
import cors from 'cors';
import express from 'express';

// Carrega as variaveis de ambiente
dotenv.config();

//String -> numero
const port = Number(process.env.PORT);

// URLs dos ms
const services = {
	authService:        process.env.AUTH_SERVICE_URL,
	contaService:       process.env.CONTA_SERVICE_URL,
	clienteService:     process.env.CLIENTE_SERVICE_URL,
	gerenteService:     process.env.GERENTE_SERVICE_URL,
	compositionService: process.env.COMPOSITION_SERVICE_URL,
};

//Caminhos no gateway
const interpreters = {
	clienteInterpreter: '/clientes',
	gerenteInterpreter: '/gerentes',
	contaInterpreter: '/contas',
	authInterpreter: '/auth',
	compositionInterpreter: ''
};

function setupMiddlewares(app) {
	//Aceita JSON nas requisições
	app.use(express.json());

	//Log de requisições
	app.use(morgan('dev'));

	//CORS
	app.use(cors({
		origin: [
			'http://localhost:4200',
			'http://127.0.0.1:4200'
		],
		credentials: true,
		methods: ['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS'],
		allowedHeaders: ['Content-Type', 'Authorization']
	}));
}

export { port, services, interpreters, setupMiddlewares };