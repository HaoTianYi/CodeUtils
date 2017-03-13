package utils;

import java.io.*;
import java.text.SimpleDateFormat;

/**
 * @author www.haotianyi.win
 * @version V1.0
 * @Description: TODO
 * @date 2017/3/4 9:46
 */
public class FileUtils {
    public static final String tragetFile = "E:\\blog\\source\\_posts";

    public static boolean writeFile(String name, String content) {
        File file = new File(tragetFile, name);
        if (name == "conf/wei.txt") {
            file = new File(name);
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            writer.write(content);
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }

    public static void writeHeader(String name, long time) {
        SimpleDateFormat ft =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String[] strings = name.split("\\.");

        File traget = new File(tragetFile, name);
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(traget)));
            writer.write("---");
            writer.newLine();
            writer.write("title: " + strings[0]);
            writer.newLine();
            writer.write("time: " + ft.format(time));
            writer.newLine();
            writer.write("categories: Android");
            writer.newLine();
            writer.write("tags: ");
            writer.newLine();
            writer.write("---");
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFooter(File traget) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(traget, true)));
            writer.write("     ");
            writer.newLine();
            writer.write("## 最后");
            writer.newLine();
            writer.write("时间仓促，有所错误在所难免，多谢您的谅解");
            writer.newLine();
            writer.write("博客没有设置评论系统，如果您对文章感兴趣或者发现错误，请您联系作者：");
            writer.newLine();
            writer.write("[天意](http://www.haotianyi.win/)    hao.ty@haotianyi.win");
            writer.newLine();
            writer.write("[蒲公](http://www.pugong.studio/)    pg@pugong.studio");
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean readFile(File file, String name) {
        String[] strings = name.split("\\.");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.equals("[TOC]") || line.equals("# " + strings[0])) {

                } else {
                    writeFile(name, line);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeFooter(new File(tragetFile, name));
        return true;
    }
}
