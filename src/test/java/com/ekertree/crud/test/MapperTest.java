package com.ekertree.crud.test;

import java.util.Iterator;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ekertree.crud.bean.Department;
import com.ekertree.crud.bean.Employee;
import com.ekertree.crud.dao.DepartmentMapper;
import com.ekertree.crud.dao.EmployeeMapper;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
	
	@Autowired
	DepartmentMapper departmentmapper;
	
	@Autowired
	EmployeeMapper employeemapper;
	
	@Autowired
	SqlSession sqlSession;
	
	//测试DepartmentMapper
//	@Test
//	public void test1() {
//		departmentmapper.insertSelective(new Department(null,"开发部"));
//		departmentmapper.insertSelective(new Department(null,"测试部"));
//	}
	
	@Test
	public void test2() {
		employeemapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@qq.com", 1));
	}
	

	@Test
	public void test3() {
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i = 0;i < 1000;i++) {
			String uid = UUID.randomUUID().toString().substring(0,5)+i;
			mapper.insertSelective(new Employee(null,uid,"M",uid+"@qq.com",1));
		}
		System.out.println("finish...");
	}

}
