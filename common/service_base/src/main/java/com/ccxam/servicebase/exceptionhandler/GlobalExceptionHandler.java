package com.ccxam.servicebase.exceptionhandler;
import com.ccxam.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R erro (Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }
    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R erroByZero (Exception e){
        e.printStackTrace();
        return R.error().message("执行了特定处理");
    }

    //特定异常
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R erro (MyException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
