package com.qa.reports;

import org.automationtesting.excelreport.Xl;

public class reportGenrate {
	
	public static void main(String[] args) throws Exception {
		
		//Xl.generateReport("Extend-Report.xlsx");
		Xl.generateReport(System.getProperty("user.dir")+ "\\ExcelReport", "Extend-Report"+System.currentTimeMillis()+".xlsx");
		
	}

}