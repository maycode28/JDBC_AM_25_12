package org.example.service;

import org.example.Article;
import org.example.dao.ArticleDao;

import java.sql.Connection;
import java.util.List;

public class ArticleService {
    private ArticleDao articleDao = new ArticleDao();

    public boolean isArticleExists(Connection conn, int id) {
        return articleDao.isArticleExists(conn, id);
    }

    public void doModify(Connection conn, int id, String title, String body) {
        articleDao.doModify(conn,id,title, body);
    }

    public int doWrite(Connection conn, String title, String body) {
        return articleDao.doWrite(conn,title, body);
    }

    public List<Article> showList(Connection conn) {
        return articleDao.showList(conn);
    }
}
