package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.domain.PedidoAutocadastroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoAutocadastroRepository extends JpaRepository<PedidoAutocadastroEntity, Integer> {
}
