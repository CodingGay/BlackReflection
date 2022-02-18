package top.niunaijun.app.ref;

import top.niunaijun.blackreflection.annotation.BClass;
import top.niunaijun.blackreflection.annotation.BConstructor;
import top.niunaijun.blackreflection.annotation.BField;
import top.niunaijun.blackreflection.annotation.BMethod;
import top.niunaijun.blackreflection.annotation.BParamClassName;
import top.niunaijun.blackreflection.annotation.BStaticField;
import top.niunaijun.blackreflection.annotation.BStaticMethod;

/**
 * Created by Milk on 2022/2/16.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 * 此处无Bug
 */
@BClass(top.niunaijun.app.bean.TestReflection.class)
public interface TestReflection {

    @BConstructor
    top.niunaijun.app.bean.TestReflection _new(String a, String b);

    @BConstructor
    top.niunaijun.app.bean.TestReflection _new(String a);

    @BMethod
    String testContextInvoke(String a, int b);

    @BStaticMethod
    String testStaticInvoke(String a, int b);

    @BStaticMethod
    String testParamClassName(@BParamClassName("java.lang.String") Object a, int b);

    @BField
    String mContextValue();

    @BStaticField
    String sStaticValue();

    @BStaticField
    String fakeField();
}
