# Black反射库 · BlackReflection

![](https://img.shields.io/badge/language-java-brightgreen.svg)

在日常开发中，会经常使用反射调用隐藏api或者其他方法，此时需要一个非常方便就能指定位置的反射库，此反射库采用接口式的写法，可快速的复制源码上的方法，打上注解，反射代码则会自动生成，不用额外的编写反射代码。

## 使用方式

### 准备

#### Step 1. 根目录Gradle文件加入
```gradle
allprojects {
    repositories {
        ...
        // 加入仓库
        maven { url 'https://jitpack.io' }
    }
}
```
#### Step 2. 需要使用的模块内引入
```gradle
implementation 'com.github.CodingGay.BlackReflection:core:1.0.5'
annotationProcessor 'com.github.CodingGay.BlackReflection:compiler:1.0.5'
```

### Demo
#### 1. 如果你需要反射 top.niunaijun.app.bean.TestReflection 中的各种方法，参考：[MainActivity.java](https://github.com/CodingGay/BlackReflection/blob/main/app/src/main/java/top/niunaijun/app/MainActivity.java)
```java
public class TestReflection {
    public static final String TAG = "TestConstructor";

    public String mContextValue = "context value";
    public static String sStaticValue = "static value";

    public TestReflection(String a) {
        Log.d(TAG, "Constructor called :" + a);
    }

    public TestReflection(String a, String b) {
        Log.d(TAG, "Constructor called : a = " + a + ", b = " + b);
    }

    public String testContextInvoke(String a, int b) {
        Log.d(TAG, "Context invoke: a = " + a + ", b = " + b);
        return a + b;
    }

    public static String testStaticInvoke(String a, int b) {
        Log.d(TAG, "Static invoke: a = " + a + ", b = " + b);
        return a + b;
    }

    public static String testParamClassName(String a, int b) {
        Log.d(TAG, "testParamClassName: a = " + a + ", b = " + b);
        return a + b;
    }
}

```
可以写成如下接口
```java
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
}

```
#### 2. build一次，让我生成相关的代码。

#### 3. 可以尽情的反射代码
构造函数
```java
TestReflection testReflection = BRTestReflection.get()._new("a");
TestReflection testReflection = BRTestReflection.get()._new("a", "b");
```

反射方法
```java
// 静态方法
BRTestReflection.get(testReflection).testContextInvoke("context", 0);

// 上下文方法
BRTestReflection.get(testReflection).testContextInvoke("context", 0);
```

反射变量
```java
// 静态变量
String staticValue = BRTestReflection.get().sStaticValue();

// 上下文变量
String contextValue = BRTestReflection.get(testReflection).mContextValue();
```

设置变量
```java
// 静态变量
BRTestReflection.get().setsStaticValue(staticValue + " changed");

// 上下文变量
BRTestReflection.get(testReflection).setmContextValue(contextValue + " changed");
```
BRTestReflection是程序自动生成的类，生成规则是BR + ClassName
- BRTestReflection.get() 用于调用静态方法
- BRTestReflection.get(caller) 用于调用非静态方法

#### 注解说明

注解 | 注解方式 | 解释
---|---|---
@BClass | Class | 指定需要反射的类
@BClassName | Class | 指定需要反射的类
@BConstructor | Method | 注明是构造方法
@BStaticMethod | Method | 注明是静态方法
@BMethod | Method | 注明是非静态方法
@BStaticField | Method | 注明是静态变量
@BField | Method | 注明是非静态变量
@BParamClass | Parameter | 注明该参数的Class，用于反射时寻找方法
@BParamClassName | Parameter | 注明该参数的Class，用于反射时寻找方法

### 混淆配置
```
-keep class top.niunaijun.blackreflection.** {*; }
-keep @top.niunaijun.blackreflection.annotation.BClass class * {*;}
-keep @top.niunaijun.blackreflection.annotation.BClassName class * {*;}
-keep @top.niunaijun.blackreflection.annotation.BClassNameNotProcess class * {*;}
-keepclasseswithmembernames class * {
    @top.niunaijun.blackreflection.annotation.BField.* <methods>;
    @top.niunaijun.blackreflection.annotation.BFieldNotProcess.* <methods>;
    @top.niunaijun.blackreflection.annotation.BFieldSetNotProcess.* <methods>;
    @top.niunaijun.blackreflection.annotation.BMethod.* <methods>;
    @top.niunaijun.blackreflection.annotation.BStaticField.* <methods>;
    @top.niunaijun.blackreflection.annotation.BStaticMethod.* <methods>;
}
```
### License

> ```
> Copyright 2022 Milk
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
> ```
