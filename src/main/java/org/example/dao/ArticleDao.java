package org.example.dao;

import org.example.Article;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {
    Connection conn;

    public ArticleDao(Connection conn) {
        this.conn = conn;
    }

    public int doWrite(String title, String body, int authorId) {
        SecSql sql = new SecSql();

        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("title = ?,", title);
        sql.append("`body`= ?,", body);
        sql.append("authorId= ?;", authorId);

        return DBUtil.insert(conn, sql);
    }
    public int getArticlesCount() {
        SecSql sql = new SecSql();
        sql.append("SELECT count(*) FROM article;");
        return DBUtil.selectRowIntValue(conn, sql);
    }
    public int getArticlesCount(String search) {
        SecSql sql = new SecSql();
        sql.append("SELECT count(*) FROM article");
        sql.append("WHERE title LIKE '%"+search+"%';");
        return DBUtil.selectRowIntValue(conn, sql);
    }

    public List<Article> getArticles(int pageNo) {
        SecSql sql = new SecSql();
        sql.append("SELECT a.id,a.regDate,a.updateDate,a.title,a.body,m.name");
        sql.append("FROM article a");
        sql.append("INNER JOIN `member` m");
        sql.append("ON a.authorId = m.Id");
        sql.append("ORDER BY id DESC");
        sql.append("LIMIT ?,10;", pageNo*10);

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

        List<Article> articles = new ArrayList<>();

        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }
        return articles;
    }
    public List<Article> getArticles(int pageNo, String search) {
        SecSql sql = new SecSql();
        sql.append("SELECT a.id,a.regDate,a.updateDate,a.title,a.body,m.name");
        sql.append("FROM article a");
        sql.append("INNER JOIN `member` m");
        sql.append("ON a.authorId = m.Id");
        sql.append("WHERE a.title LIKE '%"+search+"%'");
        sql.append("ORDER BY id DESC");
        sql.append("LIMIT ?,10;", pageNo*10);

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

        List<Article> articles = new ArrayList<>();

        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }
        return articles;
    }

    public Map<String, Object> getArticleById(int id) {
        SecSql sql = new SecSql();

        sql.append("SELECT a.id,a.regDate,a.updateDate,a.title,a.body,a.authorId,m.name");
        sql.append("FROM article a");
        sql.append("INNER JOIN `member` m");
        sql.append("ON a.authorId = m.Id");
        sql.append("WHERE a.id = ?", id);

        return DBUtil.selectRow(conn, sql);
    }

    public void doUpdate(int id, String title, String body) {
        SecSql sql = new SecSql();
        sql.append("UPDATE article");
        sql.append("SET updateDate = NOW()");
        if (title.length() > 0) {
            sql.append(",title = ?", title);
        }
        if (body.length() > 0) {
            sql.append(",`body` = ?", body);
        }
        sql.append("WHERE id = ?", id);

        DBUtil.update(conn, sql);
    }

    public void doDelete(int id) {
        SecSql sql = new SecSql();
        sql.append("DELETE FROM article");
        sql.append("WHERE id = ?", id);

        DBUtil.delete(conn, sql);
    }
}