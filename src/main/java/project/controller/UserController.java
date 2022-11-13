package project.controller;

import project.service.UserService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {


    @RequestMapping("/new_account")
    public String open_new_Account() throws Exception {
        return "/views/user/new_account";
    }

    @RequestMapping("/login")
    public String open_login() throws Exception {
        return "/views/user/login";
    }
}	

