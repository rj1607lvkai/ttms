package com.example.demo2.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {
    public static   boolean isMobileNumber(String phone) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher matcher = p.matcher(phone);
        return matcher.matches();
    }
}
