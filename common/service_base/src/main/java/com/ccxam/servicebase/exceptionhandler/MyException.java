package com.ccxam.servicebase.exceptionhandler;

import com.ccxam.commonutils.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException {
    private Integer code;
    private String msg;
}
