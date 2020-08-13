package com.kwq.root.shardingsphere.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/8/13
 * @DESC :
 */
@ApiModel
@Data
public class User {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("员工编号")
    private String staffNo;

    @ApiModelProperty("员工姓名")
    private String staffName;

    @ApiModelProperty("部门")
    private String department;

    @ApiModelProperty("创建时间")
    private LocalDateTime ctime;

    @ApiModelProperty("更新时间")
    private LocalDateTime mtime;

}
