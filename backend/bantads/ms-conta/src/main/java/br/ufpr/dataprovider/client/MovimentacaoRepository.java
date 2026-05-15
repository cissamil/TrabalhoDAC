package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.domain.MovimentacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Integer>{


}
