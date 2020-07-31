package com.zjq.library_01.util;

public class Tool {
    public static boolean checkImgFile(String fileName) {
        if(fileName!=null) {
            String suffixList = "jpg,gif,png,ico,bmp,jpeg";
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            if (suffixList.contains(suffix.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
