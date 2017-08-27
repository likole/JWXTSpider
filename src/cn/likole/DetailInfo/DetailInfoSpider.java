package cn.likole.DetailInfo;

import cn.likole.util.DBUtil;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

/**
 * Created by likole on 8/24/17.
 */
public class DetailInfoSpider {

    private OkHttpClient okHttpClient = new OkHttpClient();

    public void start(String base_url, String username, String password) throws IOException, SQLException {

        //login data
        RequestBody formBody = new FormBody.Builder()
                .add("zjh", username)
                .add("mm", password)
                .build();

        //login request
        Request request = new Request.Builder()
                .url(base_url + "/loginAction.do")
                .post(formBody)
                .build();

        //login
        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) return;
        if (response.body().string().contains("登陆")) return;

        String cookie = response.header("set-cookie");

        //info request
        request = new Request.Builder()
                .url(base_url + "/xjInfoAction.do?oper=xjxx")
                .addHeader("cookie", cookie)
                .build();

        //get info
        response = okHttpClient.newCall(request).execute();

        //parse info
        Document document = Jsoup.parse(response.body().string());
        String id = Xsoup.select(document, "//table[@class=\"titleTop3\"][1]//tr[1]/td[2]/text()").get().trim();
        String kq = Xsoup.select(document, "//table[@class=\"titleTop3\"][1]//tr[8]/td[4]/text()").get().trim();//考区
        String byzx = Xsoup.select(document, "//table[@class=\"titleTop3\"][1]//tr[9]/td[2]/text()").get().trim();//毕业中学
        String gkzf = Xsoup.select(document, "//table[@class=\"titleTop3\"][1]//tr[9]/td[4]/text()").get().trim();//高考总分
        String lqh = Xsoup.select(document, "//table[@class=\"titleTop3\"][1]//tr[10]/td[2]/text()").get().trim();//录取号
        String gkksh = Xsoup.select(document, "//table[@class=\"titleTop3\"][1]//tr[10]/td[4]/text()").get().trim();//高考考生号
        String rxksyz = Xsoup.select(document, "//table[@class=\"titleTop3\"][1]//tr[11]/td[2]/text()").get().trim();//入学考试语种
        String txdz = Xsoup.select(document, "//table[@class=\"titleTop3\"][1]//tr[11]/td[4]/text()").get().trim();//通讯地址
        String xq = Xsoup.select(document, "//table[@class=\"titleTop3\"][1]//tr[17]/td[2]/text()").get().trim();//校区
        String wyyz = Xsoup.select(document, "//table[@class=\"titleTop3\"][1]//tr[18]/td[2]/text()").get().trim();//外语语种

        System.out.println(id + "已抓取");

        //save info
        java.sql.Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO DETAIL_INFO VALUES (?,?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, kq);
        preparedStatement.setString(3, byzx);
        preparedStatement.setString(4, gkzf);
        preparedStatement.setString(5, lqh);
        preparedStatement.setString(6, gkksh);
        preparedStatement.setString(7, rxksyz);
        preparedStatement.setString(8, txdz);
        preparedStatement.setString(9, xq);
        preparedStatement.setString(10, wyyz);
        preparedStatement.execute();
        preparedStatement.close();

    }
}
