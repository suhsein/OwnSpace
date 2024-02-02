package com.example.demo.controller;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberRepository;
import com.example.demo.domain.member.MemberSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SignUpController {
    private final MemberRepository memberRepository;
    @GetMapping("/sign-up")
    public String signUpForm(@ModelAttribute("memberSave") MemberSaveDto memberSave) {
        // Form에서 객체 전송을 위해서 빈 객체를 모델에 넣어줌
        return "members/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Validated @ModelAttribute("memberSave") MemberSaveDto memberSave, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        // 필드 예외가 아닌 경우 -> 비밀번호와 비밀번호 확인이 서로 다름
        String pw = memberSave.getPassword();
        String checkPw = memberSave.getCheckPassword();

        if(pw != null && checkPw != null){
            if(!(pw.equals(checkPw))){
                bindingResult.reject("passwordCheckFail");
            }
        }

        // Validation Error
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/sign-up";
        }

        // Success: Convert Validation Bean to Member
        Member member = new Member().builder()
                .userId(memberSave.getUserId())
                .password(memberSave.getPassword())
                .email(memberSave.getEmail())
                .username(memberSave.getUsername())
                .build();

        memberRepository.save(member);
        // addFlashAttribute => 리다이렉트 시, 파라미터로 전달하는 대신 모델에 값을 담을 수 있게 해줌.
        redirectAttributes.addFlashAttribute("signUpSuccess", "success");

        return "redirect:/login";
    }
}
