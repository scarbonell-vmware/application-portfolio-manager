package com.vmware.portfolio.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileUtils {

    public static String loadResourceFile(String name) throws IOException {
        String fileContent = "";
        InputStream in = FileUtils.class.getResourceAsStream("/"+name);
        StringWriter writer = new StringWriter();
        IOUtils.copy(in, writer, "UTF-8");
        fileContent = writer.toString();

        return fileContent;
    }

    public static BufferedReader getResourceAsStream(String name) throws IOException {
        InputStream in = FileUtils.class.getResourceAsStream("/"+name);
        return new BufferedReader(new InputStreamReader(in));
    }

}
