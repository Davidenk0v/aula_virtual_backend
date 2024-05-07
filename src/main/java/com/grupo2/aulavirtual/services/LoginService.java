package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.payload.request.LoginRequestDto;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<?> loginUser(LoginRequestDto loginRequest);
}
