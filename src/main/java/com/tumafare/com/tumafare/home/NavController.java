package com.tumafare.com.tumafare.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class NavController {

    @GetMapping("/about")
    public String about() {
        return "home/about";
    }

    @GetMapping("/service")
    public String service() {
        return "home/service";
    }

    @GetMapping("/team")
    public String team() {
        return "home/team";
    }

    @GetMapping("/why")
    public String why() {
        return "home/why";
    }
}
