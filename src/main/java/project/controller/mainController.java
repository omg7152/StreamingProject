package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class mainController {

    @RequestMapping("/main")
    public String open_main(){
        return "/views/main/main";
    }

    @RequestMapping("/admin")
    public String open_admin(){
        return "/views/admin/admin";
    }

    @RequestMapping("/channel_contents")
    public String open_channel_contents(){
        return "/views/admin/channel_contents";
    }
}
