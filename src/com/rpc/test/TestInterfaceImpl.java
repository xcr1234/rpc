package com.rpc.test;

public class TestInterfaceImpl implements TestInterface {
    @Override
    public String say(String s) {
        //throw new RuntimeException("hello " + s);
        return   "hello " + s;
    }
}
