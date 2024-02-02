package com.example.demo.controller;

import com.example.demo.domain.members.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @GetMapping("")
    public String home(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        if(session == null){
            return "/index";
        }

        Member loginMember = (Member)session.getAttribute("loginMember");
        if(loginMember == null){
            return "/index";
        }

        model.addAttribute("member", loginMember);
        return "/bbs";
    }

    @GetMapping("/bbsMain")
    public String bbsMain() {
        return "/bbs";
    }
}
