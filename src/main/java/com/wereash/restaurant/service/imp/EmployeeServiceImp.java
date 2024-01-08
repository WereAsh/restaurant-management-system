package com.wereash.restaurant.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wereash.restaurant.entity.Employee;
import com.wereash.restaurant.mapper.EmployeeMapper;
import com.wereash.restaurant.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImp extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
