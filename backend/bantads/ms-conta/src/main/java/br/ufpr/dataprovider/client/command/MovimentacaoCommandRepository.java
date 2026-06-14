package br.ufpr.dataprovider.client.command;

import br.ufpr.dataprovider.adapter.domain.command.MovimentacaoCommandEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoCommandRepository extends JpaRepository<MovimentacaoCommandEntity, Integer>{
}
