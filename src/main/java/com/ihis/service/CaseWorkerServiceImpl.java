package com.ihis.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihis.bindings.DashboardPage;
import com.ihis.bindings.LoginForm;
import com.ihis.bindings.Profile;
import com.ihis.entities.CaseWorkersAccountEntity;
import com.ihis.entities.EligibilityDetailsEntity;
import com.ihis.repo.AppPlanRepository;
import com.ihis.repo.CaseWorkersAccountRepository;
import com.ihis.repo.CitizenApplicationRepository;
import com.ihis.repo.EligibilityDetailsRepository;
import com.ihis.utils.EmailUtils;

@Service
public class CaseWorkerServiceImpl implements CaseWorkerService {

	@Autowired
	private CaseWorkersAccountRepository caseRepo;

	@Autowired
	private AppPlanRepository planRepo;

	@Autowired
	private CitizenApplicationRepository citizenRepo;

	@Autowired
	private EligibilityDetailsRepository eligRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String login(LoginForm loginForm) {

		CaseWorkersAccountEntity entity = caseRepo.findByEmailIdAndPassword(loginForm.getEmailId(),
				loginForm.getPazzwd());
		if (entity == null) {
			return "INVALID CREDENTIALS";
		} else {

			return "LOGIN SUCCESS";
		}
	}

	@Override
	public Profile getProfile(String emailId) {
		CaseWorkersAccountEntity entity = caseRepo.findByEmailId(emailId);
		Profile profile = new Profile();
		BeanUtils.copyProperties(entity, profile);

		return profile;
	}

	@Override
	public String updateProfile(Profile profile) {
		CaseWorkersAccountEntity entity = new CaseWorkersAccountEntity();
		BeanUtils.copyProperties(profile, entity);
		caseRepo.save(entity);
		return "SUCCESS";
	}

	@Override
	public String forgotPassword(String emailId) {
		CaseWorkersAccountEntity entity = caseRepo.findByEmailId(emailId);
		if (entity == null) {
			return "INVALID EMAILID";
		} else {
			String fileName = "Forgot-Password-Template.txt";
			String to = emailId;
			String subject = "Forgot Password";
			String body = readMailBody(fileName, entity);

			boolean isSet = emailUtils.sendEmail(to, subject, body);

			if (isSet == true) {
				return "Password sent to email succesfully";
			}
			return "Password reset failed";

		}
	}

	@Override
	public DashboardPage getDashboardpage() {

		DashboardPage dash = new DashboardPage();
		long planCount = planRepo.count();
		long appCount = citizenRepo.count();

		dash.setTotalPlans((int) planCount);
		dash.setTotalApplicationReceived((int) appCount);

		List<EligibilityDetailsEntity> elig = eligRepo.findAll();

		long apCount = elig.stream().filter(ed -> ed.getPlanStatus().contains("APPROVED")).count();
		long dnCount = elig.stream().filter(ed -> ed.getPlanStatus().contains("DENIED")).count();

		dash.setApprovedCitizenCount((int) apCount);
		dash.setDeniedCitizenCount((int) dnCount);

		return dash;
	}

	private String readMailBody(String fileName, CaseWorkersAccountEntity entity) {

		String mailBody = null;
		try {
			StringBuffer buffer = new StringBuffer();
			FileReader fileReader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fileReader);
			String line = br.readLine();

			while (line != null) {
				buffer.append(line);
				line = br.readLine();
			}
			mailBody = buffer.toString();

			mailBody = mailBody.replace("{FNAME}", entity.getFullName());
			mailBody = mailBody.replace("{PWD}", entity.getPassword());
			mailBody = mailBody.replace("{EMAIL}", entity.getEmailId());

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;

	}

}
