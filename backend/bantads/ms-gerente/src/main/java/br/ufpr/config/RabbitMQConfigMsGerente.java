package br.ufpr.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigMsGerente {


  public static final String ASSIGN_ACCOUNT_TO_NEW_MANAGER_EXCHANGE = "fluxo.adicionar-gerente";
  public static final String ASSIGN_ACCOUNT_TO_NEW_MANAGER_QUEUE = "atribuir-conta";

  public static final String TRANSFER_ACCOUNTS_TO_MANAGER_EXCHANGE = "fluxo.remover-gerente";
  public static final String TRANSFER_ACCOUNTS_TO_MANAGER_QUEUE = "atribuir-contas";


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
  public Binding bindingAssignAccountToNewManagerChannel(Queue assignAccountToNewManagerQueue, DirectExchange assignAccountToNewManagerExchange) {
    return BindingBuilder.bind(assignAccountToNewManagerQueue).to(assignAccountToNewManagerExchange).with("fluxo.atribuir-conta.key");
  }

  @Bean
  public Binding bindingTransferAccountsToNewManagerChannel(Queue transferAccountsToNewManagerQueue, DirectExchange transferAccountsToNewManagerExchange) {
    return BindingBuilder.bind(transferAccountsToNewManagerQueue).to(transferAccountsToNewManagerExchange).with("fluxo.atribuir-contas.key");
  }



}
