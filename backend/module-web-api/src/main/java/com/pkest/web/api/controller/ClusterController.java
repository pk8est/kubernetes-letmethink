package com.pkest.web.api.controller;

import com.pkest.common.bean.PageInfo;
import com.pkest.common.bean.ResponseBean;
import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.interfaces.Insert;
import com.pkest.common.interfaces.Save;
import com.pkest.common.interfaces.Update;
import com.pkest.lib.myibatis.CompareBuilder;
import com.pkest.repo.model.ClusterModel;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.web.api.request.ClusterBody;
import com.pkest.web.api.request.NamespaceBody;
import com.pkest.web.service.service.ClusterService;
import com.pkest.web.service.service.NamespaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiOperation(value="列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page", value="分页页码", dataType="Int", paramType = "query"),
            @ApiImplicitParam(name="size", value="分页大小", dataType="Int", paramType = "query"),
    })
    public ResponseBean<PageInfo<ClusterModel>> list(Pageable pageable) throws HYException{
        CompareBuilder builder = new CompareBuilder();
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
