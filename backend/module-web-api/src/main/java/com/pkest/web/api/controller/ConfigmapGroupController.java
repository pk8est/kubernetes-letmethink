package com.pkest.web.api.controller;

import com.pkest.common.bean.PageInfo;
import com.pkest.common.bean.ResponseBean;
import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.interfaces.Insert;
import com.pkest.common.interfaces.Save;
import com.pkest.common.interfaces.Update;
import com.pkest.lib.myibatis.CompareBuilder;
import com.pkest.repo.model.ConfigmapGroupModel;
import com.pkest.web.api.annotation.ApiPageable;
import com.pkest.web.api.request.ConfigmapGroupBody;
import com.pkest.web.service.service.ConfigmapGroupService;
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
@Api(tags="配置分组接口")
@RequestMapping(value = "/api/configmapGroup", headers = "Api-Version=v1")
public class ConfigmapGroupController extends BaseController<ConfigmapGroupService> {

    @ResponseBody
    @GetMapping("/get/{id}")
    @ApiOperation(value="详情")
    public ResponseBean<ConfigmapGroupModel> get(@PathVariable("id") long id) throws HYException {
        return ResultCode.SUCCESS.wrap(getService().getOrFail(id));
    }

    @ResponseBody
    @GetMapping("/list")
    @ApiPageable
    @ApiOperation(value="列表")
    public ResponseBean<PageInfo<ConfigmapGroupModel>> list(Pageable pageable) throws HYException{
        CompareBuilder builder = new CompareBuilder();
        return ResultCode.SUCCESS.wrap(getService().GePagination(builder, pageable));
    }

    @ResponseBody
    @PostMapping("/create")
    @ApiOperation(value="创建")
    public ResponseBean<ConfigmapGroupModel> create(
            @RequestBody @Validated({Insert.class, Save.class}) ConfigmapGroupBody body) throws HYException {
        return ResultCode.SUCCESS.wrap(getService().create(body.toDto(ConfigmapGroupModel.class)));
    }

    @ResponseBody
    @PutMapping("/update/{id}")
    @ApiOperation(value="更新接口")
    public ResponseBean<ConfigmapGroupModel> update(@PathVariable("id") long id,
                                     @RequestBody @Validated({Update.class, Save.class}) ConfigmapGroupBody body)
            throws HYException {
        return ResultCode.SUCCESS.wrap(getService().update(id, body.toDto(ConfigmapGroupModel.class)));
    }

    @ResponseBody
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value="删除")
    public ResponseBean delete(@PathVariable("id") long id) throws HYException{
        if(getService().delete(id)){
            return ResultCode.SUCCESS.message("删除成功!");
        }else{
            return ResultCode.FAILURE.message("删除失败!");
        }
    }

}
