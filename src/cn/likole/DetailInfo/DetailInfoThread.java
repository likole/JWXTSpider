package cn.likole.DetailInfo;

import cn.likole.util.DBUtil;

import java.sql.*;

/**
 * Created by likole on 8/24/17.
 */
public class DetailInfoThread implements Runnable {

    DetailInfoSpider detailInfoSpider = new DetailInfoSpider();

    Connection connection = DBUtil.getConnection();

    //node info
    String[] urls = {
//            "http://202.207.0.236:8081",
//            "http://202.207.0.236:8082",
//            "http://202.207.0.236:8083",
//            "http://202.207.0.236:8084",
//            "http://202.207.0.236:8085",
//            "http://202.207.0.236:8086",
            "http://202.207.0.237:8081",
            "http://202.207.0.237:8082",
            "http://202.207.0.237:8083",
            "http://202.207.0.237:8084",
            "http://202.207.0.237:8085",
            "http://202.207.0.237:8086",
//            "http://202.207.0.238:8081",
//            "http://202.207.0.238:8082",
//            "http://202.207.0.238:8083",
//            "http://202.207.0.238:8084",
//            "http://202.207.0.238:8085",
//            "http://202.207.0.238:8086"
    };


    @Override
    public void run() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM SID ORDER BY id ASC");
            while (rs.next()) {

                //judge if the info is exist
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM DETAIL_INFO WHERE id=?");
                preparedStatement.setString(1, rs.getString("id"));
                ResultSet rs1 = preparedStatement.executeQuery();

                //if not exist,start
                if (!rs1.next()) {
                    System.out.println(rs.getString("id") + "加入队列");
                    try {
                        detailInfoSpider.start(urls[(int) (Math.random() * urls.length)], rs.getString("id"), rs.getString("id"));
                    } catch (Exception e) {

                    }

                }

                //close the resource
                rs1.close();
                preparedStatement.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
