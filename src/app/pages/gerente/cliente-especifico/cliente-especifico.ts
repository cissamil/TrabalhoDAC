import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

interface ClienteDetalhado {
  id: number;
  nome: string;
  email: string;
  telefone: string;
  saldo: number;
  limite: number;
  status: string;
}

@Component({
  selector: 'app-cliente-especifico',
  imports: [FormsModule],
  templateUrl: './cliente-especifico.html',
  styleUrl: './cliente-especifico.css',
})
export class ClienteEspecifico {
  filtroId = 1;

  clientes: ClienteDetalhado[] = [
    {
      id: 1,
      nome: 'Ana Souza',
      email: 'ana@email.com',
      telefone: '(41) 99999-1111',
      saldo: 8900,
      limite: 2500,
      status: 'Ativo',
    },
    {
      id: 2,
      nome: 'Bruno Lima',
      email: 'bruno@email.com',
      telefone: '(41) 98888-2222',
      saldo: 1240,
      limite: 1500,
      status: 'Ativo',
    },
    {
      id: 3,
      nome: 'Carla Mendes',
      email: 'carla@email.com',
      telefone: '(41) 97777-3333',
      saldo: 15300,
      limite: 5000,
      status: 'Ativo',
    },
  ];

  get clienteSelecionado(): ClienteDetalhado | undefined {
    return this.clientes.find((cliente) => cliente.id === Number(this.filtroId));
  }
}
