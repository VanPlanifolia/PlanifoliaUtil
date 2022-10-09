package cn.planifolia.van.util;

import java.sql.*;

/**
 * Created by Intellij IDEA<br>
 * 封装了简单的jdbc操作，获取连接，查询，关闭连接，目前只支持预编译
 * @author Planifolia.Van
 * @version 1.0
 * @date 2022/8/25 11:24
 */
public class SimpleJDBC {

    private static String CONNECTION_URL = "jdbc:mysql://localhost:3306/DBNAME";
    private static PreparedStatement preparedStatementGb;
    private static Connection connectionGb;
    private static ResultSet resultSetGb;

    static {
        //五步走第一步 加载驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     *
     * @param dbName 连接的数据库名字
     * @return 数据库连接
     * @throws Exception
     */
    public static Connection getConnection(String dbName) throws Exception {
        // 五步走第二部 创建连接
        return DriverManager
                .getConnection(CONNECTION_URL.replace("DBNAME", dbName), "root", "010713");
    }

    /**
     * 不会自动关闭连接的查询方法
     *
     * @param sql        sql语句
     * @param connection 连接
     * @param args       参数
     * @return 查询结果集
     * @throws Exception
     */
    public static ResultSet simpleQuery(String sql, Connection connection, Object[] args) throws Exception {

        preparedStatementGb = connection.prepareStatement(sql);

        if (args != null && args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                preparedStatementGb.setObject(i + 1, args[i]);
            }
        }

        return preparedStatementGb.executeQuery();
    }


    /**
     * 不会自动关闭的更新语句
     *
     * @param sql        查询语句
     * @param connection 连接
     * @param args       参数
     * @return 影响的行数
     * @throws Exception
     */
    public static int simpleUpdate(String sql, Connection connection, Object[] args) throws Exception {
        preparedStatementGb = connection.prepareStatement(sql);
        if (args != null && args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                preparedStatementGb.setObject(i + 1, args[i]);
            }
        }
        return preparedStatementGb.executeUpdate();
    }


    /**
     * 关闭数据库连接
     *
     * @param connection        数据库的连接
     * @param preparedStatement 数据库的ps对象
     */
    public static void simpleClose(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("con 关闭");
        }
        if (connectionGb != null) {
            connectionGb.close();
            connectionGb = null;
            System.out.println("conGb 关闭");

        }
        if (preparedStatement != null) {
            preparedStatement.close();
            System.out.println("ps 关闭");

        }
        if (preparedStatementGb != null) {
            preparedStatementGb.close();
            preparedStatementGb = null;
            System.out.println("psgb 关闭");

        }
        if (resultSet != null) {
            resultSet.close();
            System.out.println("rs 关闭");

        }
        if (resultSetGb != null) {
            resultSetGb.close();
            resultSetGb = null;
            System.out.println("rsGb 关闭");

        }
    }

}
