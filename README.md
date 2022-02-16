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
implementation 'com.github.CodingGay.BlackReflection:core:'
annotationProcessor 'com.github.CodingGay.BlackReflection:compiler:'
```

### Demo
#### 1. 如果你需要反射 android.app.ActivityThread 中的各种方法，参考：[ActivityThread.java](https://github.com/CodingGay/BlackReflection/blob/main/app/src/main/java/top/niunaijun/blackreflection/ref/ActivityThread.java)
```java
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
}
```
#### 2. build一次，让我生成相关的代码。

#### 3. 可以尽情的反射代码
```
    Object currentActivityThread = BRActivityThread.get().currentActivityThread();
    String processName = BRActivityThread.get(currentActivityThread).getProcessName();

    Log.d(TAG, "processName: " + processName);
```
BRActivityThread是程序自动生成的类，生成规则是BR + ClassName
- BRActivityThread.get() 用于调用静态方法
- BRActivityThread.get(caller) 用于调用非静态方法

#### 注解说明

注解 | 注解方式 | 解释
---|---|---
@BClass | Class | 指定需要反射的类
@BStrClass | Class | 指定需要反射的类
@BStaticMethod | Method | 注明是静态方法
@BMethod | Method | 注明是非静态方法
@BStaticField | Method | 注明是静态变量
@BField | Method | 注明是非静态变量
@BParamClass | Parameter | 注明该参数的Class，用于反射时寻找方法
@BStrParamClass | Parameter | 注明该参数的Class，用于反射时寻找方法

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
