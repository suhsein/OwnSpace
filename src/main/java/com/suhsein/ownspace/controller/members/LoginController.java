package com.suhsein.ownspace.controller.members;

import com.suhsein.ownspace.service.members.MemberService;
import com.suhsein.ownspace.service.members.dto.MemberDto;
import com.suhsein.ownspace.domain.members.Member;
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

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("member") MemberDto member,
                            HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember != null) {
            return "redirect:/";
        }
        // 이미 로그인 되어있으면, 메인으로 리다이렉트
        return "members/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("member") MemberDto member,
                        BindingResult bindingResult,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "members/login";
        }

        Member loginMember = memberService.login(member, bindingResult);
        if (loginMember == null) {
            return "members/login";
        }

        // 로그인 성공
        HttpSession session = request.getSession(); // 세션 있으면 반환, 없으면 신규생성
        session.setAttribute("loginMember", loginMember);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
