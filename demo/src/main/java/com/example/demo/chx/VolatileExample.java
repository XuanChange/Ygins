package com.example.demo.chx;

public class VolatileExample {
    private int a = 0;
    private volatile boolean flag = false;

    public void wirter() {
        a = 1;
        flag = true;
    }

    public void reader() {
        if (flag) {
            int i = a;
        }
    }
}
