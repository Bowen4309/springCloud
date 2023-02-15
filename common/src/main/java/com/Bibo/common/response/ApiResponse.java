package com.Bibo.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private boolean isSuccess;

    private T data;

    private String msg;

    //数据条数
    private Long count;

    //数据总页数
    private Long totalPages;
}
