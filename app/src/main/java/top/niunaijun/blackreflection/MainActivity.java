package top.niunaijun.blackreflection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import top.niunaijun.blackreflection.ref.BRActivityThread;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    public static String STATIC_FIELD = "static field";
    public String FIELD = "context field";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        Object currentActivityThread = BRActivityThread.get().currentActivityThread();
        String processName = BRActivityThread.get(currentActivityThread).getProcessName();
        Log.d(TAG, "processName: " + processName);
    }
}