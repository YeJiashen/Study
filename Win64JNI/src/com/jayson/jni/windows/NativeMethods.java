package com.jayson.jni.windows;

public class NativeMethods {
    // Load jni lib, it can load lib without extension name.
    // For example, if here is an lib with full name "jni_lib.dll".
    // Here can use jni_lib instead.
    static {
        System.loadLibrary("jni_lib");
    }

    // Declare a native method.
    public static native void HelloWorldStatic();

    // Declare a native method.
    public native void HelloWorldDynamic();
}