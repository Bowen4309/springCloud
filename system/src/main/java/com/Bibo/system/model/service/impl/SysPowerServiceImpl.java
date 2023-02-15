package com.Bibo.system.model.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.constant.ApiTypeGridEnum;
import com.Bibo.common.pojo.vo.GridListTreeVO;
import com.Bibo.common.response.ApiResponse;
import com.Bibo.common.util.ApiRequestParamsUtils;
import com.Bibo.common.util.HttpRequestUtil;
import com.Bibo.common.util.RequestParamsUtil;
import com.Bibo.system.model.service.ISysPowerService;
import com.Bibo.common.constant.UserConstants;
import com.Bibo.common.pojo.entity.SysPower;
import com.Bibo.system.model.mapper.SysPowerMapper;
import com.Bibo.system.model.mapper.SysRolePowerMapper;
import com.Bibo.system.model.pojo.dto.PowerDTO;
import com.Bibo.system.model.pojo.dto.PowerListDTO;
import com.Bibo.system.model.pojo.entity.SysRolePower;
import com.Bibo.system.model.pojo.vo.MetaVO;
import com.Bibo.system.model.pojo.vo.PowerListVO;
import com.Bibo.system.model.pojo.vo.PowerTreeVO;
import com.Bibo.system.model.pojo.vo.RouterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
@Service
public class SysPowerServiceImpl extends ServiceImpl<SysPowerMapper, SysPower> implements ISysPowerService {

    @Autowired
    private SysRolePowerMapper rolePowerMapper;


    /**
     * 条件分页查询权限列表
     * @param powerListDTO 权限条件
     * @return 结果
     */
    @Override
    public IPage<PowerListVO> selectPowerPageList(PowerListDTO powerListDTO){
        Page<SysPower> page = new Page(powerListDTO.getPageNum(), powerListDTO.getPageSize());
//        QueryWrapper<SysPower> queryWrapper = new QueryWrapper<SysPower>();
//        queryWrapper.like(true, "power_name", powerListDTO.getPowerName());
//        IPage<PowerListVO> powerListVO = new Page<>();
//        BeanUtil.copyProperties(this.page(page, queryWrapper), powerListVO);
//        return powerListVO;

        return this.baseMapper.selectPowerPageList(page, powerListDTO);
    }


