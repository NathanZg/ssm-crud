package com.ekertree.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ekertree.crud.bean.Employee;
import com.ekertree.crud.bean.Msg;
import com.ekertree.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	
	  @RequestMapping("/emptest")
	  public String getEmps(@RequestParam(value ="pageNum",defaultValue = "1")Integer pageNum,Model model) { 
		  //引入分页插件
		  PageHelper.startPage(pageNum, 5);
		  List<Employee> emps =
		  employeeService.getAll(); 
		  PageInfo<Employee> pageInfo = new PageInfo<Employee>(emps,5);
		  model.addAttribute("pageInfo", pageInfo);
		  return "list"; 
	  }
	 

	@ResponseBody
	@RequestMapping("/emps")
	public Msg getEmpsWithJson(@RequestParam(value ="pageNum",defaultValue = "1")Integer pageNum) {
		PageHelper.startPage(pageNum, 5);
		List<Employee> emps =employeeService.getAll(); 
		PageInfo<Employee> pageInfo = new PageInfo<Employee>(emps,5);
		return Msg.success().add("pageInfo", pageInfo);
	}
	
	@ResponseBody
	@RequestMapping(value = "/emp",method = RequestMethod.POST)
	public Msg saveEmp(@Valid Employee employee,BindingResult result) {
		if(result.hasErrors()) {
			HashMap<String,Object> map = new HashMap<String, Object>();
			List<FieldError> fieldErrors = result.getFieldErrors();
			for(FieldError error : fieldErrors) {
				map.put(error.getField(), error.getDefaultMessage());
			}
			return Msg.failure().add("errorFields", map);
		}else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}
	
	@ResponseBody
	@RequestMapping("/checkUser")
	public Msg checkUser(@RequestParam("empName")String empName) {
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regx)) {
			return Msg.failure().add("va_msg", "用户名得是2-5位中文或者6-16位英文和数字的组合！");
		}
		boolean flag = employeeService.checkUser(empName);
		if(flag) {
			return Msg.success();
		}else {
			return Msg.failure().add("va_msg", "用户名不可用！");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
	public Msg getEmp(@PathVariable("id") Integer id) {
		Employee  employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	@ResponseBody
	@RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
	public Msg saveEmp(Employee employee) {
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	@ResponseBody
	@RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("ids") String ids) {
		if(ids.contains("-")) {
			String[] string_ids = ids.split("-");
			List<Integer> del_ids = new ArrayList<Integer>();
			for (String id : string_ids) {
				del_ids.add(Integer.parseInt(id));
			}
			employeeService.deleteBatch(del_ids);
		}else {
			employeeService.deleteEmpById(Integer.parseInt(ids));
		}
		return Msg.success();
	}
}
