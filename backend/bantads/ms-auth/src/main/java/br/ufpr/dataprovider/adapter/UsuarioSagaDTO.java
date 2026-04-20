package br.ufpr.dataprovider.adapter;

import lombok.Data;

@Data
public class UsuarioSagaDTO {

  private String cpf;
  private String email;
  private StatusPedido statusPedido;
}
