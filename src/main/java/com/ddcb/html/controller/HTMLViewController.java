package com.ddcb.html.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HTMLViewController {
	
	@RequestMapping("/")
	public String getRootHtml() {
		return "redirect:/view/webview/index.html";
	}	
}