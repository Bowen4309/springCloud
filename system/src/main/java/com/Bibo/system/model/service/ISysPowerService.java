package com.Bibo.system.model.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.common.pojo.entity.SysPower;
import com.Bibo.system.model.pojo.dto.PowerDTO;
import com.Bibo.system.model.pojo.dto.PowerListDTO;
import com.Bibo.system.model.pojo.vo.PowerListVO;
import com.Bibo.system.model.pojo.vo.PowerTreeVO;
import com.Bibo.system.model.pojo.vo.RouterVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
public interface ISysPowerService extends IService<SysPower> {


    /**
     * 条件分页查询权限列表
     * @param powerListDTO 权限条件
     * @return 结果
     */
    public IPage<PowerListVO> selectPowerPageList(PowerListDTO powerListDTO);


    /**
     * 构建前端路由所需菜单
     * @param menus 菜单列表
     * @return 结果
     */
    public List<RouterVO> buildRouterMenus(List<PowerTreeVO> menus);

    /**
     * 查询系统菜单列表
     * @param power 菜单详细
     * @param userId 菜单列表
     * @return 菜单列表
     */
    public List<SysPower> selectPowerList(PowerDTO power, String userId);


    /**
     * 根据角色id查询菜单树信息
     * @param roleId 角色id
     * @return 选中菜单列表
     */
    public List<String> selectPowerListByRoleId(String roleId);


    /**
     * 根据用户id查询权限
     * @param userId 用户id
     * @return 权限列表
     */
    public List<String> selectPowerPermsByUserId(String userId);

    /**
     * 根据用户查询菜单
     * @param userId 用户id
     * @return 菜单列表
     */
    public List<PowerTreeVO> selectPowerTreeByUserId(String userId);

    /**
     * 构建前段所需下拉树结构
     * @param powers 菜单列表
     * @return 下拉树结构列表
     */
    public List<PowerTreeVO> buildPowerTreeSelect(List<SysPower> powers);

    /**
     * 是否存在菜单子节点
     * @param powerId 菜单id
     * @return 结果
     */
    public boolean hashChildByMenuId(String powerId);

    /**
     * 查询菜单是否存在角色
     * @param powerId 菜单id
     * @return 结果
     */
    public boolean checkPowerExistRole(String powerId);

    /**
     * 校验菜单名称是否唯一
     * @param power 菜单信息
     * @return 结果  true为存在  false为不存在
     */
    public boolean checkPowerNameUnique(PowerDTO power);

    public List<String> getGridDepartAll();
}
