package code.homework;

import java.sql.*;
import java.util.*;

/**
 * 实现订单管理系统
 * <p>
 * 订单数据:
 * order_id,  唯一
 * order_amount(订单金额),
 * order_user_id(订单付款人id),
 * order_user_name(订单付款人名字),
 * order_merchant_id(订单商家id)
 * order_merchant_name(订单商家名称)
 * <p>
 * 要求:
 * 提供一个用户客户端输入(Scaner)的程序,界面程序,给出相关选项
 * 如界面如下:
 * 1.创建订单
 * 2.修改订单
 * 3.删除订单
 * 4.查询订单
 * <p>
 * <p>
 * 生成订单,
 * 修改订单,基于order_id进行修改,可能修改的是金额,也可能是其他字段,具体修改啥,不确定
 * 删除订单,基于order_id进行删除
 * 查询,可以查询一条,基于order_id
 * 也可以基于其他字段,查询多条
 **/
public class Topic2 {
    private static final String URL = "jdbc:mysql://localhost:3306/cakes";
    private static final String USER_NAME = "root";
    private static final String PASSWD = "123456";

    public static void main(String[] args) {
        HashMap<String, Object> CountMap = new HashMap<>();

//        CountMap.put("order_amount", 2700);
//        //订单付款人ID  userID203
//        CountMap.put("order_user_id", "userID1000");
//        //输入用户
//        CountMap.put("order_user_name", "zhoufeng");
//        //商家ID从2000开始Business
//        CountMap.put("order_merchant_id", "businessID1000");
//        //传入的
//        CountMap.put("order_merchant_name", "shitou");
//        System.out.println(CountMap);
//
//
//        insertDB(CountMap);

//        //查询一个orderID的值
//        queryById(1000001111);
//
//        //更新orderID的值
//        UpdatequeryById(100001,"Amount","10");

        //删除值
        deleteDate(100002);
    }


    /**
     *
     * @param orderID 需要删除的订单的ID
     */
    private static void deleteDate(Integer orderID) {
        // 2.建立  // 3.建立Statement
        Connection connection = null;
        Statement statement = null;
        String insterDate = "";
        try {
            //4.连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWD);
            statement = connection.createStatement();


            insterDate = String.format("DELETE FROM dateorder WHERE id=%s", orderID);
            System.out.println(insterDate);

            int i1 = statement.executeUpdate(insterDate);
            System.out.println(i1);

            // 4.执行
            if (i1 == 0) {
                System.out.println("当前传入的需要更新的ID不存在");
            } else {
                System.out.println("更新完成:" + i1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5.资源关闭
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param hashMap 传入一个map文件,里面需要传入相应的想添加的值
     */
    public static void insertDB(HashMap<String, Object> hashMap) {
        ArrayList arrayList = new ArrayList();
        Set<Map.Entry<String, Object>> entries = hashMap.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
            arrayList.add(entry.getValue());
        }

        // 2.建立
        Connection connection = null;

        // 3.建立Statement
        Statement statement = null;
        try {
            //4.连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWD);
            statement = connection.createStatement();

            //[userID1000, zhoufeng, businessID1000, 2700, 石头电子旗舰店]
            //insert into dateorder(Userid,username,Merchantid,Amount,Merchantname) values('userID1000','zhoufeng','businessID1000',2700.00,'shitou')
            String insterDate = String.format("insert into dateorder(Userid,username,Merchantid,Amount,Merchantname) values('%s','%s','%s',%s,'%s')",
                    arrayList.get(0), arrayList.get(1), arrayList.get(2), Double.valueOf(arrayList.get(3).toString()), arrayList.get(4));
            System.out.println(insterDate);

            // 4.执行
            int effectRows = statement.executeUpdate(insterDate);

            //5.提示了更新了多少行数据了
            System.out.println("effectRows:" + effectRows);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5.资源关闭
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * @param orderID 传入的需要查询的orderID
     */
    public static void queryById(Integer orderID) {
        // 2.建立
        Connection connection = null;

        // 3.建立Statement
        Statement statement = null;
        try {
            //4.连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWD);
            statement = connection.createStatement();

            String insterDate = String.format("SELECT * FROM dateorder WHERE id='%s'", orderID);
            System.out.println(insterDate);

            // 4.执行
            ResultSet resultSet = statement.executeQuery(insterDate);

            if (resultSet.next()) {
                //5.提示了更新了多少行数据了
                System.out.println("数据为:" + resultSet.getString(2));
            } else {
                System.out.println("没有这个订单信息");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5.资源关闭
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * @param orderID 传入需要修改的订单ID
     * @param columnName 修改的字段的名字
     * @param value 需要修改为的字段的值
     */
    public static void UpdatequeryById(Integer orderID, String columnName, String value) {
        // 2.建立  // 3.建立Statement
        Connection connection = null;
        Statement statement = null;
        String insterDate = "";
        try {
            //4.连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWD);
            statement = connection.createStatement();

            //UPDATE dateorder SET Amount=2400.56, username='USA' WHERE id=100000;
            //Userid,username,Merchantid,Amount,Merchantname
            if (columnName.equals("Amount")) {
                insterDate = String.format("UPDATE dateorder SET %s=%s WHERE id=%s", columnName, value, orderID);
            } else {
                insterDate = String.format("UPDATE dateorder SET %s='%s' WHERE id=%s", columnName, value, orderID);
            }

            System.out.println(insterDate);

            // 4.执行
            int i = statement.executeUpdate(insterDate);
            if (i == 0) {
                System.out.println("当前传入的需要更新的ID不存在");
            } else {
                System.out.println("更新完成:" + i);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5.资源关闭
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}









