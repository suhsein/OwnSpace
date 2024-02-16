package com.example.demo.controller.members;

import com.example.demo.service.members.LoginService;
import com.example.demo.domain.members.Member;
import com.example.demo.domain.members.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("member") MemberDto member) {
        return "/members/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("member") MemberDto member,
                        BindingResult bindingResult,
                        HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            return "/members/login";
        }

        Member loginMember = loginService.login(member.getUserId(), member.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail");
            return "/members/login";
        }

        // 로그인 성공
        HttpSession session = request.getSession(); // 세션 있으면 반환, 없으면 신규생성
        session.setAttribute("loginMember", loginMember);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}
