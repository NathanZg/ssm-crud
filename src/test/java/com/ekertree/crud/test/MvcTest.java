package com.ekertree.crud.test;

import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ekertree.crud.bean.Employee;
import com.github.pagehelper.PageInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MvcTest {
	@Autowired
	WebApplicationContext context;
	MockMvc mockmvc;
	
	@org.junit.Before
	public void initMockMvc() {
		mockmvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testPage() {
		MvcResult result = null;
		try {
			result = mockmvc.perform(MockMvcRequestBuilders.get("/emptest").param("pageNum", "5")).andReturn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MockHttpServletRequest request = result.getRequest();
		PageInfo<Employee> page = (PageInfo<Employee>) request.getAttribute("pageInfo");
		System.out.println("当前页码："+page.getPageNum());
		System.out.println("总记录数："+page.getTotal());
		System.out.println("总页码："+page.getNavigatePages());
		System.out.println("连续显示的页数：");
		int[] navigatepageNums = page.getNavigatepageNums();
		for (int i : navigatepageNums) {
			System.out.print("page:"+i+" ");
		}
		System.out.println();
		List<Employee> list = page.getList();
		for (Employee employee : list) {
			System.out.println("ID："+employee.getEmpId()+"姓名："+employee.getEmpName());
		}
	}
}
