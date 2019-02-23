package com.pkest.web.api.controller;

import com.pkest.common.bean.PageInfo;
import com.pkest.common.bean.ResponseBean;
import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYClientException;
import com.pkest.common.exception.HYException;
import com.pkest.common.exception.HYServerException;
import com.pkest.common.exception.RecordNotFoundException;
import com.pkest.common.interfaces.Insert;
import com.pkest.common.interfaces.Save;
import com.pkest.common.interfaces.Update;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.lib.myibatis.CompareBuilder;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.web.api.request.NamespaceBody;
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
@Api(tags="名命空间接口")
@RequestMapping(value = "/api/namespace", headers = "Api-Version=v1")
public class NamespaceController extends BaseController<NamespaceService> {

    @ResponseBody
    @GetMapping("/get/{id}")
    @ApiOperation(value="详情")
    public ResponseBean<NamespaceModel> get(@PathVariable("id") long id) throws HYException {
        return ResultCode.SUCCESS.wrap(getService().getOrFail(id));
    }

    @ResponseBody
    @GetMapping("/list")
    @ApiOperation(value="列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page", value="分页页码", dataType="Int", paramType = "query"),
            @ApiImplicitParam(name="size", value="分页大小", dataType="Int", paramType = "query"),
    })
    public ResponseBean<PageInfo<NamespaceModel>> list(Pageable pageable) throws HYException{
        CompareBuilder builder = new CompareBuilder();
        return ResultCode.SUCCESS.wrap(getService().GePagination(builder, pageable));
    }

    @ResponseBody
    @PostMapping("/create")
    @ApiOperation(value="创建")
    public ResponseBean<NamespaceModel> create(
            @RequestBody @Validated({Insert.class, Save.class}) NamespaceBody body) throws HYException, K8sDriverException {
        return ResultCode.SUCCESS.wrap(getService().create(body.toDto(NamespaceModel.class), body.getWarp()));
    }

    @ResponseBody
    @PutMapping("/update/{id}")
    @ApiOperation(value="更新接口")
    public ResponseBean<NamespaceModel> update(@PathVariable("id") long id,
                                     @RequestBody @Validated({Update.class, Save.class}) NamespaceBody body)
            throws HYException, K8sDriverException {
        return ResultCode.SUCCESS.wrap(getService().update(id, body.toDto(NamespaceModel.class), body.getWarp()));
    }

    @ResponseBody
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value="删除")
    public ResponseBean delete(@PathVariable("id") long id) throws HYException, K8sDriverException{
        if(getService().delete(id)){
            return ResultCode.SUCCESS.message("删除成功!");
        }else{
            return ResultCode.FAILURE.message("删除失败!");
        }
    }

}
