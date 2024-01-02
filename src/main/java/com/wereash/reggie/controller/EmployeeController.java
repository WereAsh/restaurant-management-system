package com.wereash.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wereash.reggie.common.R;
import com.wereash.reggie.entity.Employee;
import com.wereash.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
//      1.将页面提交的password进行md5加密处理
        String password=employee.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
//       2.根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp=employeeService.getOne(queryWrapper);  //数据库中用户名是unique，所以是唯一的
//       3.如果没有查询则返回到登录失败结果
        if(emp==null){
            R.error("登陆失败，不存在此账号");
        }
//       4.密码比对，如果不一致则返回登录失败结果
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败,密码错误");
        }
//       5.查看员工状态，如果为已被禁用状态，则返回员工已禁用结果
        if(emp.getStatus()==0){
            return R.error("登录失败，员工已被禁用");
        }
//       6.登入成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
//        清理Session中保存的当前员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    @PostMapping
    public R<String>save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工：{}",employee.toString());
        /*设置初始密码123456，需要进行md5加密处理*/
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
/*        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //获取当前登录用户的id
        Long empId=(Long)request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);*/
        employeeService.save(employee);
        return R.success("新增员工成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page:{},pageSize={},name={]",page,pageSize,name);
        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        //添加过滤条件,StringUtils记得导apache的包
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);

        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /*根据id修改员工信息*/
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        Long empId=(Long) request.getSession().getAttribute("employee");

        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);

/*        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);*/
        employeeService.updateById(employee);

        return R.success("员工信息修改成功！");
    }
    /*根据id查询员工信息*/
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息。。。");
        Employee employee = employeeService.getById(id);
        if(employee!=null){
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }
}
