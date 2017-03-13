import java.io.File;
import java.util.Scanner;

/**
 * @author SiMaXiaoChen
 * @version v1.0
 */
public class Mian {
    public static void main(String[] args) throws Exception {
        Utils.println("请你输入文件夹");
        Scanner scanner = new Scanner(System.in);
        File file = new File(scanner.nextLine().trim());

        if (!file.exists()||file.isFile()) {
            Utils.println("文件夹不存在");
        }

        getFile(file);

        Utils.println("全部解析完成");
        Utils.println("成功个数"+MainThread.successCount+";"+"失败个数"+MainThread.failedCount);

    }

    private static void getFile(File file) throws Exception {
        File[] files = file.listFiles();

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile() && files[i].getName().endsWith(".md")) {
                MainThread.pase(files[i].getPath(), 2);
            }else if (files[i].isDirectory()){
                getFile(files[i]);
            }
        }
    }
}
