package com.PetarsCalorieTracker;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/hello_controller")
public class HelloController {

    @GetMapping("/mapping1")
    public void getFirstMapping(@RequestParam(name="country") String country,
                                  @RequestParam(name="city") String city,
                                  HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.printf("country: %s, city: %s\n", country, city);
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("requested session id: " + request.getRequestedSessionId());
        response.sendRedirect("/hello.html");
    }
}
