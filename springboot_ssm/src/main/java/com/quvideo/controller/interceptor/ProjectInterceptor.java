package com.quvideo.controller.interceptor;


import com.quvideo.utils.HttpHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

/**
 * 拦截器的执行流程：请求 ---> preHandle ---> return true ---> Controller ---> postHandle ---> afterCompletion ------>
 * |                                                                 |
 * |                                                                 |
 * return false -------------------------------------------------------->
 */
@Component
public class ProjectInterceptor implements HandlerInterceptor {

    /**
     * 请求方法执行之前拦截
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器, 可以拿到要执行的方法对象
     * @return 返回true继续执行, 返回false将会停止后续动作
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle...");
        /**
         * 1. 获取请求参数, 比如请求方式, 请求URI, 请求头中的信息, 以及请求参数信息
         */
        System.out.println("---> 请求方式: " + request.getMethod());
        System.out.println("---> 请求URI: " + request.getRequestURI());
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("---> headerNames: " + headerNames);
        while (headerNames.hasMoreElements()) {
            String headerK = headerNames.nextElement();
            String headerV = request.getHeader(headerK);
            System.out.println("---> " + headerK + ": " + headerV);
        }
        String queryString = request.getQueryString();
        System.out.println("---> queryString: " + queryString);
        // 注意: 该方式只能获取到GET请求的请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("---> parameterMap: " + parameterMap + " size: " + parameterMap.size());
        parameterMap.forEach((k, v) -> {
            System.out.println("---> " + k + ": " + Arrays.toString(v));
        });
        // 获取POST请求的body参数, 方式一:
        String postReqBody = getPostReqBodyByReader(request);
        System.out.println("---> POST body:" + postReqBody);
        // 获取POST请求的body参数, 方式二:
//        String postReqBody = getPostReqBodyByInputStream(request);
//        System.out.println("---> POST body:" + postReqBody);

        /**
         * 2. 获取请求对应要执行的controller及方法
         */
        System.out.println(handler);
        System.out.println(handler.getClass());
        HandlerMethod hm = (HandlerMethod) handler;
        System.out.println("---> Method.Name: " + hm.getMethod().getName());

        return true;
//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle...");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 读取POST请求的body参数, 方式一: 通过 request.getReader(); 读取
     */
    public String getPostReqBodyByReader(HttpServletRequest request) {
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST") && !requestMethod.equals("PUT")) {
            return null;
        }
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            reader = request.getReader();
            String s;
            while ((s = reader.readLine()) != null) {
                stringBuilder.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 读取POST请求的body参数, 方式二: 通过 request.getInputStream(); 读取
     * ！！！但是这里有一个问题！！！
     * 这个InputStream只能读取一次,如果在拦截器中读取了,那么Controller中通过@RequestBody注解再次读取的时候,就会遇到如下异常:
     * I/O error while reading input message
     * 解决方案: 拦截器中先读取流, 然后再将流写进去, 这样后面就可以再次读取流了
     * 方案步骤:
     *      1. 自定义一个类(ReHttpServletRequestWrapper)继承自HttpServletRequestWrapper,
     *         在构造函数中通过request.getInputStream()读取body数据并保存起来;
     *      2. 重写getInputStream方法, 返回新的inputStream对象, 并将记录的body数据设置进去;
     *      3. 重写getReader方法, 使用新的getInputStream方法new一个新的BufferedReader进行返回;
     *      4. pom文件中引入tomcat-embed-core
     *          <!-- https://mvnrepository.com/artifact/org.apache.tomcat.embed/tomcat-embed-core -->
     *          <dependency>
     *              <groupId>org.apache.tomcat.embed</groupId>
     *              <artifactId>tomcat-embed-core</artifactId>
     *              <version>10.1.7</version>
     *          </dependency>
     *      5. 自定义一个过滤器(HttpServletRequestReplacedFilter)实现Filter(jakarta.servlet.* 包里的)接口;
     *      6. 重写doFilter方法, 将所有HttpServletRequest对象类型的请求, 都通过filterChain.doFilter传递新的request对象,
     *         其他类型的请求, 传递原始request对象
     *      7. 将自定义的过滤器添加到Servlet配置类(ServletInitConfig)中重写的getServletFilters方法中Filters[]数组中
     *
     */
    public String getPostReqBodyByInputStream(HttpServletRequest request) {
        String bodyStr = null;
        try {
            bodyStr = HttpHelper.getPostOrPutReqBody(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bodyStr;
    }

}
