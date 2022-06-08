package com.ihis.bindings;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Profile {

	private int accountId;

	private String fullName;

	private String emailId;

	private long mobileNo;

	private String gender;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dob;

	private Long zzn;
}
