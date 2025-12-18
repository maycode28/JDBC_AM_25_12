package org.example.controller;

import org.example.Article;
import org.example.service.ArticleService;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleController {
    private Connection conn;
    private Scanner sc;
    private ArticleService articleService;

    public ArticleController(Scanner sc, Connection conn) {
        this.conn = conn;
        this.sc = sc;
        this.articleService = new ArticleService();
    }

    public int doWrite() {
        System.out.println("==글쓰기==");
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        int id = articleService.doWrite(conn,title,body);

        System.out.println(id + "번 글이 생성됨");
        return 0;

    }
    public int showList() {
        System.out.println("==목록==");

        List<Article> articles = articleService.showList(conn);

        if (articles.size() == 0) {
            System.out.println("게시글이 없습니다");
            return 0;
        }

        System.out.println("  번호  /   제목  ");
        for (org.example.Article article : articles) {
            System.out.printf("  %d     /   %s   \n", article.getId(), article.getTitle());
        }
        return 0;
    }

    public int doModify(String cmd) {
        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해");
            return 0;
        }


        if (!articleService.isArticleExists(conn, id)) {
            System.out.println(id + "번 글은 없음");
            return 0;
        }


        System.out.println("==수정==");
        System.out.print("새 제목 : ");
        String title = sc.nextLine().trim();
        System.out.print("새 내용 : ");
        String body = sc.nextLine().trim();

        articleService.doModify(conn, id,title,body);

        System.out.println(id + "번 글이 수정되었습니다.");
        return 0;
    }

    public int showDetail(String cmd) {
        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해");
            return 0;
        }

        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append("FROM article");
        sql.append("WHERE id = ?;", id);

        Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
        if (articleMap.isEmpty()) {
            System.out.println(id + "번 글은 없음");
            return 0;
        }

        System.out.println("==상세보기==");
        org.example.Article article = new org.example.Article(articleMap);

        System.out.println("번호 : " + article.getId());
        System.out.println("작성날짜 :  " + article.getRegDate());
        System.out.println("수정날짜 : " + article.getUpdateDate());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());
        return 0;
    }
}