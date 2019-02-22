package com.pkest.lib.kubernetes.model;

import io.fabric8.kubernetes.api.builder.BaseFluent;
import io.fabric8.kubernetes.api.model.Doneable;

/**
 * Created by wuzhonggui on 2018/12/18.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class DoneableFunction extends BaseFluent implements Doneable<Function> {

    private final Function item;
    private final io.fabric8.kubernetes.api.builder.Function<Function, Function> function;

    @Override
    public Function done() {
        return function.apply(item);
    }

    public DoneableFunction(Function item, io.fabric8.kubernetes.api.builder.Function<Function, Function> function) {
        this.item = item;
        this.function = function;
    }

}

