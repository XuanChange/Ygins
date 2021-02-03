package com.example.demo.chx;

import org.junit.Test;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class VolatileTest3 {

//    public static String TimeOfLongToStr(long time) {
//        Assert.notNull(time, "time is null");
//        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
//
//    }

    @Test
    public void test() {
        System.out.println(10 / 0);
    }

}

class HasStatic {

}
