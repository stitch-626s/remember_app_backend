package com.remember_app.remember.exception;
import com.remember_app.remember.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常 (UserException)
     */
    @ExceptionHandler(UserException.class)
    public Result handleUserException(UserException e) {
        log.error("业务异常：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 处理运行时异常 (RuntimeException)
     * 像你之前遇到的 Integer 溢出（DataIntegrityViolationException）就属于这一类
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        log.error("运行时系统异常：", e); // 打印堆栈信息以便后台排查
        return Result.error("系统繁忙，请稍后再试或检查输入数据");
    }

    /**
     * 处理所有其他未捕获的异常 (Exception)
     * 作为最后的兜底，防止出现原始的 500 报错界面
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("未知异常：", e);
        return Result.error("服务器内部错误，请联系管理员");
    }
}