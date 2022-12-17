package com.example.shoes.response;

import com.example.shoes.dto.JwtResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class GeneralApiAResponse<T> {
    private String statusCode;
    private HttpStatus status;
    private T result;

    public GeneralApiAResponse(String statusCode, HttpStatus status, T result) {
        this.statusCode = statusCode;
        this.status = status;
        this.result = result;
    }
}
