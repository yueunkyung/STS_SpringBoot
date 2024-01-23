package com.shinhan.sbproject.vo5;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sbproject.webboard.PageVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/rest/emp")
public class DeptEmpControllerRest {

	@Autowired
	EmpRepository3 empRepo;
	
	@Autowired
	DeptRepository3 deptRepo;
	
	@GetMapping("/list")
	public List<EmpVO3> emplist() {
		return (List<EmpVO3>) empRepo.findAll();
	}
	
	@GetMapping("/detail/{empno}")
	public EmpVO3 empDetail(@PathVariable("empno") Integer empno) {
		return empRepo.findById(empno).orElse(null);
	}
	
	@PostMapping("/insert")
	public int empInsert(@RequestBody EmpVO3 emp) {
		EmpVO3 insertEmp = empRepo.save(emp);
		return insertEmp == null ? -1:0;
	}
	
	@PutMapping("/update")
	public int empUpdate(@RequestBody EmpVO3 emp) {
		EmpVO3 updateEmp = empRepo.save(emp);
		return updateEmp == null ? -1:0;
	}
	
	@DeleteMapping("/delete/{empno}")
	public String empDelete(@PathVariable("empno") Integer empno) {
		empRepo.deleteById(empno);
		return "delete complete";
	}
	
	
	

}
