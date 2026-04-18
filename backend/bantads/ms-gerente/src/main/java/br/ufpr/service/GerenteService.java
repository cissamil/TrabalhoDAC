////aqui fica a logica de gerente
//
//package br.ufpr.service;
//
//import br.ufpr.model.GerenteModel;
//import br.ufpr.repository.GerenteRepository;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class GerenteService {
//
//    private final GerenteRepository gerenteRepository;
//
//    public GerenteService(GerenteRepository gerenteRepository) {
//        this.gerenteRepository = gerenteRepository;
//    }
//
//    public List<GerenteModel> listarGerentes() {
//        return gerenteRepository.findByTipoOrderByNomeAsc("gerente");
//    }
//
//    public GerenteModel buscarPorId(Long id) {
//        return gerenteRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Gerente não encontrado"));
//    }
//
//    public GerenteModel cadastrarGerente(Gerente gerente) {
//        if (gerenteRepository.existsByCpf(gerente.getCpf())) {
//            throw new RuntimeException("Já existe gerente com esse CPF");
//        }
//
//        gerente.setTipo("gerente");
//        return gerenteRepository.save(gerente);
//    }
//
//    public GerenteModel atualizarGerente(Long id, Gerente dadosAtualizados) {
//        GerenteModel gerente = buscarPorId(id);
//
//        gerente.setNome(dadosAtualizados.getNome());
//        gerente.setEmail(dadosAtualizados.getEmail());
//        gerente.setTelefone(dadosAtualizados.getTelefone());
//        gerente.setSenha(dadosAtualizados.getSenha());
//
//        return gerenteRepository.save(gerente);
//    }
//
//    public void removerGerente(Long id) {
//        Gerente gerente = buscarPorId(id);
//        gerenteRepository.delete(gerente);
//    }
//}
