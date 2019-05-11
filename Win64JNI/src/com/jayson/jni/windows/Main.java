package com.jayson.jni.windows;

public class Main {
    public static void main(String[] args) {
        NativeMethods.HelloWorldStatic();
        new NativeMethods().HelloWorldDynamic();
    }
}
