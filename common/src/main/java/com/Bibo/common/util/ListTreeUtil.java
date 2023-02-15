package com.Bibo.common.util;


import cn.hutool.core.util.ObjectUtil;
import com.Bibo.common.pojo.vo.GridListTreeVO;

import java.util.ArrayList;
import java.util.List;

public class ListTreeUtil {


    public static List<GridListTreeVO> treeList (List<GridListTreeVO> dataList, String parentId){
        List<GridListTreeVO> treeGridList = new ArrayList<GridListTreeVO>();
        dataList.forEach(childGrid ->{
            if(childGrid.getParentId().equals(parentId)){
                List<GridListTreeVO> childList =  treeList(dataList,childGrid.getId());
                if(ObjectUtil.isNotEmpty(childList)){
                    childGrid.setChildList(childList);
                }
                treeGridList.add(childGrid);
            }
        });
        return treeGridList;
    }
}
