package br.ufpr.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfigMsConta {

  public static final String REGISTER_EXCHANGE = "fluxo.autocadastro";
  public static final String REGISTER_QUEUE = "cadastrar-conta";

  public static final String APPROVED_ACCOUNT_EXCHANGE = "fluxo.conta-aprovada";
  public static final String APPROVED_ACCOUNT_QUEUE = "gerar-credencial-cliente";

  public static final String ASSIGN_ACCOUNT_TO_NEW_MANAGER_EXCHANGE = "fluxo.adicionar-gerente";
  public static final String ASSIGN_ACCOUNT_TO_NEW_MANAGER_QUEUE = "atribuir-conta";

  public static final String TRANSFER_ACCOUNTS_TO_MANAGER_EXCHANGE = "fluxo.remover-gerente";
  public static final String TRANSFER_ACCOUNTS_TO_MANAGER_QUEUE = "transferir-contas";

  public static final String UPDATE_ACCOUNT_LIMIT_EXCHANGE = "fluxo.atualizar-limite";
  public static final String UPDATE_ACCOUNT_LIMIT_QUEUE = "atualizar-limite";

  @Bean
  public DirectExchange registerExchange(){
    return new DirectExchange(REGISTER_EXCHANGE);
  }

  @Bean
  public Queue registerQueue(){
    return new Queue(REGISTER_QUEUE, true, false, false);
  }

  @Bean
  public DirectExchange approvedAccountExchange(){
    return new DirectExchange(APPROVED_ACCOUNT_EXCHANGE);
  }

  @Bean
  public Queue approvedAccountQueue(){
    return new Queue(APPROVED_ACCOUNT_QUEUE, true, false, false);
  }

  @Bean
  public DirectExchange assignAccountToNewManagerExchange(){
    return new DirectExchange(ASSIGN_ACCOUNT_TO_NEW_MANAGER_EXCHANGE);
  }

  @Bean
  public Queue assignAccountToNewManagerQueue(){
    return new Queue(ASSIGN_ACCOUNT_TO_NEW_MANAGER_QUEUE, true, false, false);
  }

  @Bean
  public DirectExchange transferAccountsToNewManagerExchange(){
    return new DirectExchange(TRANSFER_ACCOUNTS_TO_MANAGER_EXCHANGE);
  }

  @Bean
  public Queue transferAccountsToNewManagerQueue(){
    return new Queue(TRANSFER_ACCOUNTS_TO_MANAGER_QUEUE, true, false, false);
  }

  @Bean
  public DirectExchange updateAccountLimitExchange(){
    return new DirectExchange(UPDATE_ACCOUNT_LIMIT_EXCHANGE);
  }

  @Bean
  public Queue updateAccountLimitQueue(){
    return new Queue(UPDATE_ACCOUNT_LIMIT_QUEUE, true, false, false);
  }


  @Bean
  public Binding bindingRegisterChannel(Queue registerQueue, DirectExchange registerExchange) {
    return BindingBuilder.bind(registerQueue).to(registerExchange).with("fluxo.autocadastro.key");
  }

  @Bean
  public Binding bindingApprovedAccountChannel(Queue registerQueue, DirectExchange registerExchange) {
    return BindingBuilder.bind(registerQueue).to(registerExchange).with("fluxo.conta-aprovada.key");
  }

  @Bean
  public Binding bindingAssignAccountToNewManagerChannel(Queue assignAccountToNewManagerQueue, DirectExchange assignAccountToNewManagerExchange) {
    return BindingBuilder.bind(assignAccountToNewManagerQueue).to(assignAccountToNewManagerExchange).with("fluxo.atribuir-conta.key");
  }

  @Bean
  public Binding bindingTransferAccountsToNewManagerChannel(Queue transferAccountsToNewManagerQueue, DirectExchange transferAccountsToNewManagerExchange) {
    return BindingBuilder.bind(transferAccountsToNewManagerQueue).to(transferAccountsToNewManagerExchange).with("fluxo.transferir-contas.key");
  }

  @Bean
  public Binding bindingUpdateAccountLimitChannel(Queue updateAccountLimitQueue, DirectExchange updateAccountLimitExchange) {
    return BindingBuilder.bind(updateAccountLimitQueue).to(updateAccountLimitExchange).with("fluxo.atualizar-limite.key");
  }


}
