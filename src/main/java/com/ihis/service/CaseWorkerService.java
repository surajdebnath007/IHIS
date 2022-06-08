package com.ihis.service;

import com.ihis.bindings.DashboardPage;
import com.ihis.bindings.LoginForm;
import com.ihis.bindings.Profile;

public interface CaseWorkerService {

	public String login(LoginForm loginForm);

	public String forgotPassword(String emailId);

	public DashboardPage getDashboardpage();
	
	public Profile getProfile(String emailId);
	
	public String updateProfile(Profile profile);
}
