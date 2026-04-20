package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.UsuarioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<UsuarioDocument, String>{
}
