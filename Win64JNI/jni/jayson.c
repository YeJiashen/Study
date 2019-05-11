// 如果要使用jni功能, 一定要引入这个头文件, 否则是C代码是无法与java代码进行通信的
#include<jni.h>
// 由于使用了输出功能, 所以要使用标准输入输出流这个头文件
#include<stdio.h>
// 将生成的头文件引入, 这里要注意只能用双引号的形式引入而不能是尖括号
// 这是由于尖括号标识编译器只在系统默认目录或者尖括号内的工作目录下搜索头文件, 并不去用户的工作目录下寻找
// 而双引号标识编译器现在用户的工作目录下搜索头文件, 如果搜索不到则到系统默认目录下去寻找
// 这个头文件是我们自己生成的, 不在系统默认目录下, 所以只能用双引号的形式引入
// 而前两个库都属于标准库文件, 可以使用尖括号和双引号两种形式引入
#include"com_jayson_jni_windows_NativeMethods.h"

/*
 * Class:     com_jayson_jni_windows_NativeMethods
 * Method:    HelloWorldStatic
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_jayson_jni_windows_NativeMethods_HelloWorldStatic
  (JNIEnv *env, jclass clz)
 {
    printf("Hello World! I am in jni, and i am static method;");
 }

/*
 * Class:     com_jayson_jni_windows_NativeMethods
 * Method:    HelloWorldDynamic
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_jayson_jni_windows_NativeMethods_HelloWorldDynamic
  (JNIEnv *env, jobject obj)
{
    printf("Hello World! I am in jni, and i am dynamic method;");
}