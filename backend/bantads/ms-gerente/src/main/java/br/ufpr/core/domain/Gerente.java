package br.ufpr.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import br.ufpr.model.enumerator.TipoGerente;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gerente {
  private Integer id;
  private String cpf;
  private String nome;
  private String email;
  private String gerenteId;
  private TipoGerente tipoGerente;

}
