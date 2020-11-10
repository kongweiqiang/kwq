package com.kwq.root.rpc.protocol;

import java.io.Serializable;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/11/4
 * @DESC :
 */

public class ProtocolBoby implements Serializable {

    /**
     * 接口全名称
     */
    private String interfaceName;

    /**
     * 接口中调用方法名称
     */
    private String methodName;

    /**
     * 参数类型列表
     */
    private Class<?> [] parameterTypes;

    /**
     * 参数类型列列表对应值组成数组
     */
    private Object [] parameterValus;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?> [] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?> [] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameterValus() {
        return parameterValus;
    }

    public void setParameterValus(Object[] parameterValus) {
        this.parameterValus = parameterValus;
    }

}
