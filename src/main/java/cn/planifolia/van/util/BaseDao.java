package cn.planifolia.van.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Intellij IDEA<br>
 * 基础的Dao类，其中封装了一些通用的dao操作，其他一些具体实现的只要继承这个类就能直接使用这些操作，并且可以拓展具体功能。
 *
 * @author Planifolia.Van
 * @version 1.0
 * @date 2022/9/14 14:04
 */
public class BaseDao {

    public Connection connection = null;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    private String dbName = "mydb04";

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    // 加载驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接并且初始化当前类里的连接对象connection，默认获取 ‘mydb04’ 数据库的连接
     */
    public void getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName,"root","010713");
    }

    /**
     * 关闭资源的方法
     *
     * @throws SQLException
     */
    public void closeSource() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
            resultSet = null;
        }
        if (preparedStatement != null) {
            preparedStatement.close();
            preparedStatement = null;
        }
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * 数据的修改sql工具方法，需要传入预编译的sql语句以及参数，能够自关闭资源。
     *
     * @param sql  修改的sql语句
     * @param args 预编译的参数
     * @return 影响的行数
     */
    public int dataModify(String sql, Object... args) {
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closeSource();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 返回ResultSet的数据库查询方法
     *
     * @param sql  查询的sql语句
     * @param args 查询的参数
     * @return 查询到的ResultSet结果集
     */
    private ResultSet dataQuery(String sql, Object... args) {
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * 查询单条记录的工具方法，是对结果集的进一步封装，封装成map对象，里面以key-value的形式去保存了查询出来的信息。
     *
     * @param sql  查询的sql语句
     * @param args 参数
     * @return 封装完毕的map对象
     */
    public Map<String, Object> dataQueryToMap(String sql, Object... args) {
        // 执行dataQuery来获取结果集
        ResultSet resultSet = dataQuery(sql, args);
        // 创建容器保存查询的数据
        Map<String, Object> entityMap = new HashMap<>();
        try {
            // 获取结果集的元数据以获取总列数以及列名方便存入map中
            ResultSetMetaData metaData = resultSet.getMetaData();
            if (resultSet.next()) {
                // 循环查询出结果集的列来存入map
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    entityMap.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
            }
            return entityMap;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                closeSource();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return entityMap;
    }

    /**
     * 查询多条记录并且会将结果集封装到List<Map>中
     * @param sql 查询多条的sql语句
     * @param args sql语句的参数
     * @return 封装好的ListMap
     */
    public List<Map<String, Object>> dataQueryToListMap(String sql, Object... args) {
        // 执行dataQuery来获取结果集
        ResultSet resultSet = dataQuery(sql, args);
        List<Map<String, Object>> maps = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();
                // 循环查询出结果集的列来存入map
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    map.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                maps.add(map);
            }
            return maps;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                closeSource();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return maps;
    }

}
