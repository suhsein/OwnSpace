package com.suhsein.ownspace.controller;

import com.suhsein.ownspace.domain.members.Member;
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

        Member loginMember = (Member)session.getAttribute("loginMember");
        model.addAttribute("member", loginMember);
        return "/index";
    }
}
