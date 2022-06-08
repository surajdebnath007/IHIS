package com.ihis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ihis.bindings.DashboardPage;
import com.ihis.bindings.LoginForm;
import com.ihis.bindings.Profile;
import com.ihis.service.CaseWorkerService;

@RestController
public class CaseWorkerRestController {

	@Autowired
	private CaseWorkerService service;

	@PostMapping("/login")
	public String login(LoginForm loginForm) {
		return service.login(loginForm);
	}

	@GetMapping("/forgotpassword/{emailId}")
	public String forgotPassword(@PathVariable("emailId") String emailId) {
		return service.forgotPassword(emailId);
	}

	@GetMapping("/profile/{emailId}")
	public Profile getProfile(@PathVariable("emailId") String emailId) {
		return service.getProfile(emailId);
	}

	@PostMapping("/update")
	public String updateProfile(Profile profile) {
		return service.updateProfile(profile);
	}

	@GetMapping("/dashboard")
	public DashboardPage fetchDashboard() {
		return service.getDashboardpage();
	}
}
