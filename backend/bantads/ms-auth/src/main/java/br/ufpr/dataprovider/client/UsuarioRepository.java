package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.domain.UsuarioEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<UsuarioEntity, String>{
}
