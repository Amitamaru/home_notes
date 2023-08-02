package com.marzhiievskyi.home_notes.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home-notes")
@OpenAPIDefinition(info = @Info(title = "Home notes", version = "1.0.0"))
public class CommonController {
    @GetMapping("/version")
    public String version() {
        return  "Home notes. Version 1.0.0 (BETA)";
    }
}
