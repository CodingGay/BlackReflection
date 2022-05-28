package top.niunaijun.app.ref;

import top.niunaijun.blackreflection.annotation.BClassName;
import top.niunaijun.blackreflection.annotation.BStaticMethod;

/**
 * Created by Milk on 2022/5/28.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 * 此处无Bug
 */
@BClassName("android.app.ActivityThread")
public interface ActivityThread {
    @BStaticMethod
    Object currentActivityThread();
}
