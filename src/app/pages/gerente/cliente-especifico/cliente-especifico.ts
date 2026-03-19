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
}