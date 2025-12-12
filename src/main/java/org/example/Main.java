package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("==프로그램 시작==");

        Scanner sc = new Scanner(System.in);


        List<Article> articles = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        while (true) {
            System.out.print("명령어 > ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                break;
            }
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                String url = "jdbc:mariadb://127.0.0.1:3306/JDBC_AM_25_12?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                conn = DriverManager.getConnection(url, "root", "");

                if (cmd.equals("article write")) {
                    System.out.println("==글쓰기==");
                    System.out.print("제목 : ");
                    String title = sc.nextLine().trim();
                    System.out.print("내용 : ");
                    String body = sc.nextLine().trim();


                    String sql = "INSERT INTO article";
                    sql += " SET regDate = NOW(),";
                    sql += "updateDate = NOW(),";
                    sql += "title = '" + title + "',";
                    sql += "`body` = '" + body + "';";

                    pstmt = conn.prepareStatement(sql);
                    pstmt.executeUpdate();

                } else if (cmd.startsWith("article list")) {
                    String sql = "SELECT *";
                    sql += " FROM article";
                    sql += " ORDER BY id DESC;";

                    pstmt = conn.prepareStatement(sql);
                    rs = pstmt.executeQuery();
                    if (!rs.next()) {
                        System.out.println("게시글 없음");
                        continue;
                    }
                    String searchKeyword = cmd.substring("article list".length()).trim();


                    System.out.println("==목록==");
                    System.out.println("   번호    /    제목");
                    if (searchKeyword.isEmpty()) {
                        rs.previous();
                        while (rs.next()) {
                            System.out.printf("%d    /   %s\n", rs.getInt("id"), rs.getString("title"));
                        }
                    }else{
                        sql = "SELECT *";
                        sql += " FROM article";
                        sql += " WHERE title LIKE '%" + searchKeyword + "%'";
                        sql += " ORDER BY id DESC;";
                        pstmt = conn.prepareStatement(sql);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            System.out.printf("%d    /   %s\n", rs.getInt("id"), rs.getString("title"));
                        }
                    }

                }


            } catch (ClassNotFoundException e) {
                System.out.println("드라이버 로딩 실패" + e);
            } catch (SQLException e) {
                System.out.println("에러 : " + e);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if (pstmt != null && !pstmt.isClosed()) {
                        pstmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        System.out.println("==프로그램 종료==");
        sc.close();
    }
}