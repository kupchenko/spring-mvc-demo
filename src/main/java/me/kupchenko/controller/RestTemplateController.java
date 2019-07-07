package me.kupchenko.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.kupchenko.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Controller
@RequestMapping("/resttemplate")
@AllArgsConstructor
public class RestTemplateController {
    private RestTemplate restTemplate;

    @GetMapping("/getForEntity")
    private ResponseEntity getFromRemote() {
        log.info("RestTemplate request");
        ResponseEntity<User> responseEntity = restTemplate.getForEntity("http://localhost:8080/spring_mvc_demo_war/user/json", User.class);
        log.info("ResponseEntity with status {} and body {}", responseEntity.getStatusCode(), responseEntity.getBody());
        return ResponseEntity.ok().body(responseEntity.getBody());
    }
}
