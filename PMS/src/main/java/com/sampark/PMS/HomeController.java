package com.sampark.PMS;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sampark.PMS.dto.EmployeeEmails;
import com.sampark.PMS.object.MenuItem;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;
import com.sampark.PMS.util.PasswordMail;

/**
 * Handles requests for the application home page.
 */
@Controller
@EnableScheduling
public class HomeController implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@Override
	@Scheduled(fixedDelay =120000)
	//@Scheduled(fixedDelay =6000)
	public void run() {

		 logger.info("---------Starting up---------");
		 List<EmployeeEmails> employeeEmail = DbUtils.getEmployeeEmailList();
		 for (EmployeeEmails employeeEmails : employeeEmail) {
		 if (PasswordMail.send(employeeEmails)) {
		 DbUtils.updateEmployeeEmailDetails(employeeEmails);
		 logger.info("Email Sent"+ new Date());
		 }
		 }
		 
		 
		
	}
	
	
	 
	 public static void scheduleFixedRateWithInitialDelayTask() {}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.debug("[home] Welcome home! The client locale is {}.", locale);
		Date date = CommonUtils.getCurrentTime();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		model.addAttribute("imagebasePath", PMSConstants.BASE_URL + "/images?image=logo.jpg&folder=images");
		model.addAttribute("userName", CommonUtils.getCurrentUserName());
		if (CommonUtils.isCurrentUserEmployee() || CommonUtils.isCurrentUserManager()) {
			model.addAttribute("empName", DbUtils.getEmployeeName(CommonUtils.getCurrentUserName()));
		} else {
			model.addAttribute("empName", CommonUtils.getCurrentUserName());
		}
		model.addAttribute("userRoleName", CommonUtils.getCurrentUserRole());
		// model.addAttribute("subEmployeeCount",
		// DbUtils.getCurrentUserSubEmployeeCount(CommonUtils.getCurrentUserName()));
		model.addAttribute("appVersion", DbUtils.getApplicationVersion());
		logger.debug("model attributes >> " + model);
		return "PMS/home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		/* model.addObject("imagebasePath", PMSConstants.IMAGE_BASE_PATH); */
		/*
		 * model.addObject("imagebasePath",
		 * "/PMS/images?image=logo.jpg&folder=images");
		 */
		model.addObject("imagebasePath", PMSConstants.BASE_URL + "/images?image=logo.jpg&folder=images");
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("PMS/pages/login/login");

		return model;

	}

	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		logger.debug("[Access_Denied] page shown to user > [" + CommonUtils.getCurrentUserName() + "]");
		model.addAttribute("user", CommonUtils.getCurrentUserName());
		return "PMS/accessDenied";
	}

	@RequestMapping(value = "/logoutPMS", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			logMessage("logoutPMS", "Logging out PMS user... ");
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}
		} catch (Exception e) {
			logger.error("[logoutPMS] Exception while logging out PMS user... " + e);
		}
		return "redirect:/login?logoutPMS";
	}

	@RequestMapping(value = "/get-menu-items", method = RequestMethod.GET)
	@ResponseBody
	public MenuItem[] getMenuItems(Locale locale, Model model) {
		logMessage("get-menu-items", "");
		List<MenuItem> menuItem = new ArrayList<MenuItem>();

		if (CommonUtils.isCurrentUserEmployee()) {
			menuItem.add(new MenuItem("home", "#/", "fa fa-th", "HOME"));
			menuItem.add(new MenuItem("employee-kra", "#/employee-kra", "fa fa-th", "KRA's"));
			menuItem.add(new MenuItem("behavioral_comptence", "#/behavioral_comptence", "fa fa-th",
					messages.getString("menu.behaviouralCompetence")));
			menuItem.add(new MenuItem("training_needs", "#/training_needs", "fa fa-th",
					messages.getString("menu.trainingNeeds")));
			menuItem.add(new MenuItem("career_aspiration", "#/career_aspiration", "fa fa-th",
					messages.getString("menu.careerAspirations")));
			menuItem.add(new MenuItem("extra_ordinary", "#/extra_ordinary", "fa fa-th",
					messages.getString("menu.extraOrdinary")));
			menuItem.add(new MenuItem("view-appraisal", "#/view-appraisal", "fa fa-th",
					messages.getString("menu.view.appraisal")));
			menuItem.add(new MenuItem("over_all_rating", "#/over_all_rating", "fa fa-th",
					messages.getString("menu.over.all.rating")));
			menuItem.add(new MenuItem("change_password", "#/change_password", "fa fa-th",
					messages.getString("menu.changePassword")));
		}

		else if (CommonUtils.isCurrentUserManager()) {
			menuItem.add(new MenuItem("home", "#/", "fa fa-th", "HOME"));
			menuItem.add(
					new MenuItem("dashboard", "#/dashboard", "fa fa-dashboard", messages.getString("menu.dashboard")));
			menuItem.add(new MenuItem("employee-kra", "#/employee-kra", "fa fa-th", "KRA's"));
			menuItem.add(new MenuItem("behavioral_comptence", "#/behavioral_comptence", "fa fa-th",
					messages.getString("menu.behaviouralCompetence")));
			menuItem.add(new MenuItem("training_needs", "#/training_needs", "fa fa-th",
					messages.getString("menu.trainingNeeds")));
			menuItem.add(new MenuItem("career_aspiration", "#/career_aspiration", "fa fa-th",
					messages.getString("menu.careerAspirations")));
			menuItem.add(new MenuItem("extra_ordinary", "#/extra_ordinary", "fa fa-th",
					messages.getString("menu.extraOrdinary")));
			menuItem.add(new MenuItem("view-appraisal", "#/view-appraisal", "fa fa-th",
					messages.getString("menu.view.appraisal")));
			menuItem.add(new MenuItem("over_all_rating", "#/over_all_rating", "fa fa-th",
					messages.getString("menu.over.all.rating")));
			menuItem.add(new MenuItem("employee-promotion", "#/employee-promotion", "fa fa-th",
					messages.getString("menu.employee.promotion")));
			if (DbUtils.isCurrentEmployeeHead(CommonUtils.getCurrentUserName(), PMSConstants.ROLE_HR_HEAD)) {
				menuItem.add(new MenuItem("hrEmployeePromotion", "#/hrEmployeePromotion", "fa fa-th",
						messages.getString("menu.employee.hr.promotion")));
				menuItem.add(new MenuItem("", "", "fa fa-th", "SUPER ADMIN ACCESS",
						new MenuItem[] {
						new MenuItem("employee-kra-list", "#/employee-kra-list", "fa fa-th","EMPLOYEE KRA's LIST"),
						new MenuItem("training_needs-list", "#/training_needs-list", "fa fa-th","TRAINING NEEDS LIST")
						}));
			}
			if (DbUtils.isCurrentEmployeeHead(CommonUtils.getCurrentUserName(), PMSConstants.ROLE_CEO_AND_ED)) {
				menuItem.add(new MenuItem("ceoEmployeePromotion", "#/ceoEmployeePromotion", "fa fa-th",
						messages.getString("menu.employee.ceo.promotion")));
			}
			menuItem.add(new MenuItem("", "", "fa fa-th", messages.getString("menu.team"),
					new MenuItem[] {
							new MenuItem("team-appraisal", "#/team-appraisal", "fa fa-th",
									messages.getString("menu.team.appraisal")),
							new MenuItem("team_over_all_rating", "#/team_over_all_rating", "fa fa-th",
									messages.getString("menu.team.over.all.rating")),
					new MenuItem("team_rating", "#/team_rating", "fa fa-th", "TEAM OVERALL RATING")
//					,new MenuItem("employee-kra-list", "#/employee-kra-list", "fa fa-th","EMPLOYEE KRA's LIST"),
//					new MenuItem("training_needs-list", "#/training_needs-list", "fa fa-th","TRAINING NEEDS LIST")
					}));
			menuItem.add(new MenuItem("change_password", "#/change_password", "fa fa-th",
					messages.getString("menu.changePassword")));
			
		} else if (CommonUtils.isCurrentUserAdmin()) {
			menuItem.add(new MenuItem("hrDashboard", "#/hrDashboard", "fa fa-dashboard",
					messages.getString("menu.dashboard")));
			menuItem.add(new MenuItem("appraisal-cycle", "#/appraisal-cycle", "fa fa-line-chart",
					messages.getString("menu.appraisalConfigurator")));
			// menuItem.add(new MenuItem("master-page", "#/master-page",
			// "fa fa-th", "Master"));
			menuItem.add(new MenuItem("", "", "fa fa-th", messages.getString("menu.application.master"),
					new MenuItem[] {
							new MenuItem("appraisal_years-list", "#/appraisal_years-list", "fa fa-th",
									messages.getString("menu.year")),
							new MenuItem("application_roles-list", "#/application_roles-list", "fa fa-th",
									messages.getString("menu.application.role")),
							new MenuItem("organization_roles-list", "#/organization_roles-list", "fa fa-th",
									messages.getString("menu.organization.role")),
							new MenuItem("department-list", "#/department-list", "fa fa-th",
									messages.getString("menu.department")),
							new MenuItem("designation-list", "#/designation-list", "fa fa-th",
									messages.getString("menu.designation")), }));

			menuItem.add(new MenuItem("", "", "fa fa-industry", messages.getString("menu.appraisal.master"),
					new MenuItem[] { new MenuItem("behavioral_comptence-list", "#/behavioral_comptence-list",
							"fa fa-th", messages.getString("menu.behaviouralCompetence")) }));
			menuItem.add(new MenuItem("", "", "fa fa-users", messages.getString("menu.employee.master"),
					new MenuItem[] {
							new MenuItem("employee-form-configuration", "#/employee-form-configuration", "fa fa-th",
									messages.getString("menu.form.configuration")),
							new MenuItem("employee-list", "#/employee-list", "fa fa-th",
									messages.getString("menu.employee.list")),
							new MenuItem("employee-appraisal-list", "#/employee-appraisal-list", "fa fa-th",
									messages.getString("menu.employee.appraisal.list")),
							new MenuItem("add-employee", "#/add-employee", "fa fa-th",
									messages.getString("menu.new.employee")),
							new MenuItem("employee-bulk", "#/employee-bulk", "fa fa-th",
									messages.getString("menu.employee.bulk")) }));
			menuItem.add(new MenuItem("", "", "fa fa-th", messages.getString("menu.team"),
					new MenuItem[] {new MenuItem("team_rating", "#/team_rating", "fa fa-th","TEAM OVERALL RATING")
							,new MenuItem("employee-kra-list", "#/employee-kra-list", "fa fa-th","EMPLOYEE KRA's LIST"),
							new MenuItem("training_needs-list", "#/training_needs-list", "fa fa-th","TRAINING NEEDS LIST")
							}));
			menuItem.add(new MenuItem("", "", "fa fa-th", messages.getString("menu.changePassword"),
					new MenuItem[] { new MenuItem("change_password", "#/change_password", "fa fa-th",
							messages.getString("menu.changePassword")) }));
			// menuItem.add(new MenuItem("hrEmployeePromotion",
			// "#/hrEmployeePromotion", "fa fa-th",
			// messages.getString("menu.employee.promotion")));
			menuItem.add(
					new MenuItem("change-logo", "#/change-logo", "fa fa-th", messages.getString("menu.changeLogo")));

		} else {
			// menuItem.add(
			// new MenuItem("dashboard", "#/dashboard", "fa fa-dashboard",
			// messages.getString("menu.dashboard")));
			// menuItem.add(new MenuItem("employee-kra", "#/employee-kra", "fa
			// fa-th", "My KRA"));
			// menuItem.add(new MenuItem("extra_ordinary", "#/extra_ordinary",
			// "fa fa-th",
			// messages.getString("menu.extraOrdinary")));
			// menuItem.add(new MenuItem("behavioral_comptence",
			// "#/behavioral_comptence", "fa fa-th",
			// messages.getString("menu.behaviouralCompetence")));
			// menuItem.add(new MenuItem("training_needs", "#/training_needs",
			// "fa fa-th",
			// messages.getString("menu.trainingNeeds")));
			// menuItem.add(new MenuItem("career_aspiration",
			// "#/career_aspiration", "fa fa-th",
			// messages.getString("menu.careerAspirations")));
			// menuItem.add(new MenuItem("change_password", "#/change_password",
			// "fa fa-th",
			// messages.getString("menu.changePassword")));
			// menuItem.add(new MenuItem("final_review", "#/final_review",
			// "fa fa-th",
			// messages.getString("menu.finalReview")));
			// menuItem.add(new MenuItem("exit", "logoutPMS", "glyphicon
			// glyphicon-off", messages.getString("menu.logout")));
		}
		MenuItem list[] = new MenuItem[menuItem.size()];
		list = menuItem.toArray(list);
		logMessage("get-menu-items", "output size > " + list.length);
		return list;
	}

	@RequestMapping(value = "/master-page", method = RequestMethod.GET)
	public String masterPage(Locale locale, Model model) {
		logMessage("master-page", "");
		return "PMS/pages/master-page/master-page";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		logMessage("index", "");
		if (CommonUtils.isCurrentUserAdmin()) {
			return "PMS/pages/dashboard/hrDashboard";
		} else {
			model.addAttribute("appraiserName", DbUtils.getAppraiserName(CommonUtils.getCurrentUserName()));
			return "PMS/pages/index/index";
		}

	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Locale locale, Model model) {
		logMessage("dashboard", "");
		return "PMS/pages/dashboard/dashboard";
	}

	@RequestMapping(value = "/hrDashboard", method = RequestMethod.GET)
	public String hrDashboard(Locale locale, Model model) {
		logMessage("hrDashboard", "");
		return "PMS/pages/dashboard/hrDashboard";
	}

	@RequestMapping(value = "/show-modal", method = RequestMethod.GET)
	public String showModal(Locale locale, Model model) {
		return "PMS/pages/modal/show-modal";
	}

	private void logMessage(String action, String data) {
		try {
			logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
		} catch (Exception e) {
			logger.debug("Exception while logging action [" + action + "] message [" + data);
		}
	}

	

}
