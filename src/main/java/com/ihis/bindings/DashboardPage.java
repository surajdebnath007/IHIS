package com.ihis.bindings;

import lombok.Data;

@Data
public class DashboardPage {

	private int totalPlans;
	private int totalApplicationReceived;
	private int approvedCitizenCount;
	private int deniedCitizenCount;
}
