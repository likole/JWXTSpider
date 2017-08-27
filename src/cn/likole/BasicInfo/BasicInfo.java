package cn.likole.BasicInfo;

import cn.likole.util.DBUtil;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by likole on 8/22/17.
 */
public class BasicInfo {

    public static void start() {

        //create spider
        Spider spider = Spider.create(new BasicInfoSpider());

        //add urls
        Connection connection = DBUtil.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM ids LEFT JOIN (SELECT id as i FROM BASIC_INFO) AS b ON b.i=ids.id WHERE b.i IS NULL AND id LIKE '013" +
                    "%'");

            while (rs.next()) {
                System.out.println(rs.getString("id") + "加入队列");
                spider.addUrl("http://202.207.0.237:8081/setReportParams?resultPage=http://202.207.0.237:8081/reportFiles/cj/cj_zwcjd.jsp&LS_XH=" + rs.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //start
        spider.addPipeline(new BasicInfoPipeline()).thread(8).run();
    }

    public static void main(String[] args) {
        start();
    }
}
