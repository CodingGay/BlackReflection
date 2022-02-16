package top.niunaijun.app.ref;

import top.niunaijun.blackreflection.annotation.BClass;
import top.niunaijun.blackreflection.annotation.BField;
import top.niunaijun.blackreflection.annotation.BMethod;
import top.niunaijun.blackreflection.annotation.BParamClassName;
import top.niunaijun.blackreflection.annotation.BStaticField;

/**
 * Created by Milk on 2022/2/16.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 * 此处无Bug
 */
@BClass(top.niunaijun.app.MainActivity.class)
public interface MainActivity {
    @BStaticField
    String TAGStatic();

    @BField
    String TAGContext();

    @BMethod
    void testInvoke(@BParamClassName("java.lang.String") Object a, int b);
}
