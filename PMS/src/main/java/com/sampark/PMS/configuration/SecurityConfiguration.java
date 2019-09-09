package com.sampark.PMS.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sampark.PMS.HomeController;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
				.usersByUsernameQuery("select username,password, enabled from users where username=?")
				.authoritiesByUsernameQuery(
						"select u.username, r.name from users as u,roles as r where u.username=? and u.role_id = r.id");
		logger.info("sfdbfnsdb");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		 web.ignoring().antMatchers("/resources/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.httpBasic().authenticationEntryPoint(new  AjaxAuthorizationPoint("/login"));
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/appraisal-cycle/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/appraisal-cycle-start/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/application_roles-list/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/behavioral_comptence-list/**").access("hasRole('ADMIN')")
		.antMatchers("/get-all-applicationRoles-list/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/save-applicationRoles/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/delete-applicationRoles/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/getALLAppraisalPendingEmployeeDetails/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/getAllDepartmentHRDashBoardDetails/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/get-all-employeeAppraisalList/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/updateEmployeeAppraisal/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/save-behavioral_comptence/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/behavioral_data_carry_forward/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/get-behavioral-competence-list/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/change-logo/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/changeLogo/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/get-all-basic-details/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/getHRDashBoardDetails/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/get-employee-dynamic-form-details/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/get_Current_employee_dynamic_form/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/department-list/**").access("hasRole('ROLE_ADMIN')")
//		.antMatchers("/get-all-departments/**").permitAll()
		.antMatchers("/save-department/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/delete-department/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/designation-list/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/get-all-designations/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/save-designation/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/delete-designation/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/employee-list/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/employee-appraisal-list/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/add-employee/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/modify-employee/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/employee-form-configuration/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/delete-employee/**").access("hasRole('ADMIN')")
		.antMatchers("/save-column/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/activate_column/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/delete-column/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/add-new-employee/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/add-employee-bulk/**").access("hasRole('ROLE_ADMIN')")
//		.antMatchers("/get-all-employeeList/**").permitAll()
		.antMatchers("/extra_ordinary-list/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/get_extra_ordinary_list/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/save_extra_ordinary/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/activate_extra_ordinary/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/delete_extra_ordinary/**").access("hasRole('ROLE_ADMIN')")	
		.antMatchers("/hrDashboard/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/organization_roles-list/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/get-all-organization-roles/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/save-organization-roles/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/delete-organization-roles/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/qualification-list/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/get-all-qualifications/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/save-qualification/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/delete-qualification/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/training_needs-list/**").access("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')")
		.antMatchers("/save_training_needs/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/activate_training_needs/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/delete_training_needs/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/get_training_needs_list/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/team-appraisal/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/firstlevel-appraisal/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/secondlevel-appraisal/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/getAppraisalPendingEmployeeDetails/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/add-manager-behavioral-comptence-details/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/add-manager-career_aspirations-details/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/getDashBoardDetails/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/get-all-team-members/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/get-manager-Details/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/get-all-subEmployeeList/**").access("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')")
		.antMatchers("/employee-promotion/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/hrEmployeePromotion/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/ceoEmployeePromotion/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/add-manager-extraOrdinary-details/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/add-manager-kra-details/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/dashboard/**").access("hasRole('ROLE_MANAGER')")
		.antMatchers("/add-manager-trainingNeeds-details/**").access("hasRole('ROLE_MANAGER')")

		.antMatchers("/add-employee-behavioral-comptence-details/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')")
		.antMatchers("/add-employee-career_aspirations-details/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')")
//		.antMatchers("/getDetails/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE','ROLE_HOD')")
		.antMatchers("/add-employee-trainingNeeds-details/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')")
		
		.antMatchers("/isUserFirstTimeLogin/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE','ROLE_HOD')")
		.antMatchers("/training_needs/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE','ROLE_HOD')")
		.antMatchers("/employee-kra/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE','ROLE_HOD')")
		.antMatchers("/add-employee-kra-details/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE','ROLE_HOD')")
		.antMatchers("/career_aspiration/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE','ROLE_HOD')")	
		.antMatchers("/add-employee-extraOrdinary-details/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE','ROLE_HOD')")
		.antMatchers("/change_password/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE','ROLE_ADMIN','ROLE_HOD')")
//		.antMatchers("/reset_password/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE','ROLE_ADMIN','ROLE_HOD')")
//		.antMatchers("/resetPassword/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE','ROLE_ADMIN','ROLE_HOD')")
		.antMatchers("/changeUserPassword/**").access("hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE','ROLE_ADMIN','ROLE_HOD')")
		.antMatchers("/home/**").permitAll() 
		.antMatchers("/index/**").permitAll()
		.antMatchers("/PMS/images/**").permitAll()
				.and().formLogin().loginPage("/login").failureUrl("/login?error").usernameParameter("username")
				.passwordParameter("password").and().logout().logoutSuccessUrl("/login?logout/")
				.and().exceptionHandling().accessDeniedPage("/accessDenied").and().anonymous().disable();
		http.csrf().disable();

	}

}
