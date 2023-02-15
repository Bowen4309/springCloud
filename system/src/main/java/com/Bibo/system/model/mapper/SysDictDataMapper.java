package com.Bibo.system.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.Bibo.common.pojo.entity.SysDictData;
import com.Bibo.system.model.pojo.dto.DictListDTO;
import com.Bibo.system.model.pojo.dto.SysDictDataListDto;
import com.Bibo.system.model.pojo.vo.DictListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author xdh
 * @since 2021-10-19
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {


    /**
     * 分页条件查询字典列表
     * @param dictListDTO 查询条件
     * @return 结果
     */
    @Select("<script>" +
            "SELECT * FROM sys_dict_data WHERE  status='0'  " +
            "<if test=\"dict.dictLabel != null and dict.dictLabel != ''\">AND dict_label LIKE concat('%',#{dict.dictLabel},'%') </if> " +
            "</script>")
    public IPage<DictListVO> selectDictPageList(Page page, @Param("dict") DictListDTO dictListDTO);

    /**
     * 查询字典类型列表
     * @param type 字典类型
     * @return 结果
     */
    @Select("SELECT * FROM sys_dict_data WHERE dict_type=#{type}  ORDER BY dict_sort")
    public IPage<SysDictDataListDto> selectDictTypePageList(Page page, @Param("type") String type);

    /**
     * 查询没有空的数据
     * @return
     */
    @Select("select * from sys_dict_data where dict_type is not null and dict_value is not null and dict_label is not null")
    public List<SysDictData> selectNotEmptyList();


    @Select("SELECT * FROM sys_dict_data WHERE dict_type=#{type} and status ='0'  ORDER BY dict_sort")
    public List<SysDictData> selectDictTypeList(@Param("type") String type);

    /**
     * 查询数据字典二层列表
     * @param type
     * @return
     */
    @Select("select DISTINCT dict_type,dict_name from sys_dict_data  where type =#{type} and status != '2'")
    public List<SysDictData> selectDistinctDictType(String type);

    @Select("select DISTINCT dict_type from sys_dict_data  where  status = '0'")
    public List<String> selectAllDistinctDictType();
}
