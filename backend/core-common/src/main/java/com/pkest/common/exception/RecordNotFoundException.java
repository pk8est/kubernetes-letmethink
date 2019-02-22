package com.pkest.common.exception;

import lombok.NoArgsConstructor;

import java.util.function.Supplier;

/**
 * Created by wuzhonggui on 2019/1/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@NoArgsConstructor
public class RecordNotFoundException implements Supplier<HYClientException>  {

    private Long id;
    private String message = "[%s] record not fount!";

    public String getFormatMessage(){
        return String.format(message, id);
    }

    public RecordNotFoundException(Long id){
        this(id, null);
    }

    public RecordNotFoundException(String message){
        this(null, message);
    }

    public RecordNotFoundException(Long id, String message){
        if(id != null) this.id = id;
        if(message != null) this.message = message;
    }

    @Override
    public HYClientException get() {
        return new HYClientException(getFormatMessage());
    }
}
