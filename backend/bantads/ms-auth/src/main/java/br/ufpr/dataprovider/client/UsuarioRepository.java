package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.domain.UsuarioEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioRepository extends MongoRepository<UsuarioEntity, String>{

  UsuarioEntity findByEmail(String email);
  UsuarioEntity findByUserId(String userId);
  boolean existsByUserId(String userId);

  @Transactional
  void deleteByUserId(String userId);
}
