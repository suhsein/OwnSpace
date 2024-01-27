package com.example.demo.controller;

import com.example.demo.domain.member.LoginService;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberDto;
import com.example.demo.domain.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberRepository memberRepository;
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("member") MemberDto member) {
        return "/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("member") MemberDto member, BindingResult bindingResult,
                        HttpServletRequest request) {
        log.info("bindingResult={}", bindingResult.getAllErrors());
        if(bindingResult.hasErrors()){
            return "/login";
        }

        Member loginMember = loginService.login(member.getUserId(), member.getPassword());
        if (loginMember == null) {
            bindingResult.reject("loginFail");
            return "/login";
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
