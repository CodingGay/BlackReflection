package top.niunaijun.app.ref;

import androidx.appcompat.app.ActionBar;

import top.niunaijun.blackreflection.annotation.BClass;
import top.niunaijun.blackreflection.annotation.BMethod;

/**
 * Created by Milk on 2022/2/15.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 * 此处无Bug
 */
@BClass(androidx.appcompat.app.AppCompatActivity.class)
public interface AppCompatActivity {

    @BMethod
    ActionBar getSupportActionBar();
}
