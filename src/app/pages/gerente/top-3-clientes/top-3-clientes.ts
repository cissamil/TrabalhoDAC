import { Component } from '@angular/core';

interface TopCliente {
  posicao: number;
  nome: string;
  volumeFinanceiro: number;
  score: number;
}

@Component({
  selector: 'app-top-3-clientes',
  imports: [],
  templateUrl: './top-3-clientes.html',
  styleUrl: './top-3-clientes.css',
})
export class Top3Clientes {
  topClientes: TopCliente[] = [
    { posicao: 1, nome: 'Carla Mendes', volumeFinanceiro: 15300, score: 98 },
    { posicao: 2, nome: 'Ana Souza', volumeFinanceiro: 8900, score: 92 },
    { posicao: 3, nome: 'Elisa Prado', volumeFinanceiro: 4200, score: 89 },
  ];
}
