package top.niunaijun.app;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import top.niunaijun.app.bean.TestReflection;
import top.niunaijun.app.ref.BRActivityThread;
import top.niunaijun.app.ref.BRTestReflection;
import top.niunaijun.blackreflection.BlackReflection;
import top.niunaijun.blackreflection.R;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BlackReflection.CACHE = true;

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
        Field field = BRTestReflection.get()._check_fakeField();
        Log.d(TAG, "checkField: " + field);

        Log.d(TAG, "============================");

        // setContextField
        BRTestReflection.get(testReflection)._set_mContextValue(contextValue + " changed");
        Log.d(TAG, "mContextValue: " + BRTestReflection.get(testReflection).mContextValue());

        Log.d(TAG, "============================");

        // setStaticField
        BRTestReflection.get()._set_sStaticValue(staticValue + " changed");
        Log.d(TAG, "sStaticValue: " + BRTestReflection.get().sStaticValue());

        Log.d(TAG, "============================");

        // use @BParamClassName
        BRTestReflection.get().testParamClassName("i am java.lang.String", 0);

        findViewById(R.id.btn_start).setOnClickListener(v -> {
            try {
                int count = 10000;
                long l = System.currentTimeMillis();
                for (int i = 0; i < count; i++) {
                    testSystemReflection();
                }
                Log.d(TAG, "SystemReflection: " + (System.currentTimeMillis() - l) + "ms");

                l = System.currentTimeMillis();
                for (int i = 0; i < count; i++) {
                    testBlackReflection();
                }
                Log.d(TAG, "BlackReflection: " + (System.currentTimeMillis() - l) + "ms");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void testSystemReflection() throws Exception {
        Class<?> clazz = Class.forName("android.app.ActivityThread");
        Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
        currentActivityThread.setAccessible(true);
        Object invoke = currentActivityThread.invoke(null);
//        Log.d(TAG, "testSystemReflection: " + invoke);
    }

    private void testBlackReflection() throws Exception {
        Object o = BRActivityThread.get().currentActivityThread();
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