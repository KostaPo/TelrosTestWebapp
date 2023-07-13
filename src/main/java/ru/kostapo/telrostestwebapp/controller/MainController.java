package ru.kostapo.telrostestwebapp.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j
@Controller
public class MainController {
    @RequestMapping({"", "/"})
    public String index() {
        log.debug("get request on index page");
        return "login";
    }

    @RequestMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        log.debug("get request on login page");
        if (error != null) {
            model.addAttribute("error", error);
            log.debug("login page request with error: " + error);
        }
        if (logout != null) {
            model.addAttribute("logout", logout);
            log.debug("login page request with logout: " + logout);
        }
        return "login";
    }
}
