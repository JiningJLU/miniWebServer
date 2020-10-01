package com.Util;

import java.io.Closeable;
import java.io.IOException;

public class IOCloseUtil {
    public static void closeAll(Closeable... things){
        for(Closeable c:things){
            if(c != null){
                try {
                    c.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}