    /**
     * 构建前端路由所需菜单
     * @param menus 菜单列表
     * @return 结果
     */
    @Override
    public List<RouterVO> buildRouterMenus(List<PowerTreeVO> menus){
        List<RouterVO> routers = new LinkedList<>();
        for (PowerTreeVO menu : menus){
            RouterVO router = new RouterVO();
            router.setHidden("1".equals(menu.getVisible()));
            router.setPath(menu.getPath());
            router.setName(menu.getComponentName());
            router.setComponent(menu.getComponent());
            router.setMetaVO(new MetaVO(menu.getPowerName(), menu.getIcon()));
            List<PowerTreeVO> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size()>0 && UserConstants.TYPE_MENU.equals(menu.getPowerType())){
                router.setChildren(buildRouterMenus(cMenus));
            }
            else if (menu.getParentId()=="0" && UserConstants.TYPE_DIR.equals(menu.getPowerType())){
                List<RouterVO> childrenList = new ArrayList<RouterVO>();
                RouterVO children = new RouterVO();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(menu.getComponentName());
                children.setMetaVO(new MetaVO(menu.getPowerName(), menu.getIcon()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }


    /**
     * 查询系统菜单列表
     * @param power 菜单详细
     * @param userId 菜单列表
     * @return 菜单列表
     */
    @Override
    public List<SysPower> selectPowerList(PowerDTO power, String userId){
        return this.baseMapper.selectPowerList(power, userId);
    }


    /**
     * 根据角色id查询菜单树信息
     * @param roleId 角色id
     * @return 选中菜单列表
     */
    @Override
    public List<String> selectPowerListByRoleId(String roleId){
        return this.baseMapper.selectPowerListByRoleId(roleId);
    }


    /**
     * 根据用户id查询权限
     * @param userId 用户id
     * @return 权限列表
     */
    @Override
    public List<String> selectPowerPermsByUserId(String userId){
        return this.baseMapper.selectMenuPermsByUserId(userId);
    }

    /**
     * 根据用户查询菜单
     * @param userId 用户id
     * @return 菜单列表
     */
    @Override
    public List<PowerTreeVO> selectPowerTreeByUserId(String userId){
        List<SysPower> powers = this.baseMapper.selectPowerTreeByUserId(userId);
        return getChildPerms(powers, "0");
    }


    /**
     * 构建前段所需下拉树结构
     * @param powers 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<PowerTreeVO> buildPowerTreeSelect(List<SysPower> powers){
        List<PowerTreeVO> powerTree = buildPowerTree(powers);
//        return powerTree.stream().map(TreeSelect::new).collect(Collectors.toList());
        return powerTree;
    }

    /**
     * 构建前段所需的树结构
     * @param powers 菜单列表
     * @return 树结构列表
     */
    private List<PowerTreeVO> buildPowerTree(List<SysPower> powers){
        List<PowerTreeVO> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        for (SysPower power : powers){
            tempList.add(power.getPowerId());
        }
        for (Iterator<SysPower> iterator = powers.iterator();iterator.hasNext();){
            SysPower power = iterator.next();
            PowerTreeVO powerTreeVo = new PowerTreeVO();
            BeanUtil.copyProperties(power, powerTreeVo);
            //如果是顶级节点，遍历该父节点的所有子节点
            if (!tempList.contains(power.getParentId())){
                recursionFn(powers, powerTreeVo);
                returnList.add(powerTreeVo);
            }
        }
        if (returnList.isEmpty()){
            return null;
        }
        return returnList;
    }


    /**
     * 根据父节点id获取所有子节点
     * @param list 权限列表
     * @param parentId 父节点id
     * @return
     */
    public List<PowerTreeVO> getChildPerms(List<SysPower> list, String parentId){
        List<PowerTreeVO> returnList = new ArrayList<>();
        for (Iterator<SysPower> iterator = list.iterator();iterator.hasNext();){
            SysPower p = iterator.next();
            PowerTreeVO powerTreeVO = new PowerTreeVO();
            BeanUtil.copyProperties(p, powerTreeVO);
            if (p.getParentId().equals(parentId)){
                recursionFn(list, powerTreeVO);
                returnList.add(powerTreeVO);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     * @param list 权限列表
     * @param power 权限信息
     */
    private void recursionFn(List<SysPower> list, PowerTreeVO power){
        List<PowerTreeVO> childList = getChildList(list, power);
        power.setChildren(childList);
        for(PowerTreeVO pChild : childList){
            //判断是否有子节点
            if (getChildList(list, pChild).size()>0){
                recursionFn(list, pChild);
            }
        }
    }

    /**
     * 获取子节点列表
     * @param list 权限列表
     * @param power 权限信息
     * @return 子节点列表
     */
    private List<PowerTreeVO> getChildList(List<SysPower> list, PowerTreeVO power){
        List<PowerTreeVO> pList = new ArrayList<>();
        Iterator<SysPower> it = list.iterator();
        while (it.hasNext()){
            SysPower p = it.next();
            PowerTreeVO powerTreeVO = new PowerTreeVO();
            BeanUtil.copyProperties(p, powerTreeVO);
            if (powerTreeVO.getParentId().equals(power.getPowerId())){
                pList.add(powerTreeVO);
            }
        }
        return pList;
    }


    /**
     * 是否存在菜单子节点
     * @param powerId 菜单id
     * @return 结果
     */
    public boolean hashChildByMenuId(String powerId){
        int result = this.baseMapper.selectCount(new QueryWrapper<SysPower>().eq("parent_id", powerId).ne("power_type","3"));
        return result>0?true:false;
    }


    /**
     * 查询菜单是否存在角色
     * @param powerId 菜单id
     * @return 结果
     */
    public boolean checkPowerExistRole(String powerId){
        int result = rolePowerMapper.selectCount(new QueryWrapper<SysRolePower>().eq("power_id", powerId));
        return result>0?true:false;
    }


    /**
     * 校验菜单名称是否唯一
     * @param power 菜单信息
     * @return 结果  true为存在  false为不存在
     */
    public boolean checkPowerNameUnique(PowerDTO power){
        String menuId = ObjectUtil.isNull(power.getPowerId())?"0":power.getPowerId();
        int info = this.baseMapper.selectCount(new QueryWrapper<SysPower>()
                .eq("parent_id",power.getParentId())
                .eq("power_name",power.getPowerName())
                .ne("power_id",menuId));
        return info>0?true:false;
    }

    @Override
    public List<String> getGridDepartAll() {
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(RequestParamsUtil.getUrlApi(),RequestParamsUtil.getUrlToken(), ApiRequestParamsUtils.insertParamsString(ApiTypeGridEnum.GRID_LIST.getApiType(),""));
            if(apiResponse.isSuccess()){
                JSONArray jsonArray = JSONObject.parseArray(apiResponse.getData().toString());
                List<String> girdListTreeVOList = new ArrayList<String>();
                for (Object grid:jsonArray) {
                    //jsonArray.forEach(grid ->{
                    GridListTreeVO girdRangeDTO = JSONObject.toJavaObject(JSONObject.parseObject(grid.toString()), GridListTreeVO.class);
                    if(girdRangeDTO.getName().endsWith("大队")){
                        girdListTreeVOList.add(girdRangeDTO.getName());
                    }
                }
                return girdListTreeVOList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return  new ArrayList<>();
    }


}
