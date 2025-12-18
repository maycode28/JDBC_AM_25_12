package org.example.controller;

import org.example.service.MemberService;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController {
    private Connection conn;
    private Scanner sc;

    private MemberService memberService;

    public MemberController(Scanner sc, Connection conn) {
        this.sc = sc;
        this.conn = conn;
        this.memberService = new MemberService();
    }

    public void doJoin() {
        String loginId = null;
        String loginPw = null;
        String loginPwConfirm = null;
        String name = null;
        System.out.println("==회원가입==");
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();

            if (loginId.length() == 0 || loginId.contains(" ")) {
                System.out.println("아이디 똑바로 써");
                continue;
            }

            boolean isLoginIdDup = memberService.isLoginIdDup(conn, loginId);

            System.out.println(isLoginIdDup);

            if (isLoginIdDup) {
                System.out.println(loginId + "은(는) 이미 사용중");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine().trim();

            if (loginPw.length() == 0 || loginPw.contains(" ")) {
                System.out.println("비밀번호 똑바로 써");
                continue;
            }

            boolean loginCheckPw = true;

            while (true) {
                System.out.print("비번 확인 : ");
                loginPwConfirm = sc.nextLine().trim();

                if (loginPwConfirm.length() == 0 || loginPwConfirm.contains(" ")) {
                    System.out.println("비밀번호 확인 똑바로 써");
                    continue;
                }

                if (loginPw.equals(loginPwConfirm) == false) {
                    System.out.println("비번이 일치하지 않아");
                    loginCheckPw = false;
                }
                break;
            }
            if (loginCheckPw) {
                break;
            }
        }
        while (true) {
            System.out.print("이름 : ");
            name = sc.nextLine().trim();

            if (name.length() == 0 || name.contains(" ")) {
                System.out.println("이름 똑바로 써");
                continue;
            }
            break;
        }

        int id = memberService.doJoin(conn, loginId, loginPw, name);

        System.out.println(id + "번 회원 가입함");
    }

    public void doLogin() {
        System.out.println("==로그인==");
        for (int i =1; i<=3; i++){
            System.out.print("아이디 : ");
            String loginId = sc.nextLine().trim();
            System.out.print("비밀번호 :");
            String loginPw = sc.nextLine().trim();
            Controller.loginedMember = memberService.doLogin(conn, loginId,loginPw);
            if(Controller.loginedMember!=null){
                System.out.println(Controller.loginedMember.getName()+"님 로그인 되었습니다.");
                return;
            }
            System.out.println("로그인 되지 않았습니다. 아이디와 비밀번호를 확인해주세요.");

        }
        System.out.println("로그인 시도 횟수를 초과했습니다. 다시 확인하시고 로그인해주세요");


    }

    public void doLogout() {
        Controller.loginedMember = null;
        System.out.println("로그아웃 되었습니다.");
    }

    public void showProfile() {
        System.out.println("==회원 정보==");
        System.out.println("이름 : "+ Controller.loginedMember.getName());
        System.out.println("아이디 : "+ Controller.loginedMember.getLoginId());
        System.out.println("가입일 : "+ Controller.loginedMember.getRegDate());
        System.out.println("회원번호 : "+ Controller.loginedMember.getId());
    }
}