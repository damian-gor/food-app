package com.damgor.foodapp.controller.controllerUI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

@RestController
@ApiIgnore
@RequestMapping("/ui")
public class LoginRedirectionUI {

    @GetMapping("/login")
    public void loginAndRedirect(HttpServletResponse httpServletResponse, @RequestParam("referer") String referer) {
        httpServletResponse.setHeader("Location", referer);
        httpServletResponse.setStatus(302);
    }
}
