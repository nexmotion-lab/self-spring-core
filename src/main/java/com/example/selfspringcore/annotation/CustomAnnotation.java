package com.example.selfspringcore.annotation;

import jakarta.servlet.annotation.WebInitParam;

import java.lang.annotation.*;

public class CustomAnnotation {

    @Target(ElementType.TYPE) // 클래스와 인터페이스에 어노테이션을 적용할 수 있다
    @Retention(RetentionPolicy.RUNTIME) // 어노테이션을 실행 시간에 이용(리플렉션 API로 접근)
    @Component
    public @interface Controller {
    }

    @Target(ElementType.TYPE) // 클래스와 인터페이스에 어노테이션을 적용할 수 있다
    @Retention(RetentionPolicy.RUNTIME) // 어노테이션을 실행 시간에 이용(리플렉션 API로 접근)
    @Controller
    public @interface test {
    }

    @Target(ElementType.TYPE) // 클래스와 인터페이스에 어노테이션을 적용할 수 있다
    @Retention(RetentionPolicy.RUNTIME) // 어노테이션을 실행 시간에 이용(리플렉션 API로 접근)
    @test
    public @interface test2 {
    }


    @Target(ElementType.TYPE) // 클래스와 인터페이스에 어노테이션을 적용할 수 있다
    @Retention(RetentionPolicy.RUNTIME) // 어노테이션을 실행 시간에 이용(리플렉션 API로 접근)
    @Component
    public @interface Service {
    }

    @Target(ElementType.ANNOTATION_TYPE) // 클래스와 인터페이스에 어노테이션을 적용할 수 있다
    @Retention(RetentionPolicy.RUNTIME) // 어노테이션을 실행 시간에 이용(리플렉션 API로 접근)
    public @interface Component {
    }

    @Target(ElementType.CONSTRUCTOR) // 클래스와 인터페이스에 어노테이션을 적용할 수 있다
    @Retention(RetentionPolicy.RUNTIME) // 어노테이션을 실행 시간에 이용(리플렉션 API로 접근)
    public @interface Autowired {}

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface WebServlet {

        /**
         * The name of the servlet
         *
         * @return the name of the servlet
         */
        String name() default "";

        /**
         * The URL patterns of the servlet
         *
         * @return the URL patterns of the servlet
         */
        String[] value() default {};

        /**
         * The URL patterns of the servlet
         *
         * @return the URL patterns of the servlet
         */
        String[] urlPatterns() default {};

        /**
         * The load-on-startup order of the servlet
         *
         * @return the load-on-startup order of the servlet
         */
        int loadOnStartup() default -1;

        /**
         * The init parameters of the servlet
         *
         * @return the init parameters of the servlet
         */
        WebInitParam[] initParams() default {};

        /**
         * Declares whether the servlet supports asynchronous operation mode.
         *
         * @return {@code true} if the servlet supports asynchronous operation mode
         * @see jakarta.servlet.ServletRequest#startAsync
         * @see jakarta.servlet.ServletRequest#startAsync( jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse)
         */
        boolean asyncSupported() default false;

        /**
         * The small-icon of the servlet
         *
         * @return the small-icon of the servlet
         */
        String smallIcon() default "";

        /**
         * The large-icon of the servlet
         *
         * @return the large-icon of the servlet
         */
        String largeIcon() default "";

        /**
         * The description of the servlet
         *
         * @return the description of the servlet
         */
        String description() default "";

        /**
         * The display name of the servlet
         *
         * @return the display name of the servlet
         */
        String displayName() default "";

    }

}
