package br.ufpr.rabbitMQConfig.consumers;

import br.ufpr.dataprovider.adapter.StatusPedido;
import br.ufpr.dataprovider.adapter.TipoUsuario;
import br.ufpr.dataprovider.adapter.UsuarioDocument;
import br.ufpr.dataprovider.adapter.UsuarioSagaDTO;
import br.ufpr.dataprovider.client.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CriarContaConsumer {

  private final UsuarioRepository usuarioRepository;

  @RabbitListener(queues = "criar.credencial.queue")
  public void criarCredencial(UsuarioSagaDTO usuarioDTO){
    if(!StatusPedido.APROVADO.equals(usuarioDTO.getStatusPedido())){
      throw new RuntimeException("Pedido de usuário não aprovado");
    }

    UsuarioDocument novoUsuario = new UsuarioDocument();

    novoUsuario.setLogin(usuarioDTO.getEmail());
    novoUsuario.setSenha("1234");
    novoUsuario.setTipoUsuario(TipoUsuario.CLIENTE);

    usuarioRepository.save(novoUsuario);

    System.out.println("Credenciais criadas no MongoDB para: " + novoUsuario.getLogin());


  }

}
