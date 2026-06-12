package br.ufpr.dataprovider.client.query;

import br.ufpr.dataprovider.adapter.domain.query.ContaQueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaQueryRepository extends JpaRepository<ContaQueryEntity, Integer> {
}
