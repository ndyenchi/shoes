package com.example.shoes.controller;

import com.example.shoes.common.StatusCode;
import com.example.shoes.response.GeneralApiAResponse;
import com.example.shoes.repository.LeftMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("/left-menu")
public class LeftMenuController {
    @Autowired
    private LeftMenuRepository leftMenuRepository;
    @GetMapping
    public GeneralApiAResponse<?> list(){
        return new GeneralApiAResponse<>(StatusCode.SUCCESS, HttpStatus.OK,leftMenuRepository.findAll());
    }
}
