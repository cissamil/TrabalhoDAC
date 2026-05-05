package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.LoginInputData;
import br.ufpr.core.ports.input.LoginUserInputPort;
import br.ufpr.entrypoint.request.LoginRequest;
import br.ufpr.entrypoint.response.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.lang.Object;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

  private final LoginUserInputPort loginUserInputPort;

  @PostMapping(value = "/login")
  ResponseEntity<TokenResponse> loginUser(@RequestBody @Valid LoginRequest request){

    LoginInputData inputData = new LoginInputData();

    inputData.setUserEmail(request.getUserEmail());
    inputData.setUserPassword(request.getUserPassword());

    String accessToken = loginUserInputPort.execute(inputData);

    TokenResponse tokenResponse = new TokenResponse();

    tokenResponse.setToken(accessToken);

    return ResponseEntity.ok(tokenResponse);

  }


}
