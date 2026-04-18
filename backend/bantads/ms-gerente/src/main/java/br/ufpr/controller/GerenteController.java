////aqui ficam os endpoints de gerente
//
//package br.ufpr.controller;
//import br.ufpr.model.GerenteAdmin;
//import br.ufpr.service.GerenteService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/gerentes")
//@CrossOrigin(origins = "*")
//public class GerenteController {
//
//    private final GerenteService gerenteService;
//
//    public GerenteController(GerenteService gerenteService) {
//        this.gerenteService = gerenteService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Gerente>> listarGerentes() {
//        return ResponseEntity.ok(gerenteService.listarGerentes());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Gerente> buscarGerentePorId(@PathVariable Long id) {
//        return ResponseEntity.ok(gerenteService.buscarPorId(id));
//    }
//
//    @PostMapping
//    public ResponseEntity<Gerente> cadastrarGerente(@RequestBody Gerente gerente) {
//        Gerente novoGerente = gerenteService.cadastrarGerente(gerente);
//        return ResponseEntity.status(HttpStatus.CREATED).body(novoGerente);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Gerente> atualizarGerente(
//            @PathVariable Long id,
//            @RequestBody Gerente gerente) {
//        return ResponseEntity.ok(gerenteService.atualizarGerente(id, gerente));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> removerGerente(@PathVariable Long id) {
//        gerenteService.removerGerente(id);
//        return ResponseEntity.noContent().build();
//    }
//}
