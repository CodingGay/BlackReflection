package top.niunaijun.app.bean;

import android.util.Log;

/**
 * Created by Milk on 2022/2/16.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 * 此处无Bug
 */
public class TestReflection {
    public static final String TAG = "TestConstructor";

    public String mContextValue = "context value";
    public static String sStaticValue = "static value";

    public TestReflection(String a) {
        Log.d(TAG, "Constructor called :" + a);
    }

    public TestReflection(String a, String b) {
        Log.d(TAG, "Constructor called : a = " + a + ", b = " + b);
    }

    public String testContextInvoke(String a, int b) {
        Log.d(TAG, "Context invoke: a = " + a + ", b = " + b);
        return a + b;
    }

    public static String testStaticInvoke(String a, int b) {
        Log.d(TAG, "Static invoke: a = " + a + ", b = " + b);
        return a + b;
    }

    public static String testParamClassName(String a, int b) {
        Log.d(TAG, "testParamClassName: a = " + a + ", b = " + b);
        return a + b;
    }
}
