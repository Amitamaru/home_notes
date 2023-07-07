package com.marzhiievskyi.home_notes.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home-notes")
public class CommonController {
    @GetMapping("/version")
    public String version() {
        String version = "Home notes. Version 1.0.0 (BETA)";
        log.info(version);
        return version;
    }
}
