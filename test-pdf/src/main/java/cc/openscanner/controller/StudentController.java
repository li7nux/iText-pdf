package cc.openscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.openscanner.bean.Student;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private Student student;

	@RequestMapping(value = "/html", produces = "application/xml")
	public String html(Model model) {
		student.setName("Corey Brown");
		model.addAttribute("stud", student);
		return "studentPage";
	}
	
	@RequestMapping(value="/beanToPdfSimple")
	public ModelAndView beanToPdfSimple() {
		ModelAndView m = new ModelAndView("simpleStudPdfView");
		student.setName("xiaotong shabi");
		m.getModelMap().addAttribute("stud", student);
		return m;
	}

	@RequestMapping(value="/beanToPdfRich")
	public ModelAndView beanToPdf() {
		ModelAndView m = new ModelAndView("richStudPdfView");
		student.setName("小童 你傻逼啊");
		m.getModelMap().addAttribute("stud", student);
		return m;
	}
}