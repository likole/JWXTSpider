package cn.likole.DetailInfo;

import cn.likole.util.DBUtil;

import java.io.IOException;
import java.rmi.server.ExportException;
import java.sql.*;

/**
 * Created by likole on 8/24/17.
 */
public class DetailInfo {

    public static void start(int n) {

        for (int i = 0; i < n; i++) {
            new Thread(new DetailInfoThread()).run();
        }

    }

}
