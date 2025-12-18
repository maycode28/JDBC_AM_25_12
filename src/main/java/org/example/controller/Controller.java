package org.example.controller;

import org.example.dto.Member;

import java.util.List;
public class Controller {

    protected static Member loginedMember = null;

    protected static List<Member> members;

    public static boolean isLogined() {
        return loginedMember != null;
    }

    public void doAction(String cmd, String actionMethodName) {
        // 구현하지 마세요
    }

}