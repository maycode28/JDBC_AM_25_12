package org.example;

import org.example.controller.ArticleController;
import org.example.controller.Controller;
import org.example.controller.MemberController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

    public void run() {
        System.out.println("==프로그램 시작==");
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("명령어 > ");
            String cmd = sc.nextLine().trim();

            Connection conn = null;

            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            String url = "jdbc:mariadb://127.0.0.1:3306/JDBC_AM_25_12?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";

            try {
                conn = DriverManager.getConnection(url, "root", "");

                int actionResult = action(conn, sc, cmd);

                if (actionResult == -1) {
                    System.out.println("==프로그램 종료==");
                    sc.close();
                    break;
                }

            } catch (SQLException e) {
                System.out.println("에러 1 : " + e);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int action(Connection conn, Scanner sc, String cmd) {

        if (cmd.equals("exit")) {
            return -1;
        }

        MemberController memberController = new MemberController(sc, conn);
        ArticleController articleController = new ArticleController(sc, conn);

        if (cmd.equals("member join")) {
            if (Controller.isLogined()){
                System.out.println("로그아웃이 필요합니다.");
                return 0;
            }
            memberController.doJoin();
        }else if (cmd.equals("member login")) {
            if (Controller.isLogined()){
                System.out.println("로그아웃이 필요합니다.");
                return 0;
            }
            memberController.doLogin();
        } else if (cmd.equals("member logout")) {
            if (!Controller.isLogined()){
                System.out.println("로그인이 필요합니다.");
                return 0;
            }
            memberController.doLogout();
        }else if (cmd.equals("member profile")) {
            if (!Controller.isLogined()){
                System.out.println("로그인이 필요합니다.");
                return 0;
            }
            memberController.showProfile();
        }
        else if (cmd.equals("article write")) {
            if (!Controller.isLogined()){
                System.out.println("로그인이 필요합니다.");
                return 0;
            }
            articleController.doWrite();
        } else if (cmd.startsWith("article list")) {
            articleController.showList(cmd);
        } else if (cmd.startsWith("article modify")) {
            if (!Controller.isLogined()){
                System.out.println("로그인이 필요합니다.");
                return 0;
            }
            articleController.doModify(cmd);
        } else if (cmd.startsWith("article detail")) {
            articleController.showDetail(cmd);
        } else if (cmd.startsWith("article delete")) {
            if (!Controller.isLogined()){
                System.out.println("로그인이 필요합니다.");
                return 0;
            }
            articleController.doDelete(cmd);
        } else {
            System.out.println("사용할 수 없는 명령어입니다");
        }


        return 0;
    }
}