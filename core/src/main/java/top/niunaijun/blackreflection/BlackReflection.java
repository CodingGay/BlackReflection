package top.niunaijun.blackreflection;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import top.niunaijun.blackreflection.annotation.BClass;
import top.niunaijun.blackreflection.annotation.BField;
import top.niunaijun.blackreflection.annotation.BFieldNotProcess;
import top.niunaijun.blackreflection.annotation.BFieldSetNotProcess;
import top.niunaijun.blackreflection.annotation.BParamClass;
import top.niunaijun.blackreflection.annotation.BClassName;
import top.niunaijun.blackreflection.annotation.BClassNameNotProcess;
import top.niunaijun.blackreflection.annotation.BParamClassName;
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
    private static final Map<Class<?>, Object> sProxyCache = new HashMap<>();

    // key caller
    private static final WeakHashMap<Object, Map<Class<?>, Object>> sCallerProxyCache = new WeakHashMap<>();

    public static <T> T create(Class<T> clazz, final Object caller) {
        return create(clazz, caller, false);
    }

    public static <T> T create(Class<T> clazz, final Object caller, boolean withException) {
        try {
            if (!withException) {
                if (caller == null) {
                    Object o = sProxyCache.get(clazz);
                    if (o != null) {
                        return (T) o;
                    }
                } else {
                    Map<Class<?>, Object> callerClassMap = sCallerProxyCache.get(caller);
                    if (callerClassMap != null) {
                        Object o = callerClassMap.get(clazz);
                        if (o != null) {
                            return (T) o;
                        }
                    }
                }
            }

            final WeakReference<Object> weakCaller = caller == null ? null : new WeakReference<>(caller);

            final Class<?> aClass = getClassNameByBlackClass(clazz);
            Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    try {
                        boolean isStatic = weakCaller == null;

                        Object callerByWeak = isStatic ? null : weakCaller.get();

                        String name = method.getName();
                        // fidel
                        BField bField = method.getAnnotation(BField.class);
                        BFieldNotProcess bFieldNotProcess = method.getAnnotation(BFieldNotProcess.class);
                        if (bField != null || bFieldNotProcess != null) {
                            Object call;
                            Reflector on = Reflector.on(aClass).field(name);
                            if (isStatic) {
                                call = on.get(args);
                            } else {
                                if (callerByWeak == null) {
                                    return null;
                                }
                                call = on.get(callerByWeak);
                            }
                            return call;
                        }
                        BFieldSetNotProcess bFieldSetNotProcess = method.getAnnotation(BFieldSetNotProcess.class);
                        if (bFieldSetNotProcess != null) {
                            // startsWith "set"
                            name = name.substring(3);
                            Reflector on = Reflector.on(aClass).field(name);
                            if (isStatic) {
                                on.set(args[0]);
                            } else {
                                if (callerByWeak == null) {
                                    return 0;
                                }
                                on.set(callerByWeak, args[0]);
                            }
                            return 0;
                        }

                        // method
                        Class<?>[] paramClass = getParamClass(method);
                        Object call;
                        Reflector on = Reflector.on(aClass).method(name, paramClass);
                        if (isStatic) {
                            call = on.call(args);
                        } else {
                            if (callerByWeak == null) {
                                return null;
                            }
                            call = on.callByCaller(callerByWeak, args);
                        }
                        return call;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                        if (withException) {
                            throw throwable;
                        }
                    }
                    return null;
                }
            });

            if (caller == null) {
                sProxyCache.put(clazz, o);
            } else {
                Map<Class<?>, Object> callerClassMap = sCallerProxyCache.get(caller);
                if (callerClassMap == null) {
                    callerClassMap = new HashMap<>();
                    sCallerProxyCache.put(caller, callerClassMap);
                }
                callerClassMap.put(clazz, o);
            }
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
                if (annotation instanceof BParamClassName) {
                    found = true;
                    param[i] = Class.forName(((BParamClassName) annotation).value());
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
        BClassName bClassName = clazz.getAnnotation(BClassName.class);
        BClassNameNotProcess bClassNameNotProcess = clazz.getAnnotation(BClassNameNotProcess.class);
        if (bClass == null && bClassName == null && bClassNameNotProcess == null) {
            throw new RuntimeException("Not found @BlackClass or @BlackStrClass");
        }

        if (bClass != null) {
            return bClass.value();
        } else if (bClassName != null) {
            return Class.forName(bClassName.value());
        } else {
            return Class.forName(bClassNameNotProcess.value());
        }
    }
}
