package cn.planifolia.van.util;

import java.io.*;

/**
 * Created by Intellij IDEA
 *
 * @author Planifolia.Van
 * @version 1.0
 * @date 2022/8/16 11:27
 */
public class FileCopyTool {

    /**
     * 字节流文件拷贝的工具方法
     * @param fis 文件输入流
     * @param fos 文件输出流
     */
    public static void copy(FileInputStream fis , FileOutputStream fos) {

        int count;
        byte[] bytes = new byte[1024];

        try {
            while ((count= fis.read(bytes))>0){
                fos.write(bytes,0,count);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            closeStream(fis, fos);
        }
    }

    public static void copy(FileReader fr , FileWriter fw){
        int count;
        char[] chars = new char[1024];

        try {
            while ((count= fr.read(chars))>0){
                fw.write(chars,0,count);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            closeStream(fr, fw);
        }
    }



    /**
     * 关闭资源的工具方法
     * @param is 输入流
     * @param os 输出流
     */
    public static void closeStream(Closeable is , Closeable os){
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
