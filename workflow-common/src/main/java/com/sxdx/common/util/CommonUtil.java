package com.sxdx.common.util;

import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: workflow-activiti
 * @description:
 * @author: garnett
 * @create: 2020-07-26 10:21
 **/

public class CommonUtil {
    /**
     * 把指定的复杂对象属性，按照指定的内容，封装到新的map中
     * @param source 目标对象
     * @param ps     需要封装到map中的属性
     * @return
     */
    public static Map<String, Object> obj2map(Object source, String[] ps) {
        Map<String, Object> map = new HashMap<>();
        if (source == null)
            return null;
        if (ps == null || ps.length < 1) {
            return null;
        }
        for (String p : ps) {
            PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(
                    source.getClass(), p);
            if (sourcePd != null && sourcePd.getReadMethod() != null) {
                try {
                    Method readMethod = sourcePd.getReadMethod();
                    if (!Modifier.isPublic(readMethod.getDeclaringClass()
                            .getModifiers())) {
                        readMethod.setAccessible(true);
                    }
                    Object value = readMethod.invoke(source, new Object[0]);
                    map.put(p, value);
                } catch (Exception ex) {
                    throw new RuntimeException(
                            "Could not copy properties from source to target",
                            ex);
                }
            }
        }
        return map;
    }

}
