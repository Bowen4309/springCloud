package com.Bibo.system.model.controller;

import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.TagDTO;
import com.Bibo.system.model.pojo.dto.TagTypeDTO;
import com.Bibo.system.model.service.ISysTagService;
import com.Bibo.system.model.service.ISysTagTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "标签管理")
@RestController
@RequestMapping(value = "/tag")
public class SysTagManageController {

    @Autowired
    private ISysTagTypeService tagTypeService;

    @Autowired
    private ISysTagService tagService;

    @SysLog(title="查询标签分类树状数据", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "查询标签分类树状数据",notes = "查询标签分类树状数据")
    @GetMapping("/tagTypeListTree")
    public Response tagTypeListTree(){
        return tagTypeService.getTagTypeListTree();
    }

    @SysLog(title="新增标签分类", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "新增标签分类",notes = "新增标签分类")
    @PostMapping("/saveTagType")
    public Response saveTagType(@RequestBody  TagTypeDTO tagTypeDTO){
        tagTypeService.saveTagType(tagTypeDTO);
        return Response.success();
    }

    @SysLog(title="删除标签分类", operatorType= OperatorTypeEnum.DELETE)
    @ApiOperation(value = "删除标签分类",notes = "删除标签分类")
    @GetMapping("/deleteTagType")
    public Response deleteTagType(String tagTypeId){
        return tagTypeService.deleteTagType(tagTypeId);
    }


    @SysLog(title="修改标签分类", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "修改标签分类",notes = "修改标签分类")
    @PostMapping("/updateTagType")
    public Response updateTagType(@RequestBody TagTypeDTO tagTypeDTO){
        tagTypeService.updateTagType(tagTypeDTO);
        return Response.success();
    }

    @SysLog(title="新增标签", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "新增标签",notes = "新增标签")
    @PostMapping("/saveTag")
    public Response saveTag(@RequestBody TagDTO tagDTO){
        tagService.saveTag(tagDTO);
        return Response.success();
    }

    @SysLog(title="删除标签", operatorType= OperatorTypeEnum.DELETE)
    @ApiOperation(value = "删除标签",notes = "删除标签")
    @GetMapping("/deleteTag")
    public Response deleteTag(String id){
        return tagService.deleteTag(id);
    }


    @SysLog(title="修改标签", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "修改标签",notes = "修改标签")
    @PostMapping("/updateTag")
    public Response updateTag(@RequestBody TagDTO tagDTO){
        tagService.updateTag(tagDTO);
        return Response.success();
    }

    @SysLog(title="根据分类查询标签", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据分类查询标签",notes = "根据分类查询标签")
    @GetMapping("/tagListByType")
    public Response tagListByType(String tagTypeId,String status){
        return tagService.getTagListByTypeId(tagTypeId,status);
    }

    @SysLog(title="标签启用停用", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "标签启用停用",notes = "标签启用停用")
    @GetMapping("/updateStatus")
    @ApiImplicitParams({ @ApiImplicitParam(value = "ids(id;隔开)", name = "ids",required = true),
            @ApiImplicitParam(value = "状态", name = "status",required = true)})
    public Response updateStatus(String ids,String status){
        return tagService.updateTagSatus(ids,status);
    }
}
