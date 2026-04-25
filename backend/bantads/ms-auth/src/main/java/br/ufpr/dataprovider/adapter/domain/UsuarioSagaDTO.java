package br.ufpr.dataprovider.adapter.domain;

import br.ufpr.model.enumerator.StatusPedido;
import lombok.Data;

@Data
public class UsuarioSagaDTO {

  private String cpf;
  private String email;
  private StatusPedido statusPedido;
}
