import com.qiniu.http.Response;

import java.io.File;
import java.util.ArrayList;

public class MainThread {

    private static final int MODE_COVER = 1;
    private static final int MODE_BACKUP = 2;

    public static int successCount = 0;
    public static int failedCount = 0;

    private static UploadUtil mUploadDemo;
    private static LocalProperty mLocalProperty;

    public static void pase(String filePath, int mode) throws Exception {

        mLocalProperty = LocalProperty.getLocalProperty();

        if (mLocalProperty == null) {
            mLocalProperty = LocalProperty.getLocalPropertyByConsole();
        }

        mUploadDemo = new UploadUtil(mLocalProperty.getAccessKey(),
                mLocalProperty.getSecretKey(), mLocalProperty.getBucketName());

        File targetFile = new File(filePath);

        targetFile.setWritable(true);
        System.out.println(targetFile.exists());
        System.out.println(targetFile.canWrite());

        if (!targetFile.exists() || !targetFile.canWrite()) {
            Utils.println("Error ! File not found or can't be write !-----文件不能写或者不存在");
            return;
        }

        Utils.println("Start parse file :" + targetFile.getAbsolutePath()
                + "\n");
        String blogString = Utils.getStringFromFile(targetFile, "UTF-8");

        File imgFile;


        ArrayList<String> tagsArrayList = Utils.getImageTags(blogString);

        if (tagsArrayList.size() > 0) {

            for (int i = 0; i < tagsArrayList.size(); i++) {
                String imageTag = tagsArrayList.get(i);
                String imgFilePath = Utils.getFilePathFromImageTag(imageTag,filePath);
                String altString = Utils.getAltFromImageTag(imageTag);
                Utils.println("Find Local image :" + imgFilePath + "\n");


                imgFile = new File(imgFilePath);
                System.out.println(imgFile.setReadable(true));

                System.out.println(imgFile.exists() + "----" + imgFilePath);

                if (!imgFile.exists() || !imgFile.canRead()) {
                    Utils.println("File not found or can't be write !------文件不能写或者不存在\n");
                    failedCount++;
                    continue;
                }

                Utils.println("Start upload " + imgFile.getName()
                        + " to QiNiu service...\n");
                Response response = mUploadDemo.upload(
                        imgFile.getAbsolutePath(), imgFile.getName());
                if (response.isOK()) {
                    String qiniuUrl = mLocalProperty.getImageUrl(imgFile
                            .getName());
                    Utils.println("Upload successful ! The url of image is "
                            + qiniuUrl + "\n");
                    blogString = blogString.replace(imageTag,
                            Utils.getImageTagByUrl(altString, qiniuUrl));
                    successCount++;
                } else {
                    failedCount++;
                    Utils.println("Error ! " + response.error);
                }

            }

            switch (mode) {
                case MODE_COVER:
                    Utils.writeStringToFile(blogString, targetFile);
                    Utils.println("Save as " + targetFile.getAbsolutePath() + "\n");
                    break;
                case MODE_BACKUP:
                    String fileName = Utils.getBackUpFileName(targetFile.getName());
                    File backupFile = new File(targetFile.getParentFile(), fileName);
                    Utils.writeStringToFile(blogString, backupFile);
                    Utils.println("Save as " + backupFile.getAbsolutePath() + "\n");
                    break;
                default:
                    throw new IllegalArgumentException("ERROR MODE " + mode);
            }

            Utils.println("Upload image completed ! Success:" + successCount
                    + "   Failed:" + failedCount);
        } else {
            Utils.println("Didn't find any local image tag.");
        }

    }
}
