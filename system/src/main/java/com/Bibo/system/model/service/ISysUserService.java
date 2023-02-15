package com.Bibo.system.model.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.pojo.entity.SysUser;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.UserDTO;
import com.Bibo.system.model.pojo.dto.UserListDTO;
import com.Bibo.system.model.pojo.vo.UserListVO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
public interface
ISysUserService extends IService<SysUser> {

    /**
     * 根据条件分页查询用户列表
     * @param user 查询条件
     * @return 用户列表
     */
    public IPage<UserListVO> selectUserPageList(UserListDTO user);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 结果
     */
    public LoginUser selectUserByUserName(String username);

    /**
     * 校验部门是否存在用户
     * @param deptId 部门id
     * @return 结果
     */
    public Boolean checkDeptExistUser(String deptId);

    /**
     * 校验用户名称是否唯一
     * @param userName 警员姓名
     * @return 结果
     */
    public Boolean checkUserNameUnique(String userName);

    /**
     * 校验是否警号唯一
     * @param policeCode
     * @return
     */
    public Boolean checkPoliceCode(String policeCode);

    /**
     * 用户授权角色
     * @param userId
     * @param roleIds
     */
    public void insertUserAuth(String userId, List<String> roleIds);

    public void insertUserAuth(List<String> userId, List<String> roleIds);


    /**
     * 获取用户的菜单路由
     * @param userId 用户ID
     */
    Map<String,Object> selectRouterByUserId(String userId);

    /**
     * 修改用户信息
     * @param userDTO 用户信息
     * @return 结果
     */
    public int updateUser(UserDTO userDTO);

    /**
     * 添加用户信息
     * @param userDTO 用户信息
     * @return 结果
     */
    public boolean saveUser(UserDTO userDTO) throws InvalidKeySpecException, NoSuchAlgorithmException;

    /**
     * 获取用户的信息
     */
    LoginUser selectLoginUserCode(String policeCode);

    Response getAllGridList(String name);

    List<SysUser> getAllByDeptId(String deptId);

    List<Map<String,Object>> orcelPolice();

    Response synchronousPolice(List<Map<String,Object>> polices);
;
}
