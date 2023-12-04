package com.example.selfspringcore;

import com.example.selfspringcore.controller.ControllerB;

public class SelfspringcoreApplication {
    public static void main(String[] args) throws Exception{
        System.out.println("0. main 메소드 시작");
        SimpleDIContainer container = new SimpleDIContainer("com.example"); // 스캔 요청
        ControllerB controllerB = container.getBean(ControllerB.class);
        System.out.println("5. controllerB : " + controllerB);

        // Servlet Test
        System.out.println("\n// Servlet Test");
        Hello hello = new Hello();
        hello.setData("hello");
        String data = hello.getData();
        System.out.println("hello test : data = " + data);
    }
}
