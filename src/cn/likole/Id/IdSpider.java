package cn.likole.Id;

import cn.likole.util.DBUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by likole on 8/26/17.
 */
public class IdSpider implements PageProcessor {

    Site site = Site.me();

    @Override
    public void process(Page page) {
        List<String> strings = page.getHtml().xpath("//div[@id=\"managelist\"]//tr/td[4]/div/text()").all();
        Connection connection = DBUtil.getConnection();
        for (String s : strings) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO  ids VALUES (?)");
                preparedStatement.setString(1, s.replace("@mail.imu.edu.cn", ""));
                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
