package com.pkest.web.api.controller;

import com.pkest.common.bean.ResponseBean;
import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYClientException;
import com.pkest.common.exception.HYServerException;
import com.pkest.common.exception.RecordNotFoundException;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.web.service.service.NamespaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @GetMapping("/{id}")
    @ApiOperation(value="详情")
    public ResponseBean<NamespaceModel> get(@PathVariable("id") long id) throws HYServerException, HYClientException {
        return ResultCode.SUCCESS.wrap(getService().GeFind(id).orElseThrow(new RecordNotFoundException(id)));
    }

/*
    @ResponseBody
    @GetMapping("/list")
    @ApiOperation(value="列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page", value="分页页码", dataType="Int", paramType = "query"),
            @ApiImplicitParam(name="size", value="分页大小", dataType="Int", paramType = "query"),
    })
    public ResponseBean<PageInfo<App>> list(Pageable pageable) throws HYServerException, HYClientException{
        CompareBuilder builder = new CompareBuilder();
        builder.filter("delete_status", 0);
        return ResultCode.SUCCESS.wrap(getService().pagination(builder, pageable));
    }

    @ResponseBody
    @GetMapping("/listByBusiness")
    @ApiOperation(value="列表(按业务分组)")
    public ResponseBean<Map<String, CmdbBusiness>> business() throws HYServerException, HYClientException{
        CompareBuilder builder = new CompareBuilder();
        builder.filter("delete_status", 0);
        Map<String, CmdbBusiness> business = new HashMap();
        List<App> list = getService().getRepository().findAll(builder);
        list.forEach(app -> {
            if(!business.containsKey(app.getBusinessId())){
                CmdbResponse<CmdbBusiness> response = cmdbClient.businessGet(app.getBusinessId()).execute();
                if(response.isSuccess() && response.getData() != null){
                    business.put(app.getBusinessId(), response.getData());
                }else{
                    business.put(app.getBusinessId(), new CmdbBusiness(app.getBusinessId()));
                }
            }
            business.get(app.getBusinessId()).add(app);
        });
        return ResultCode.SUCCESS.wrap(business);
    }

    @ResponseBody
    @PostMapping
    @ApiOperation(value="创建")
    @DBLogger(title = "执行创建操作")
    public ResponseBean<App> create(@RequestBody @Validated({Insert.class, Save.class}) AppBody body) throws HYServerException, HYClientException {
        return ResultCode.SUCCESS.wrap(getService().create(body.toDto(App.class)));
    }

    @ResponseBody
    @PutMapping("/{appId}")
    @Permission(value = "/update/{appId}", name = "更新")
    @ApiOperation(value="更新接口")
    @DBLogger(title = "执行更新操作")
    public ResponseBean<App> update(@PathVariable("appId") long appId, @RequestBody @Validated({Update.class, Save.class}) AppBody body)
            throws HYServerException, HYClientException {
        return ResultCode.SUCCESS.wrap(getService().update(appId, body.toDto(App.class)));
    }


    @ResponseBody
    @DeleteMapping("/{appId}")
    @ApiOperation(value="删除")
    @DBLogger(title = "执行删除操作")
    public ResponseBean delete(@PathVariable("appId") long appId) throws HYServerException, HYClientException {
        if(getService().delete(appId)){
            return ResultCode.SUCCESS.message("删除成功!");
        }else{
            return ResultCode.FAILURE.message("删除失败!");
        }
    }*/

}
