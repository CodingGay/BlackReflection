package top.niunaijun.blackreflection;

import javax.lang.model.element.ExecutableElement;

/**
 * Created by Milk on 2022/2/15.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 * 此处无Bug
 */
public class BlackReflectionInterfaceInfo {
    // 方法element
    private ExecutableElement executableElement;
    // 是否是字段
    private boolean isField;

    public boolean isField() {
        return isField;
    }

    public void setField(boolean field) {
        isField = field;
    }

    public ExecutableElement getExecutableElement() {
        return executableElement;
    }

    public void setExecutableElement(ExecutableElement executableElement) {
        this.executableElement = executableElement;
    }
}
