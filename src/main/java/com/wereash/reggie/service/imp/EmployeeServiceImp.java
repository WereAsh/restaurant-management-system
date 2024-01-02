package com.wereash.reggie.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wereash.reggie.entity.Employee;
import com.wereash.reggie.mapper.EmployeeMapper;
import com.wereash.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImp extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
