package top.niunaijun.blackreflection.proxy;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

import top.niunaijun.blackreflection.BlackReflectionInfo;
import top.niunaijun.blackreflection.utils.ClassUtils;

/**
 * Created by sunwanquan on 2020/1/8.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 * 此处无Bug
 */
public class BlackReflectionProxy {

    private static final ClassName BR = ClassName.get("top.niunaijun.blackreflection", "BlackReflection");
    private final BlackReflectionInfo mReflection;

    private final ClassName mContextInterface;
    private final ClassName mStaticInterface;
    private final String mPackageName;

    public BlackReflectionProxy(String packageName, BlackReflectionInfo reflection) {
        mReflection = reflection;
        mPackageName = packageName;
        String finalClass = mReflection.getClassName()
                .replace(packageName + ".", "")
                .replace(".", "");

        mContextInterface = ClassName.get(ClassUtils.getPackage(finalClass), ClassUtils.getName(finalClass + "Context"));
        mStaticInterface = ClassName.get(ClassUtils.getPackage(finalClass), ClassUtils.getName(finalClass + "Static"));
    }

    public JavaFile generateJavaCode() {
        MethodSpec.Builder getStaticInterface = MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(mStaticInterface);

        MethodSpec.Builder getContextInterface = MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ClassName.get("java.lang", "Object"), "caller", Modifier.FINAL)
                .returns(mContextInterface);
        generaNotCallerMethod(getStaticInterface);
        generaCallerMethod(getContextInterface, mReflection);

        String finalClass = "BR" + mReflection.getClassName()
                .replace(mPackageName + ".", "")
                .replace(".", "");

        // generaClass
        TypeSpec reflection = TypeSpec.classBuilder(finalClass)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(getStaticInterface.build())
                .addMethod(getContextInterface.build())
                .build();
        return JavaFile.builder(mPackageName, reflection).build();
    }

    private void generaNotCallerMethod(MethodSpec.Builder registerMethod) {
        String statement = "return $T.create($T.class, null)";
        registerMethod.addStatement(statement,
                BR,
                mStaticInterface
        );
    }

    private void generaCallerMethod(MethodSpec.Builder registerMethod, BlackReflectionInfo reflectionInfo) {
        String statement = "return $T.create($T.class, caller)";
        registerMethod.addStatement(statement,
                BR,
                mContextInterface
        );
    }
}
