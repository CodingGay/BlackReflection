package top.niunaijun.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;

import top.niunaijun.app.bean.TestReflection;
import top.niunaijun.app.ref.BRTestReflection;
import top.niunaijun.blackreflection.R;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestReflection testReflection = testBConstructor();
        Class<?> classReady = BRTestReflection.getRealClass();
        Log.d(TAG, "classReady: " + classReady);
        Log.d(TAG, "============================");

        // testInvokeContext
        BRTestReflection.get(testReflection).testContextInvoke("context", 0);

        Log.d(TAG, "============================");

        // testInvokeStatic
        BRTestReflection.get().testStaticInvoke("static", 0);

        Log.d(TAG, "============================");

        // testGetContextField
        String contextValue = BRTestReflection.get(testReflection).mContextValue();
        Log.d(TAG, "mContextValue: " + contextValue);

        Log.d(TAG, "============================");

        // testGetContextField
        String staticValue = BRTestReflection.get().sStaticValue();
        Log.d(TAG, "sStaticValue: " + staticValue);

        Log.d(TAG, "============================");

        // checkField
        Field field = BRTestReflection.get()._checkfakeField();
        Log.d(TAG, "checkField: " + field);

        Log.d(TAG, "============================");

        // setContextField
        BRTestReflection.get(testReflection)._setmContextValue(contextValue + " changed");
        Log.d(TAG, "mContextValue: " + BRTestReflection.get(testReflection).mContextValue());

        Log.d(TAG, "============================");

        // setStaticField
        BRTestReflection.get()._setsStaticValue(staticValue + " changed");
        Log.d(TAG, "sStaticValue: " + BRTestReflection.get().sStaticValue());

        Log.d(TAG, "============================");

        // use @BParamClassName
        BRTestReflection.get().testParamClassName("i am java.lang.String", 0);
    }

    private TestReflection testBConstructor() {
        // test BConstructor
        TestReflection testReflection = BRTestReflection.get()._new("a");
        Log.d(TAG, "TestConstructor: " + testReflection);

        testReflection = BRTestReflection.get()._new("a", "b");
        Log.d(TAG, "TestConstructor: " + testReflection);
        return testReflection;
    }
}