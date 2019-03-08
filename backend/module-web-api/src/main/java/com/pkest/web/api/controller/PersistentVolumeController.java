package com.pkest.web.api.controller;

import com.pkest.common.bean.PageInfo;
import com.pkest.common.bean.ResponseBean;
import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.interfaces.Insert;
import com.pkest.common.interfaces.Save;
import com.pkest.common.interfaces.Update;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.lib.myibatis.CompareBuilder;
import com.pkest.repo.model.PersistentVolumeModel;
import com.pkest.web.api.annotation.ApiPageable;
import com.pkest.web.api.request.PersistentVolumeBody;
import com.pkest.web.service.service.PersistentVolumeService;
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
@Api(tags="持久化存储卷接口")
@RequestMapping(value = "/api/persistentVolume", headers = "Api-Version=v1")
public class PersistentVolumeController extends BaseController<PersistentVolumeService> {

    @ResponseBody
    @GetMapping("/get/{id}")
    @ApiOperation(value="详情")
    public ResponseBean<PersistentVolumeModel> get(@PathVariable("id") long id) throws HYException {
        return ResultCode.SUCCESS.wrap(getService().getOrFail(id));
    }

    @ResponseBody
    @GetMapping("/list")
    @ApiPageable
    @ApiOperation(value="列表")
    public ResponseBean<PageInfo<PersistentVolumeModel>> list(Pageable pageable) throws HYException{
        CompareBuilder builder = new CompareBuilder();
        return ResultCode.SUCCESS.wrap(getService().GePagination(builder, pageable));
    }

    @ResponseBody
    @PostMapping("/create")
    @ApiOperation(value="创建")
    public ResponseBean<PersistentVolumeModel> create(
            @RequestBody @Validated({Insert.class, Save.class}) PersistentVolumeBody body) throws HYException, K8sDriverException {
        return ResultCode.SUCCESS.wrap(getService().create(body.toDto(PersistentVolumeModel.class), body.getYaml()));
    }

    @ResponseBody
    @PutMapping("/update/{id}")
    @ApiOperation(value="更新接口")
    public ResponseBean<PersistentVolumeModel> update(@PathVariable("id") long id,
                                     @RequestBody @Validated({Update.class, Save.class}) PersistentVolumeBody body)
            throws HYException, K8sDriverException {
        return ResultCode.SUCCESS.wrap(getService().update(id, body.toDto(PersistentVolumeModel.class), body.getYaml()));
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
