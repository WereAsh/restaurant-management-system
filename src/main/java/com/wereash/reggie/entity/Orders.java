package com.wereash.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 订单表
 */
@Data
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //订单号
    private String number;

    //状态
    private Integer status;

    //用户
    private Long userId;

    private Long addressBookId;

    //下单时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime orderTime;

    //检出时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Integer payMethod;

    private BigDecimal amount;

    private String remark;

    //手机号
    private String phone;


    //地址
    private String address;


    private String userName;

    private String consignee;


}
