package write;

import utils.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

/**
 * @author www.haotianyi.win
 * @version V1.0
 * @Description: TODO
 * @date 2017/3/4 9:40
 */
public class AutoComplete {

    public final String tragetFile = "E:\\blog\\source\\_posts";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请您输入要写入的文件地址：");
        file(scanner.nextLine());
    }

    public static void file(String folder) {
        File fileDir = new File(folder);

        SimpleDateFormat sdf = new SimpleDateFormat("DDD");
        int today = Integer.parseInt(sdf.format(System.currentTimeMillis()));
        System.out.println(today);

        File[] listFiles = fileDir.listFiles();
        for (File f : listFiles) {
            Calendar cal = Calendar.getInstance();
            long time = f.lastModified();
            cal.setTimeInMillis(time);
            if (66 == Integer.parseInt(sdf.format(cal.getTime()))) {
                FileUtils.writeHeader(f.getName(), time);
                FileUtils.readFile(f, f.getName());
                System.out.println("又NB了-----，全部搞定");
            }
        }
    }
}
