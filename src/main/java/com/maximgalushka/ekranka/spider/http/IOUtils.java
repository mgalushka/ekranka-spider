package com.maximgalushka.ekranka.spider.http;

import java.io.*;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 07/09/2011
 */
public final class IOUtils {

    /**
     * @param is input stream
     * @param encoding file encoding
     * @return String with file content
     * @throws IOException if
     */
    public static String convertStreamToString(InputStream is, String encoding)
            throws IOException {
        /*
         * To convert the InputStream to String we use the
         * Reader.read(char[] buffer) method. We iterate until the
         * Reader return -1 which means there's no more data to
         * read. We use the StringWriter class to produce the string.
         */
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        // "windows-1251", "utf-8"
                        new InputStreamReader(is, encoding));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }


    private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };

    public static String formatFileName(String name){
        for(char c : ILLEGAL_CHARACTERS){
            name = name.replace(c, ' ');
        }
        return name;
    }

    /**
     * @param filePath the name of the file to open.
     *                  Not sure if it can accept URLs or just filenames.
     *                  Encoding is default: UTF-8
     * @return string converted from file
     * @throws java.io.IOException if
     */
    public static String readFileAsString(String filePath)
            throws IOException{

        return readFileAsString(filePath, "utf-8");
    }

    /**
     * @param filePath the name of the file to open.
     *                  Not sure if it can accept URLs or just filenames.
     * @param encoding encoding to convert file from
     * @return string converted from file
     * @throws java.io.IOException if
     */
    public static String readFileAsString(String filePath, String encoding)
            throws IOException{
        return convertStreamToString(new FileInputStream(filePath), encoding);
    }
}
