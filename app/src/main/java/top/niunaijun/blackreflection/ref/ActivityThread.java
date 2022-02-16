package top.niunaijun.blackreflection.ref;

import android.content.Intent;
import android.os.IBinder;

import top.niunaijun.blackreflection.annotation.BMethod;
import top.niunaijun.blackreflection.annotation.BParamClass;
import top.niunaijun.blackreflection.annotation.BStaticField;
import top.niunaijun.blackreflection.annotation.BStrClass;
import top.niunaijun.blackreflection.annotation.BStaticMethod;

/**
 * Created by Milk on 2022/2/15.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 * 此处无Bug
 */
@BStrClass("android.app.ActivityThread")
public interface ActivityThread {

    @BStaticMethod
    Object currentPackageName();

    @BStaticMethod
    Object currentActivityThread();

    @BMethod
    String getProcessName();

    @BMethod
    void sendActivityResult(@BParamClass(IBinder.class) IBinder token, String id, int requestCode, int resultCode, Intent data);

    @BStrClass("android.app.ActivityThread$H")
    interface H {
        @BStaticField
        int CREATE_SERVICE();
    }
}
