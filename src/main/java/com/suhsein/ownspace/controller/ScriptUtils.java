package com.suhsein.ownspace.controller;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ScriptUtils {
    public static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=euc-kr");
        response.setCharacterEncoding("euc-kr");
    }
    public static void alert(HttpServletResponse response, String alertText){
        init(response);
        try{
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('" + alertText + "');</script>");
            writer.flush();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void alertAndMovePage(HttpServletResponse response, String alertText, String nextPage){
        init(response);
        try{
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('" + alertText + "'); location.href='" + nextPage + "';</script>");
            writer.flush();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void alertAndBackPage(HttpServletResponse response, String alertText){
        init(response);
        try{
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('" + alertText + "'); history.go(-1);</script>");
            writer.flush();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
