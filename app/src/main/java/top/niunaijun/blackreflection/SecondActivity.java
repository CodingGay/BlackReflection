package top.niunaijun.blackreflection;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;

import top.niunaijun.blackreflection.ref.BRAppCompatActivity;

/**
 * Created by Milk on 2022/2/15.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 * 此处无Bug
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SecondActivity activity = SecondActivity.this;
        ActionBar supportActionBar = BRAppCompatActivity.get(activity).getSupportActionBar();
        try {
            Method getSupportActionBar = AppCompatActivity.class.getDeclaredMethod("getSupportActionBar");
            getSupportActionBar.setAccessible(true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
