package com.gcwl.bkserver.config.exception;

import com.gcwl.bkserver.util.Result;
import com.gcwl.bkserver.util.ResultEnum;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Object defaultErrorHandler(HttpServletRequest req, Exception e) {
        String errorPosition = "";
        //如果错误堆栈信息存在
        if (e.getStackTrace().length > 0) {
            StackTraceElement element = e.getStackTrace()[0];
            String fileName = element.getFileName() == null ? "未找到错误文件" : element.getFileName();
            int lineNumber = element.getLineNumber();
            errorPosition = fileName + ":" + lineNumber;
        }
        return Result.error("错误信息：" + e.toString() + " \n错误位置:" + errorPosition);
    }

    /**
     * GET/POST请求方法错误的拦截器
     * 因为开发时可能比较常见,而且发生在进入controller之前,上面的拦截器拦截不到这个错误
     * 所以定义了这个拦截器
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object httpRequestMethodHandler() {
        return Result.error("oh，资源未找到，请登录后尝试");
    }

//    /**
//     * 本系统自定义错误的拦截器
//     * 拦截到此错误之后,就返回这个类里面的json给前端
//     * 常见使用场景是参数校验失败,抛出此错,返回错误信息给前端
//     */
//    @ExceptionHandler(CommonJsonException.class)
//    public Object commonJsonExceptionHandler(CommonJsonException commonJsonException) {
//        return commonJsonException.getResultJson();
//    }

    /**
     * 权限不足报错拦截
     */
    @ExceptionHandler({ AuthorizationException.class})
    public Object unauthorizedExceptionHandler() {
        return Result.error("权限不足");
    }

    /**
     * 未登录报错拦截
     * 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
     */
    @ExceptionHandler({UnauthenticatedException.class})
    public Object unauthenticatedException() {
        return Result.error("未登录，请先登录");
    }

}
