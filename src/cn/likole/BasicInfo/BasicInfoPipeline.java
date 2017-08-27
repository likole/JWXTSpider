package cn.likole.BasicInfo;

import cn.likole.util.DBUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by likole on 8/22/17.
 */
public class BasicInfoPipeline implements Pipeline {

    public void process(ResultItems resultItems, Task task){
        Connection connection = DBUtil.getConnection();

        try {
            PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO BASIC_INFO VALUES (?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, (String) resultItems.get("id"));
            preparedStatement.setString(2, (String) resultItems.get("name"));
            preparedStatement.setBoolean(3,"男".equals(resultItems.get("gender"))?true:false);
            preparedStatement.setString(4, (String) resultItems.get("idcard"));
            preparedStatement.setString(5, (String) resultItems.get("nation"));
            preparedStatement.setString(6, (String) resultItems.get("jg"));
            preparedStatement.setString(7, (String) resultItems.get("zz"));
            preparedStatement.setString(8, (String) resultItems.get("bj"));
            preparedStatement.setString(9, (String) resultItems.get("zy"));
            preparedStatement.setString(10, (String) resultItems.get("xs"));
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
