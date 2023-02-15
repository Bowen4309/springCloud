package com.Bibo.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeverPage {

    /**
     * 页码
     */
    private Long skip;

    /**
     * 每页大小
     */
    private Long limit;
}
