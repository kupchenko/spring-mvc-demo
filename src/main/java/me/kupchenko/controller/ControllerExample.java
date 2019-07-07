package me.kupchenko.controller;

import lombok.extern.log4j.Log4j2;
import me.kupchenko.exception.BusinessLogicException;
import me.kupchenko.model.ResponseMetadata;
import me.kupchenko.model.User;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.TimeUnit;

@Log4j2
@Controller
public class ControllerExample {

    @GetMapping("/")
    public String showHomePage(Model model) {
        log.info("Handling request");
        model.addAttribute("name", "Dmitrii");
        return "home";
    }

    @RequestMapping("/greeting")
    public String showGreetingPage(Model model) {
        log.info("Handling request");
        model.addAttribute("name", "Dmitrii");
        return "greeting";
    }

    @RequestMapping("/v2/greeting")
    public ModelAndView showV2GreetingPage(ModelAndView modelAndView) {
        log.info("Handling request");
        modelAndView.addObject("name", "Dmitrii");
        modelAndView.setViewName("greeting");
        return modelAndView;
    }

    @RequestMapping("/greeting/redirect")
    public String redirectOnGreetingPage(Model model) {
        return "redirect:/greeting";
    }

    @ResponseBody
    @GetMapping(value = "/user/json/v2", produces = "application/json")
    public User returnJson(Model model) {
        return new User("Dmitrii", "Kupchenko");
    }

    @RequestMapping(value = "/user/json", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<User> returnJsonResponseEntity(Model model) {
        User user = new User("Dmitrii", "Kupchenko");
        return ResponseEntity.ok().body(user);
    }

    @PostMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMetadata> createUser(User user) {
        //save user
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMetadata("user.created", "User has been created."));
    }

    @ResponseBody
    @GetMapping("/nocache")
    public ResponseEntity<ResponseMetadata> noCache(Model model) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .cacheControl(CacheControl.noCache())
                .body(new ResponseMetadata("user.created", "User has been created."));
    }

    @GetMapping("/cachetimeout")
    public ResponseEntity<ResponseMetadata> cacheMaxAge(Model model) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .body(new ResponseMetadata("user.created", "User has been created."));
    }

    @GetMapping("/cookie")
    public ResponseEntity<ResponseMetadata> cookie(@CookieValue("JSESSIONID") String cookie) {
        log.info("Cookie value: {}", cookie);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMetadata("user.created", "User has been created."));
    }

    @GetMapping("/headers")
    public ResponseEntity<ResponseMetadata> headers(@RequestHeader("Accept-Encoding") String encoding,
                                                    @RequestHeader("Keep-Alive") long keepAlive) {
        log.info("Headers:\n Accept-Encoding: {} \n Keep-Alive: {}", encoding, keepAlive);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMetadata("user.created", "User has been created."));
    }

    @GetMapping("/controller/advice")
    public ResponseEntity<ResponseMetadata> exceptionHandling() {
        throw new BusinessLogicException("Something went wrong!");
    }

    @CrossOrigin(origins = "https://domain2.com", maxAge = 3600)
    @GetMapping("/cors")
    public ResponseEntity<ResponseMetadata> cors() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMetadata("everything.is.ok", "Everything is ok."));
    }
}
