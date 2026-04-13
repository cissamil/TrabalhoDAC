// recebe requisicoes do cliente e encaminha para o microservico correto.

import express from 'express';
import dotenv from 'dotenv';

// Carrega as variaveis do .env para dentro de process.env.
dotenv.config();


const app = express();
const port = Number(process.env.PORT || 8080);

// Mapeia os enderecos dos microservicos que o gateway vai usar no proxy.
const services = {

	auth: process.env.AUTH_SERVICE_URL || 'http://localhost:8081',
	cliente: process.env.CLIENTE_SERVICE_URL || 'http://localhost:8082',
	conta: process.env.CONTA_SERVICE_URL || 'http://localhost:8083',
	gerente: process.env.GERENTE_SERVICE_URL || 'http://localhost:8084',
	saga: process.env.SAGA_SERVICE_URL || 'http://localhost:8085'
};

