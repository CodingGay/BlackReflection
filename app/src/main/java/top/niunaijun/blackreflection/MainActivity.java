package top.niunaijun.blackreflection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import top.niunaijun.blackreflection.ref.BRActivityThread;
import top.niunaijun.blackreflection.ref.BRActivityThreadH;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

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
        Log.d(TAG, "field CREATE_SERVICE: " + CREATE_SERVICE);
    }
}