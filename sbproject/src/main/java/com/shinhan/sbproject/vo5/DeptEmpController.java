package com.shinhan.sbproject.vo5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeptEmpController {

	@Autowired
	EmpRepository3 empRepo;
	
	@Autowired
	DeptRepository3 deptRepo;
	
	@GetMapping("/deptemp")
	public String f1(Model model) {
		model.addAttribute("dlist", deptRepo.findAll());
		model.addAttribute("elist", empRepo.findAll());
		return "dept";
	}

}
