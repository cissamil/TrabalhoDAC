import { Router } from '@angular/router';
import { NgxMaskDirective } from "ngx-mask";
import { FormsModule } from "@angular/forms";
import { DecimalPipe, NgClass } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ResponseModal } from '../../../core/models/response-modal';
import { validateCEP, validateEmail } from '../../../core/shared/helpers';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ClienteSessionService } from '../../../core/services/session-controller.service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { ClienteConta } from '../../../core/models/ClienteConta';
import { ContaGerente } from '../../../core/models/ContaGerente';

@Component({
  selector: 'app-cliente-perfil',
  imports: [FormsModule, DecimalPipe, NgxMaskDirective, MatIconModule, NgClass],
  templateUrl: './cliente-perfil.html',
  styleUrls: ['./cliente-perfil.css', '../../shared/css/responseModal.css'],
})
export class ClientePerfil implements OnInit {
  constructor(
    //private router: Router,
    private contaService: ContaService,
    private clienteService: ClienteService,
    private clienteSessionService: ClienteSessionService,
    private cdr: ChangeDetectorRef,

  ) {}

  private clienteConta!: ClienteConta;
  contaCliente!: ContaGerente;
  cep: string = '';
  rua: string = '';
  cidade: string = '';
  estado: string = '';
  endereco: string[] = [];
  salario: string = '';
  updatedCliente!: ClienteConta;

  currencyFormatter: CurrencyFormatter = new CurrencyFormatter();
  responseModal: ResponseModal | null = null;

  ngOnInit(): void {
    const dadosCarregados = this.clienteService.clienteContaLogado;

    if (dadosCarregados) {
      this.clienteConta = dadosCarregados;
      this.contaCliente = dadosCarregados.conta;
      this.initalizeProfileData();
      this.cdr.detectChanges();

    } else {
      console.log("Nenhum dado encontrado no localStorage para o Perfil.");
    }
  }

  get colorSaldo(){
    return this.contaCliente.saldo > 0 ? 'blue' : 'red';
  }

  initalizeProfileData() {
    this.updatedCliente = {...this.clienteConta};
    if (this.clienteConta.endereco) {
      const enderecoParts = this.clienteConta.endereco.split(' - ');
      this.cep = enderecoParts[0] || '';
      this.rua = enderecoParts[1] || '';
      this.cidade = enderecoParts[2] || '';
      this.estado = enderecoParts[3] || '';
    }

    // Formata o salário para exibição inicial
    this.salario = this.currencyFormatter.applyCurrencyMaskOnNumber(
      this.clienteConta.salario,
    );
  }

  handleSalario(e: any) {
    //mascara em tempo real
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.salario = input.value;
  }

  closeModal(){
    this.responseModal = null;
  }


  atualizarDados() {

    const verifyFields = this.validateFields();

    if(verifyFields != null){
      this.responseModal = {
        title: "Campo Inválido",
        message: verifyFields,
        messageIcon: "error",
        type: 'error'
      };
      return;
    }

    const salarioNumber: number = this.currencyFormatter.removeCurrencyMaskFromString(this.salario);
    let novoLimite = salarioNumber / 2;

    console.log(`Novo limite: ${novoLimite}, Saldo: ${this.contaCliente.saldo}`)

    if(this.contaCliente.saldo < 0){
      const saldoPositivo = this.contaCliente.saldo * -1;
      if(novoLimite < saldoPositivo){
        novoLimite = saldoPositivo;
      }
    }

    this.contaCliente.limite = novoLimite;
    console.log('Salário atual', salarioNumber);
    const enderecoCompleto = `${this.cep} - ${this.rua} - ${this.cidade} - ${this.estado}`;

    this.updatedCliente.salario = salarioNumber;
    this.updatedCliente.endereco = enderecoCompleto;


//função de atualizar cliente, envia os dados informados para o servidor
    this.clienteService.atualizar(this.updatedCliente).subscribe({
      next:(clienteAtualizado: any)=>{
        //dado que salvou corretamente no servidor e no banco
        this.updatedCliente.conta=this.contaCliente
        //e atuliza em updatedCliente com o novoLimite e cliente no local

        this.contaService.atualizarConta(this.contaCliente as any).subscribe({
          //envia os dados informados para o servidor atualizar a conta e o limite no bd
          next: (contaAtualizadaBanco: any) => {
            // salvou corretamente no servidor, atualiza o banco
            this.updatedCliente = {
              //pega as infos de updatedCLiente e copiando para ClienteConta e Conta Gerente (local)
              ...(clienteAtualizado as ClienteConta),
              conta: contaAtualizadaBanco as ContaGerente
            };
            // Salva os dados na sessão
            this.clienteSessionService.setCliente(this.updatedCliente);
            this.responseModal = {
              title: "Sucesso",
              message: "Dados alterados com êxito!",
              messageIcon: "check",
              type: 'success'
            };
          },
          error: (err) => {
            console.error('Erro ao atualizar limite da conta', err);
            this.mostrarModalErro('Erro ao salvar o novo limite de crédito no servidor.');
          }
          });
      },
      error: (err) => {
        console.error('Erro ao atualizar dados cadastrais do cliente', err);
        this.mostrarModalErro('Erro ao salvar os dados cadastrais no servidor.');
      }
    });
  }

mostrarModalErro(msg: string) {
    this.responseModal = {
      title: "Erro",
      message: msg,
      messageIcon: "error",
      type: 'error'
    };
    this.cdr.detectChanges();
  }


  validateFields(): string | null{

    if(!this.clienteConta.nome) return "Preencha o nome corretamente";

    if(!this.clienteConta.email) return "Preencha o email corretamente";

    if(!this.clienteConta.cpf) return "Preencha o CPF corretamente";

    if(!this.clienteConta.telefone) return "Preencha o telefone corretamente";

    if(this.salario === "0,00") return "Preencha o campo de salário";

    if(!this.cep) return "Preencha o o CEP corretamente";

    if(!this.rua) return "Preencha a rua corretamente";

    if(!this.cidade) return "Preencha a cidade corretamente";

    if(!this.estado) return "Preencha o estado corretamente";

    if (!validateEmail(this.clienteConta.email)) return "Digite um email válido";

    if(!validateCEP(this.cep)) return "Preencha o cep corretamente";


    return null;

  }

}
