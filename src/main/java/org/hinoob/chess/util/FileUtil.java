package org.hinoob.chess.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtil {

    public static void copy(File src, File dest) {
        if(src.isDirectory()) {
            if(!dest.exists()) dest.mkdirs();

            String[] children = src.list();
            if(children != null) {
                for(String child : children) {
                    copy(new File(src, child), new File(dest, child));
                }
            }
        } else {
            try {
                FileInputStream inputStream = new FileInputStream(src);
                FileOutputStream outputStream = new FileOutputStream(dest);

                byte[] buffer = new byte[1024];
                int length;
                while((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                inputStream.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
