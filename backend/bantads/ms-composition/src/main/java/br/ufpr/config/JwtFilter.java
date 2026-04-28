package br.ufpr.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;

@Component
public class JwtFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

    String header = request.getHeader("Authorization");

    // Verifica se o header existe e começa com "Bearer "
    if (header != null && header.startsWith("Bearer ")) {
      try {
        // Remove o "Bearer " para pegar só o token
        String token = header.substring(7);

        // O JWT tem 3 partes separadas por ponto: header.payload.signature
        String[] parts = token.split("\\.");

        if (parts.length >= 2) {
          // A parte 1 (índice 1) é o Payload. Decodificamos de Base64 para String
          String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

          // Lemos o JSON para extrair o campo "sub"
          JsonNode payload = new ObjectMapper().readTree(payloadJson);

          if (payload.has("sub")) {
            String idGerente = payload.get("sub").asText();

            // O "pulo do gato": salvamos o ID como um atributo da requisição
            request.setAttribute("usuarioLogadoId", idGerente);
          }
        }
      } catch (Exception e) {
        // Se der qualquer erro de formatação no mock, o log avisa
        logger.warn("Erro ao tentar extrair dados do token Mock: " + e.getMessage());
      }
    }

    // Libera a requisição para seguir seu caminho até o Controller
    filterChain.doFilter(request, response);
  }
}
