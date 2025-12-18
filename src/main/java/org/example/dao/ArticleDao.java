package org.example.dao;

import org.example.Article;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleDao {
    public boolean isArticleExists(Connection conn, int id) {
        Map<String, Object> articleMap = new HashMap<>();
        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append("FROM article");
        sql.append("WHERE id = ?;", id);
        articleMap= DBUtil.selectRow(conn, sql);
        return !articleMap.isEmpty();
    }

    public int doWrite(Connection conn, String title, String body) {
        SecSql sql = new SecSql();
        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("title = ?,", title);
        sql.append("`body` = ?;", body);
        return DBUtil.insert(conn, sql);
    }

    public List<Article> showList(Connection conn) {
        List articles = new ArrayList<>();
        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append("FROM article");
        sql.append("ORDER BY id DESC");

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new org.example.Article(articleMap));
        }
        return articles;
    }

    public void doModify(Connection conn, int id,String title, String body) {
        SecSql sql = new SecSql();
        sql.append("UPDATE article");
        sql.append("SET updateDate = NOW()");
        if (title.length() > 0) {
            sql.append(", title = ?", title);
        }
        if (body.length() > 0) {
            sql.append(",`body` = ?", body);
        }
        sql.append("WHERE id = ?;", id);
        DBUtil.update(conn, sql);
    }
}
