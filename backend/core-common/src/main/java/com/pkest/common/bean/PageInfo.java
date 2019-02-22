package com.pkest.common.bean;

import com.github.pagehelper.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by wuzhonggui on 2019/1/21.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Data
public class PageInfo<T> implements Serializable {
    private long total;
    private int pageNum;
    private int pageSize;
    private List<T> list;

    public PageInfo(List<T> list) {
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum() == 0 ? 1 : page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
            this.list = page;
        } else if (list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.total = list.size();
            this.list = list;
        }
    }
}
