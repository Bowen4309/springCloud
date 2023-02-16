package com.Bibo.system.model.service;

import com.Bibo.system.model.pojo.dto.SysDictDataListDto;
import com.Bibo.system.model.pojo.dto.SysDictDataPageReqDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.common.pojo.entity.SysDictData;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.DictListDTO;
import com.Bibo.system.model.pojo.dto.SysDictDataDto;
import com.Bibo.system.model.pojo.vo.DictListVO;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author xdh
 * @since 2021-10-19
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 分页条件查询字典列表
     * @param dictListDTO 查询条件
     * @return 结果
     */
    public IPage<DictListVO> selectDictPageList(DictListDTO dictListDTO);

    /**
     * 分页查询字典类型列表
     */
    public IPage<SysDictDataListDto> selectDictTypeList(SysDictDataPageReqDto reqDto);

    public List<SysDictData> selectDictTypeList(String type);

    /**
     * 更新数据字典到redis
     * @return
     */
    public Response renewDictDateToRedis();

    public List<SysDictDataDto>  selectTagSourceList(String dictName);

    public List<SysDictData> selectDistinctDictType(String type);

    public Boolean startOrStopSysDictData(String id);

    /**
     * 分页模糊查询天算字典类型
     */
    public Response selectTfDistTypeList(SysDictDataPageReqDto dto);

    /**
     * 添加天算字典类型
     */
    public Response addTfDistTypeList(String dety);
}
