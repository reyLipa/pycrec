package com.todocode.pycrecer.config;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()")
public class TestAuthController {
    @GetMapping("/hello")
    @PreAuthorize("permitAll")
    public String hello(){
        return "hello world";
    }
    @GetMapping("/security")
    @PreAuthorize("hasAuthority('READ')")
    public String helloSecurity(){
        return "hello world Security";
    }

    @GetMapping("/hello-security")
    @PreAuthorize("hasAuthority('CREATE')")
    public String helloSecurity2(){
        return "hello world Security2";
    }
}
