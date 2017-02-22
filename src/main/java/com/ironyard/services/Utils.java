package com.ironyard.services;

/**
 * Created by wailm.yousif on 2/14/17.
 */
public class Utils
{
    public static String getFileNameWithoutExtension(String fileName)
    {
        String[] strArr = fileName.split("\\.");
        String fileNameWithoutExt = "";

        System.out.println("strArr.length=" + strArr.length + "#");
        if (strArr.length < 2)
        {
            fileNameWithoutExt = fileName;
        }
        else
        {
            int lastIdx = fileName.lastIndexOf(strArr[strArr.length-1]);
            fileNameWithoutExt = fileName.substring(0, lastIdx-1);
        }

        return fileNameWithoutExt;
    }


    public static String getPageSizeOptions(Integer currPageSize, Integer minVal, Integer maxVal, Integer inc)
    {
        String options = "";
        for (int i=minVal; i < maxVal+1; i+=inc)
        {
            options = options + "<option value=\"" + String.valueOf(i) + "\" ";
            if (i == currPageSize)
                options = options + "selected";
            options = options + ">" + String.valueOf(i) + "</option>";
        }
        return options;
    }


    public static String getSortByOptions(Integer currSortBy, String[] optsValues)
    {
        String options = "";
        for (int i=1; i < 3; i++)
        {
            options = options + "<option value=\"" + String.valueOf(i) + "\" ";
            if (i == currSortBy)
                options = options + "selected";
            options = options + ">" + optsValues[i-1] + "</option>";
        }
        return options;
    }

}
