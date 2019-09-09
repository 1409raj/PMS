package com.sampark.PMS.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.sampark.PMS.PMSConstants;
import com.sampark.PMS.dto.Employee;


public class CommonUtils {
	
	private static final Logger logger = Logger.getLogger(CommonUtils.class);

	/**
	 * Returns new date containing date value from first parameter and time value from second parameter
	 * @param date
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date getTime(Date date, Date time) throws ParseException {
		SimpleDateFormat sdfDate = new SimpleDateFormat(PMSConstants.DATE_FORMAT);
		String oldDate = sdfDate.format(date);
		SimpleDateFormat sdfTime = new SimpleDateFormat(PMSConstants.TIME_FORMAT_JAVA);
		String oldTime = sdfTime.format(time);
		String n = oldDate + " " + oldTime;
		SimpleDateFormat sdf = new SimpleDateFormat(PMSConstants.DATE_FORMAT + " " + PMSConstants.TIME_FORMAT_JAVA);
		Date newTime = sdf.parse(n);
//		logger.debug("getTime(" + date + ", " + time + ") => " + newTime);
		return newTime;
	}
	
	/**
	 * Returns new date containing date value from first parameter and time value from second parameter
	 * @param date
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date getTime(Date date, String time) throws ParseException {
		String[] array = time.split(" : ");
		int hours = Integer.parseInt(array[0]);
		int minutes = Integer.parseInt(array[1]);
		int seconds = Integer.parseInt(array[2]);
		long millis = (hours*60*60 + minutes*60 + seconds) * 1000;
		long oldTime = date.getTime();
		Date newTime = new Date(oldTime + millis);
//		logger.debug("getTime(" + date + ", " + time + ") => " + newTime);
		return newTime;
	}
	
	/**
	 * Returns new date containing date value from first parameter and time value from second parameter
	 * @param date
	 * @param time
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static Date getTimeForExclusion(Date date, String time, boolean endTime) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String[] array = time.split(" : ");
		int hours = Integer.parseInt(array[0]);
		if (hours >= 24) {
			hours = hours - 24;
			c.setTime(new Date(c.getTimeInMillis() + 24*60*60*1000));
		}
		c.set(Calendar.HOUR_OF_DAY, hours);
		c.set(Calendar.MINUTE,  endTime ? 59 : 0);
		c.set(Calendar.SECOND,  endTime ? 59 : 0);
		c.set(Calendar.MILLISECOND,  0);
		Date newTime = new Date(c.getTimeInMillis());
//		logger.debug("getTimeForExclusion(" + date + ", " + time + ") => " + newTime);
		return newTime;
	}
	
	public static String getCurrentUserName() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		logger.debug("CommonUtils.getCurrentUserName() >> " + userName);
		
		// remove domain name
		if (userName != null && userName.indexOf('\\') > 0) {
			userName = userName.substring(userName.indexOf('\\') + 1);
		}
		return userName;
	}
	
	public static String getCurrentUserRole() {
		Collection<? extends GrantedAuthority> auths = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

		if (auths != null) {
			for (GrantedAuthority auth : auths) 
			{
				System.out.println("CommonUtils.getCurrentUserRole() " + auth.getAuthority());
				return auth.getAuthority();
			}
		}
		
		return "";
	}
	
	public static boolean isCurrentUserEmployee() {
		String role = getCurrentUserRole();
		if (role.toUpperCase().startsWith("ROLE_")) {
			role = role.substring(5);
		}
		return PMSConstants.USER_EMPLOYEE.equalsIgnoreCase(role);
	}
	public static boolean isCurrentUserManager() {
		String role = getCurrentUserRole();
		if (role.toUpperCase().startsWith("ROLE_")) {
			role = role.substring(5);
		}
		return PMSConstants.USER_MANAGER.equalsIgnoreCase(role);
	}
	public static boolean isCurrentUserHOD() {
		String role = getCurrentUserRole();
		if (role.toUpperCase().startsWith("ROLE_")) {
			role = role.substring(5);
		}
		return PMSConstants.USER_HOD.equalsIgnoreCase(role);
	}
	
	public static Integer getCurrentUserId(String empCode) {
		return DbUtils.getCurrentUserId(empCode);
	}
	
	public static Integer getActiveAppraisalYearId() {
		return DbUtils.getActiveAppraisalYearId();
	}

	public static boolean isCurrentUserAdmin() {
		String role = getCurrentUserRole();
		if (role.toUpperCase().startsWith("ROLE_")) {
			role = role.substring(5);
		}
		return PMSConstants.USER_ADMIN.equalsIgnoreCase(role);
	}
	
	public static Date getCurrentTime() {
		return new Date();
	}
	
	public static Date getCurrentDateWithoutTime() {
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
		return c.getTime();
	}
	
	public static Date getEndOfCentury() {
		Calendar c = Calendar.getInstance();
		c.set(2099, 11, 31, 23, 59, 59);
		return c.getTime();
	}
	
	/**
	 * get the days before/after the given date
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getPreviousDay() {
		return new Date(getCurrentDateWithoutTime().getTime() - 24*60*60*1000);
	}

	public static Integer getEmployeeAppraisalYearDetails(Integer activeAppraisalYearId, String empCode) {
		return DbUtils.getEmployeeAppraisalYearDetails(activeAppraisalYearId,empCode);
	}

	public static String getFirstLevelManagerId(String empCode) {
		return DbUtils.getFirstLevelManagerId(empCode);
	}

	public static Employee getCurrentEmployeeData(String empCode) {
		return DbUtils.getCurrentEmployeeData(empCode);
	}

	public static String getSecondLevelManagerId(String empCode) {
		return DbUtils.getSecondLevelManagerId(empCode);
	}

	public static String getEmployeeType(String empCode) {
		return DbUtils.getEmployeeType(empCode);
	}

	
	

	
}
