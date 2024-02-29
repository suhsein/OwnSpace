package com.suhsein.ownspace.controller;

import com.suhsein.ownspace.domain.members.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public class CheckLogin {
    public static boolean checkLoginMember(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("loginMember");
        if(loginMember == null){
            model.addAttribute("msg", "로그인 된 사용자만 글을 작성할 수 있습니다.");
            model.addAttribute("redirectURL", "/login");
            return false;
        }
        return true;
    }
}
