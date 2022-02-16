package top.niunaijun.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import top.niunaijun.app.ref.BRActivityThread;
import top.niunaijun.app.ref.BRActivityThreadH;
import top.niunaijun.app.ref.BRMainActivity;
import top.niunaijun.blackreflection.R;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    public static String TAGStatic = "tag static";
    public String TAGContext = "tag context";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });


        // call method
        Object currentActivityThread = BRActivityThread.get().currentActivityThread();
        String processName = BRActivityThread.get(currentActivityThread).getProcessName();
        Log.d(TAG, "processName: " + processName);


        // get field
        int CREATE_SERVICE = BRActivityThreadH.get().CREATE_SERVICE();
        Log.d(TAG, "get field CREATE_SERVICE: " + CREATE_SERVICE);


        // set field
        Log.d(TAG, "before set TAGStatic: " + TAGStatic);
        BRMainActivity.get().setTAGStatic(TAGStatic + " changed");
        Log.d(TAG, "after set TAGStatic: " + TAGStatic);

        Log.d(TAG, "before set TAGContext: " + TAGContext);
        BRMainActivity.get(this).setTAGContext(TAGContext + " changed");
        Log.d(TAG, "after set TAGContext: " + TAGContext);

        BRMainActivity.get(this).testInvoke("avalue", 99999);
    }

    public void testInvoke(String a, int b) {
        Log.d(TAG, "testInvoke: a = " + a + ", b = " + b);
    }
}