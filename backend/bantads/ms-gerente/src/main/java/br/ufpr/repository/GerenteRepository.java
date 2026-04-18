//busca/salva os dados no banco

import br.ufpr.model.GerenteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface GerenteRepository extends JpaRepository<Gerente, Long>{
  //JpaRepository traz save,findAll,findById,deleteById ja da lib
  List<Gerente> findByTipoOrderByNomeAsc(String tipo);
  //criando uma busca que ordena por nome ascendente
  Optional<Gerente> findByCpf(String cpf);
  //criando uma busca por cpf
  boolean existsByCpf(String cpf);
  //verifica se o cpf ja existe
}
