package com.wereash.reggie.dto;

import com.wereash.reggie.entity.OrderDetail;
import com.wereash.reggie.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
