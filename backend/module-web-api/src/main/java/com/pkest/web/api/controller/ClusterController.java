package com.pkest.web.api.controller;

import com.pkest.common.bean.PageInfo;
import com.pkest.common.bean.ResponseBean;
import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.interfaces.Insert;
import com.pkest.common.interfaces.Save;
import com.pkest.common.interfaces.Update;
import com.pkest.lib.myibatis.QueryBuilder;
import com.pkest.repo.model.ClusterModel;
import com.pkest.web.api.annotation.ApiPageable;
import com.pkest.web.api.annotation.Searchable;
import com.pkest.web.api.annotation.Sortable;
import com.pkest.web.api.request.ClusterBody;
import com.pkest.web.service.service.ClusterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wuzhonggui on 2019/1/21.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */

@Slf4j
@Controller
@Api(tags="集群接口")
@RequestMapping(value = "/api/cluster", headers = "Api-Version=v1")
public class ClusterController extends BaseController<ClusterService> {

    @ResponseBody
    @GetMapping("/get/{id}")
    @ApiOperation(value="详情")
    public ResponseBean<ClusterModel> get(@PathVariable("id") long id) throws HYException {
        return ResultCode.SUCCESS.wrap(getService().getOrFail(id));
    }

    @ResponseBody
    @GetMapping("/list")
    @ApiPageable
    @ApiOperation(value="列表")
    @Sortable({"id", "createdAt"})
    @Searchable(value = {"id", "name"})
    public ResponseBean<PageInfo<ClusterModel>> list(QueryBuilder builder, Pageable pageable) throws HYException{
        return ResultCode.SUCCESS.wrap(getService().GePagination(builder, pageable));
    }



    @ResponseBody
    @PostMapping("/create")
    @ApiOperation(value="创建")
    public ResponseBean<ClusterModel> create(
            @RequestBody @Validated({Insert.class, Save.class}) ClusterBody body) throws HYException{
        return ResultCode.SUCCESS.wrap(getService().create(body.toDto(ClusterModel.class)));
    }

    @ResponseBody
    @PutMapping("/update/{id}")
    @ApiOperation(value="更新接口")
    public ResponseBean<ClusterModel> update(@PathVariable("id") long id,
                                     @RequestBody @Validated({Update.class, Save.class}) ClusterBody body)
            throws HYException{

        return ResultCode.SUCCESS.wrap(getService().update(id, body.toDto(ClusterModel.class)));
    }

    @ResponseBody
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value="删除")
    public ResponseBean delete(@PathVariable("id") long id) throws HYException {
        getService().getOrFail(id);
        if(getService().GeDelete(id) == 1){
            return ResultCode.SUCCESS.message("删除成功!");
        }else{
            return ResultCode.FAILURE.message("删除失败!");
        }
    }

}
