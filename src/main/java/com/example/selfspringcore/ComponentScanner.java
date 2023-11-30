package com.example.selfspringcore;

import com.example.selfspringcore.annotation.CustomAnnotation.Component;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;

public class ComponentScanner {



    public List<Class<?>> scanPackage(String basePackage) throws Exception{

        // 현재 스레드를 기준으로 ClassLoader 반환
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 프로젝트 리소스를 기준으로 검색하기 위해 basePackage 경로에서 .를 /로 변환 ex)com.example -> com/example
        String path = basePackage.replace('.', '/');
        // classLoader의 getResources 메소드는 검색하는 리소스 명으로 된 모든 리소스를 찾고, 리소스 명은 리소스를 식별하는
        // '/'로 구분 된 경로들로 열겨형 데이터인 Enumeration으로 리턴
        // file:/Users/minwoo/IdeaProjects/.../org/example
        Enumeration<URL> resources = classLoader.getResources(path);

        List<File> dirs = new ArrayList<>();
        // Enumeration은 열거형 데이터로 cursor의 위치를 통해 객체를 반환
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            // dirs에 /users ... /com/example까지의 경로를 보관
            dirs.add(new File(resource.getFile())); // resource.getFile() := /Users/minwoo/IdeaProjects/.../org/example
        }

        ArrayList<Class<?>> classes = new ArrayList<>(); // 탐색할 클래스를 저장
        for (File directory : dirs) { // directory := /Users/minwoo/.../org/example
            classes.addAll(findClasses(directory, basePackage)); // findClasses 호출 후 탐색 클래스 저장
        }

        return classes;
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        // 파일이 존재하는지 확인하고, 존재하지 않으면 빈 리스트를 반환
        if (!directory.exists()) {
            return classes;
        }
        // 디렉토리에 있는 모든 파일들을 배열로 가져온다
        File[] files = directory.listFiles();
        // 디렉토리에 있는 각 파일에 대해 반복합니다.
        for (File file : files) { // file := 디렉토리 내부의 모든 파일
            // 파일이 디렉토리라면, 그것은 하위 패키지로 가정
            if (file.isDirectory()) {
                // 디렉토리(하위 패키지) 이름에 점이 포함되어 있지 않은지 확인, 디렉토리 이름엔 점이 있을 수 없다
                assert !file.getName().contains(".");
                // 재귀적으로 서브디렉토리에서 클래스를 찾으며, 패키지 이름에 서브디렉토리 이름을 추가
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                // org.example.ComponenetScanner <- 과 같이 파싱
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                // 방금 구성한 클래스 이름에 대한 클래스 객체를 로드합니다.
                Class<?> cls = Class.forName(className); // cls := class org.example.Main
                if (!cls.isAnnotation() && isComponent(cls)) { // 컴포넌트 어노테이션이 내부에 있는지 확인
                    classes.add(cls);
                }
            }
        }
        return classes;
    }

    private static boolean isComponent(Class<?> cls) {
        Set<Class<?>> visited = new HashSet<>();
        return isComponentRecursive(cls, visited); // 재귀이므로 스택 오버 플로우 방지
    }

    private static boolean isComponentRecursive(Class<?> cls, Set<Class<?>> visited) {
        // Direct check for @Component
        if (cls.isAnnotationPresent(Component.class)) {
            return true;
        }

        // Iterate over all annotations
        for (Annotation annotation : cls.getAnnotations()) {
            Class<?> annotationType = annotation.annotationType();
            // 이미 처리한 어노테이션은 스킵
            if (!visited.add(annotationType)) {
                continue;
            }

            // Check if the annotation is @Component or has @Component in its meta-annotations
            if (annotationType.isAnnotationPresent(Component.class) ||
                    isComponentRecursive(annotationType, visited)) {
                return true;
            }
        }

        return false;
    }
}
