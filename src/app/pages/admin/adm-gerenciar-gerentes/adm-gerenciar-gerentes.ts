import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { ManagerListTableData } from '../../../core/models/table-data';
import { CONTAS_MOCK } from '../../../core/mock/mock-data';
import { Conta, GerenteAdmin } from '../../../core/models/entities';
import { GerenteService } from '../../../core/services/gerente-services';

@Component({
  selector: 'app-admin-gerenciar-gerentes',
  imports: [MatTableModule],
  templateUrl: './adm-gerenciar-gerentes.html',
  styleUrl: './adm-gerenciar-gerentes.css',
})
export class AdminGerenciarGerentes implements OnInit{
  gerentes:GerenteAdmin[]=[];
  contas:Conta[]=CONTAS_MOCK;
  MANAGERS_TABLE:ManagerListTableData[]=[];
  //array final da tabela
  totalGerentes:number=0;
  totalContas:number=0;
  mediaPorGerente:number=0;

  displayedcColunas:string[]=[
    'Nome',
    'CPF',
    'E-mail',
    'Telefone',
    'Quantidade de Contas',
    'Ações'
  ];

  constructor(private gerenteService:GerenteService){}
  calcularCards():void{
    this.totalGerentes=this.gerentes.length;
    this.totalContas=this.contas.length;
    this.mediaPorGerente=this.totalGerentes>0?this.totalContas/this.totalGerentes:0;
  }

  ngOnInit(): void {
    this.gerentes=this.gerenteService.listarGerentes();
    this.fillGerentesTable();
    this.calcularCards();
  };


  fillGerentesTable(): void {
    //imprime os dados dos gerentes na tela em formato de tabela
  this.MANAGERS_TABLE = this.gerentes.map((gerente) => {
      //preenche e espelha todos os dados de gerente com spreed na impressao da tabela

    const quantidadeContas = this.contas.filter(
      //faz o cruzamento e calcula a qtd de contas que cada gerente tem
      (conta) => conta.gerente === gerente.nome
    ).length;

    return {
      nome: gerente.nome,
      cpf: gerente.cpf,
      email: gerente.email,
      telefone: gerente.telefone,
      quantidadeClientes: quantidadeContas // mantém seu nome
    };
  });

  this.MANAGERS_TABLE.sort((a, b) => a.nome.localeCompare(b.nome));
    //ordem alfabetica
}




}
