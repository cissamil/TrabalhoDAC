import { Component } from '@angular/core';

interface ClienteResumo {
  id: number;
  nome: string;
  email: string;
  saldo: number;
  status: 'Ativo' | 'Inativo';
}

@Component({
  selector: 'app-todos-clientes',
  imports: [],
  templateUrl: './todos-clientes.html',
  styleUrl: './todos-clientes.css',
})
export class TodosClientes {
  clientes: ClienteResumo[] = [
    { id: 1, nome: 'Ana Souza', email: 'ana@email.com', saldo: 8900, status: 'Ativo' },
    { id: 2, nome: 'Bruno Lima', email: 'bruno@email.com', saldo: 1240, status: 'Ativo' },
    { id: 3, nome: 'Carla Mendes', email: 'carla@email.com', saldo: 15300, status: 'Ativo' },
    { id: 4, nome: 'Diego Rocha', email: 'diego@email.com', saldo: -150, status: 'Inativo' },
    { id: 5, nome: 'Elisa Prado', email: 'elisa@email.com', saldo: 4200, status: 'Ativo' },
  ];
}
