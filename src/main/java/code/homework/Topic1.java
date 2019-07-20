package code.homework;

import org.junit.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 回顾之前的一个作业, day02/homework.Topic11
 * <p>
 * 从文件取出数据之后,将数据放到数据库中去,
 * <p>
 * 注: 注意代码的健壮性 以及 编写相应的测试用例
 * <p>
 * 要求: 经过几天的代码编写, 请尽量用一些设计的手段进去
 *
 * @author haoc
 */


/**
 * 思路:
 * 取出来数据生成一个ArrayList<Map<String, String>> dataArray这样
 * 然后依次取出来数据,插入数据库
 */
public class Topic1 {
    private static final String URL = "jdbc:mysql://localhost:3306/cakes";
    private static final String USER_NAME = "root";
    private static final String PASSWD = "123456";

    public static void main(String[] args) {
        //获取数据
        ArrayList fileList = testGetFile();

        //插入数据库
        insertDB(fileList);
    }

    /**
     * 获取文件数据,返回一个map文件
     */
    public static ArrayList testGetFile() {
        ArrayList<Map<String, String>> dataArray = new ArrayList<>();
        FileInputStream fs = null;
        try {
            //1.定义文件读取流
            fs = new FileInputStream("/Users/wangshijie/IdeaProjects/jdbc-demos/src/main/java/code/homework/data/getResult");

            InputStreamReader isr = new InputStreamReader(fs);
            BufferedReader br = new BufferedReader(isr);

            String line = "";

            while ((line = br.readLine()) != null) {
                HashMap<String, String> dataHashMap = new HashMap<>();
                String[] split = line.split(" ");
                //放到map里面
                dataHashMap.put("username", split[0].trim());
                dataHashMap.put("id", split[1].trim());
                dataHashMap.put("address", split[2].trim());

                //增加到list中
                dataArray.add(dataHashMap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(dataArray);
        return dataArray;

    }


    /**
     * insert数据库
     */
    public static void insertDB(ArrayList<Map<String,String>> arrayList) {
        //便利出来数据
        for (int i = 0; i < arrayList.size(); i++) {
            final Map<String, String> stringStringMap = arrayList.get(i);
            String instertName=stringStringMap.get("username");
            String instertAddress=stringStringMap.get("address");

            System.out.println(instertAddress);
            System.out.println(instertName);
            //insert数据库
            // 2.建立
            Connection connection = null;

            // 3.建立Statement
            Statement statement = null;
            try {
                //4.连接
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWD);
                statement = connection.createStatement();
                String insterDate= String.format("insert into workteam(username,address) values('%s','%s')",instertName,instertAddress);

                // 4.执行
                int effectRows = statement.executeUpdate(insterDate);

                //5.提示了更新了多少行数据了
                System.out.println("effectRows:" + effectRows);

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
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
}
