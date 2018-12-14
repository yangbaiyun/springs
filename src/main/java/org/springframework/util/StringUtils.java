package org.springframework.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author chl
 * @date 2018/12/13 19:17
 */
public class StringUtils {



    public static String[] commaDelimitedListToStringArray(String s) {
        return delimitedListToStringArray(s, ",");
    }


    public static String[] delimitedListToStringArray(String s, String delimiter) {

        if(s==null)
            return new String[0];
        if(delimiter==null)
            return new String[]{s};

        List list=new LinkedList();
        int pos=0;
        int prePos=0;
        while((prePos=s.indexOf(delimiter,pos))!=-1)
        {
            list.add(s.substring(pos,prePos));
            pos=prePos+delimiter.length();
        }

        if(pos<=s.length())
            list.add(s.substring(pos));

        return (String[])list.toArray(new String[list.size()]);

    }

    public static String arrayToDelimitedString(Object[] arr, String delim) {
        if (arr == null)
            return "null";
        else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < arr.length; i++) {
                if (i > 0)
                    sb.append(delim);
                sb.append(arr[i]);
            }
            return sb.toString();
        }
    }

    public static String arrayToCommaDelimitedString(Object[] arr) {
        return arrayToDelimitedString(arr, ",");
    }





}
