package top.niunaijun.blackreflection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import top.niunaijun.blackreflection.ref.BRActivityThread;
import top.niunaijun.blackreflection.ref.BRActivityThreadH;
import top.niunaijun.blackreflection.ref.BRMainActivity;


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
    }

    public void test() {
        throw new RuntimeException("test RuntimeException");
    }
}