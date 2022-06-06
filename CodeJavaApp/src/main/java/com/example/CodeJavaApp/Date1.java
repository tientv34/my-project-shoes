package com.example.CodeJavaApp;

import java.sql.Timestamp;
import java.util.Date;

public class Date1 {
    public static void main(String[] args) {

//        LocalDate date1 = LocalDate.now();
//        LocalDateTime date = LocalDateTime.now();
//
//        Long miliSeconds = System.currentTimeMillis();
//
//        Instant instant = Instant.now();
//        String d1 = instant.toString();
//        System.out.println("d1: " +d1);
//
//        OffsetDateTime d2 = OffsetDateTime.now(ZoneOffset.UTC);
//        System.out.println("d2: "+d2);
//
//
//        System.out.println("Date: "+ date1);
//
//        System.out.println("Date: "+ date);
//        System.out.println("MiliSeconds: "+ miliSeconds);
//        Locale localeEN = new Locale("en", "EN");
//        NumberFormat en = NumberFormat.getInstance(localeEN);
//
//        // đối với số có kiểu long được định dạng theo chuẩn của nước Anh
//        // thì phần ngàn của số được phân cách bằng dấu phẩy
//        double longNumber = 12345678d;
//        String str1 = en.format(longNumber);
//        System.out.println("Số " + longNumber + " sau khi định dạng = " + str1);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
        Date date = new Date();
        System.out.println(new Timestamp(date.getTime()));
        System.out.println(timestamp.getTime());

    }
}
