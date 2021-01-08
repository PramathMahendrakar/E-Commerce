package com.pra.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pra.spring.dto.RegisterDTO;
import com.pra.spring.service.RegisterService;

@Controller
@RequestMapping("/")
public class RegisterController {

	@Autowired
	private RegisterService service;

	public RegisterController() {
		System.out.println("Created..." + this.getClass().getSimpleName());
	}

	@RequestMapping(value = "/info.do", method = RequestMethod.POST)
	public String onSave(RegisterDTO dto, Model model) {

		System.out.println("Invoked onsave " + dto);
		model.addAttribute("dto", dto);

		boolean success = service.validateSave(dto);
		System.out.println("Saved" + success);
		return "LandingPage";

	}
}
