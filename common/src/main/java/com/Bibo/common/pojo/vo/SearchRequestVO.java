package com.Bibo.common.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequestVO {

    private Object params;

    private String methods;

    private String[] timeRange;

    private Map<String,String> header;
}
