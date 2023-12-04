package com.example.selfspringcore;

import com.example.selfspringcore.annotation.CustomAnnotation;
import com.example.selfspringcore.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 서블릿에서 ControllerB를 사용하거나 다른 로직을 추가할 수 있습니다.
//        ControllerB controllerB = new ControllerB();
//        String result = controllerB.someMethod();

        // 응답을 작성
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h2>Hello from MyServlet</h2>");
        out.println("<p>Result from ControllerB: " + "result" + "</p>");
        out.println("</body></html>");
    }
}
