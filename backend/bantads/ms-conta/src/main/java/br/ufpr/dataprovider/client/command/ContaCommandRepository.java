package br.ufpr.dataprovider.client.command;

import br.ufpr.dataprovider.adapter.domain.command.ContaCommandEntity;
import br.ufpr.core.domain.StatusConta;
import br.ufpr.dataprovider.adapter.domain.GerenteTotalClientesResponse;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaCommandRepository extends JpaRepository<ContaCommandEntity, Integer> {
}
