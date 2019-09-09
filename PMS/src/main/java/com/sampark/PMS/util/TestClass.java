package com.sampark.PMS.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestClass {

	public static void main(String[] args) throws ParseException {
		String str_date = "11-June-07";
		DateFormat formatter;
		Date date;
		formatter = new SimpleDateFormat("dd-MMM-yy");
		date = formatter.parse(str_date);
		System.out.println("\n "+ date);
	}

}
