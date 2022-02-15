package top.niunaijun.blackreflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import top.niunaijun.blackreflection.annotation.BClass;
import top.niunaijun.blackreflection.annotation.BField;
import top.niunaijun.blackreflection.annotation.BFieldNotProcess;
import top.niunaijun.blackreflection.annotation.BParamClass;
import top.niunaijun.blackreflection.annotation.BStrClass;
import top.niunaijun.blackreflection.annotation.BStrClassNotProcess;
import top.niunaijun.blackreflection.annotation.BStrParamClass;
import top.niunaijun.blackreflection.utils.Reflector;

/**
 * Created by Milk on 2022/2/15.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 * 此处无Bug
 */
@SuppressWarnings("unchecked")
public class BlackReflection {

    public static <T> T create(Class<T> clazz) {
        return create(clazz, null);
    }

    public static <T> T create(Class<T> clazz, final Object caller) {
        try {
            final Class<?> aClass = getClassNameByBlackClass(clazz);
            Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    try {
                        String name = method.getName();

                        // fidel
                        BField bField = method.getAnnotation(BField.class);
                        BFieldNotProcess bFieldNotProcess = method.getAnnotation(BFieldNotProcess.class);
                        if (bField != null || bFieldNotProcess != null) {
                            Object call;
                            Reflector on = Reflector.on(aClass).field(name);
                            if (caller == null) {
                                call = on.get(args);
                            } else {
                                call = on.get(caller);
                            }
                            return call;
                        }

                        // method
                        Class<?>[] paramClass = getParamClass(method);
                        Object call;
                        Reflector on = Reflector.on(aClass).method(name, paramClass);
                        if (caller == null) {
                            call = on.call(args);
                        } else {
                            call = on.callByCaller(caller, args);
                        }
                        return call;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                    return null;
                }
            });
            return (T) o;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Class<?>[] getParamClass(Method method) throws Throwable {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Class<?>[] param = new Class[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            boolean found = false;
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof BStrParamClass) {
                    found = true;
                    param[i] = Class.forName(((BStrParamClass) annotation).value());
                    break;
                } else if (annotation instanceof BParamClass) {
                    found = true;
                    param[i] = ((BParamClass) annotation).value();
                    break;
                }
            }

            if (!found) {
                param[i] = parameterTypes[i];
            }
        }
        return param;
    }

    private static Class<?> getClassNameByBlackClass(Class<?> clazz) throws ClassNotFoundException {
        BClass bClass = clazz.getAnnotation(BClass.class);
        BStrClass bStrClass = clazz.getAnnotation(BStrClass.class);
        BStrClassNotProcess bStrClassNotProcess = clazz.getAnnotation(BStrClassNotProcess.class);
        if (bClass == null && bStrClass == null && bStrClassNotProcess == null) {
            throw new RuntimeException("Not found @BlackClass or @BlackStrClass");
        }

        if (bClass != null) {
            return bClass.value();
        } else if (bStrClass != null) {
            return Class.forName(bStrClass.value());
        } else {
            return Class.forName(bStrClassNotProcess.value());
        }
    }
}
