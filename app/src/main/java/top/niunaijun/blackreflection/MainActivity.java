package top.niunaijun.blackreflection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    public static String STATIC_FIELD = "static field";
    public String FIELD = "context field";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Object activityThread = BRActivityThread.get().currentActivityThread();
        String processName = BRActivityThread.get(activityThread).getProcessName();
        Log.d(TAG, "processName: " + processName);
    }
}