package com.atguigu.springcloud.controller;

import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptService;

@RestController
public class DeptController {
	  @Autowired
	  private DeptService service;
	  
//	  @RequestMapping(value="/dept/add",method=RequestMethod.POST)
//	  public boolean add(@RequestBody Dept dept)
//	  {
//	   return service.add(dept);
//	  }
	  
	  @RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
	  @HystrixCommand(fallbackMethod = "processHystrix_Get")
	  public Dept get(@PathVariable("id") Long id)
	  {
          Dept dept = service.get(id);
          if(null == dept) {
              throw new RuntimeException("该ID:"+id+"没有对应的信息");
          }
          return dept;
      }

	  public Dept processHystrix_Get(@PathVariable("id") Long id) {
	  	Dept errObj = new Dept();
	  	errObj.setDeptno(id);
	  	errObj.setDname(id+"没有对应的信息，null--hystrix");
	  	errObj.setDb_source("no this database in MySQL");
	  	return errObj;
	  }
	  
//	  @RequestMapping(value="/dept/list",method=RequestMethod.GET)
//	  public List<Dept> list()
//	  {
//	   return service.list();
//	  }

}
