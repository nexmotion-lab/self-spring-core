package com.example.selfspringcore;

import com.example.selfspringcore.annotation.CustomAnnotation.Autowired;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleDIContainer {

    private Map<Class<?>, Object> beans = new HashMap<>(); //

    public SimpleDIContainer(String basePackage) throws Exception{
        ComponentScanner scanner = new ComponentScanner();
        List<Class<?>> classes = scanner.scanPackage(basePackage); // 스캔이 된 클래스 보관

        System.out.println("1. classes = " + classes);

        // First pass: Create instances for classes with no-args constructor
        // 기본 생성자만 가진 클래스는 의존성 주입이 필요 없기에 미리 객체 생성
        for (Class<?> clazz : classes) {
            try {
                // 기본 생성자인 경우
                // class.getConstructors() := 해당 클래스의 생성자를 가져온다 -> length = 생성자 개수
                if (clazz.getConstructors().length == 1 && clazz.getConstructors()[0].getParameterCount() == 0) {
                    Object instance = clazz.newInstance(); // 인스턴스 생성해 객체 생성
                    beans.put(clazz, instance);
                    System.out.println("2. Created no-args instance of " + clazz.getName()); // Debugging line
                }
            } catch (Exception e) {
                throw new RuntimeException("Error during no-args bean instantiation for " + clazz.getName(), e);
            }
        }

        // Second pass: Create instances for classes with args constructor
        for (Class<?> clazz : classes) {
            if (!beans.containsKey(clazz)) { // 해당 빈이 없는 경우
                try {
                    for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                        if (constructor.isAnnotationPresent(Autowired.class)) { // 생성자에 Autowired가 있으면
                            constructor.setAccessible(true); // 메소드의 private 제어 변경 허용
                            Class<?>[] paramTypes = constructor.getParameterTypes(); // 파라미터 클래스를 가져온다.
                            Object[] params = new Object[paramTypes.length];
                            for (int i = 0; i < paramTypes.length; i++) {
                                params[i] = beans.get(paramTypes[i]); // 파라미터의 객체를 가져온다
                                if (params[i] == null) {
                                    throw new RuntimeException("No qualifying bean of type '" + paramTypes[i].getName() + "' available for constructor of " + clazz.getName());
                                }
                            }
                            Object instance = constructor.newInstance(params); // 파라미터 객체를 기반으로 인스턴스 생성
                            beans.put(clazz, instance);
                            System.out.println("3. Created args-based instance of " + clazz.getName()); // Debugging line
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error during args-based bean instantiation for " + clazz.getName(), e);
                }
            }
        }

    }

    public <T> T getBean(Class<T> clazz) {
        System.out.println("4. beans = " + beans);
        return clazz.cast(beans.get(clazz));
    }


}
