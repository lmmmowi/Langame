package com.lmmmowi.langame.util;

import java.io.*;

/**
 * @Author: mowi
 * @Date: 2018/9/25
 * @Description:
 */
public class FileKit {

    public static String readContent(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String s;
            StringBuilder stringBuilder = new StringBuilder();
            while ((s = reader.readLine()) != null) {
                stringBuilder.append(s).append("\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeContent(File file, String content) {
        PrintWriter printWriter = null;

        try {
            file.getParentFile().mkdirs();

            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            printWriter.print(content);
            printWriter.flush();
        } catch (IOException e) {
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
