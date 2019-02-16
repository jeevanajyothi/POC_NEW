package com.aa.connectme.util;

import org.testng.annotations.DataProvider;

public class TestData {
	
	@DataProvider(name = "ConnectMeTowerAppLoginStations")
	public static Object[][] dataProviderMessage() {
		return new Object[][] { { "Chrome", "Stage", "DFW" }, { "Chrome", "Stage", "CLT" } };
	}
	
	
	/*@DataProvider(name = "ConnectMeTowerAppLoginStations")
	public static Object[][] dataProviderMessage() {
		return new Object[][] { { "Chrome", "NextGen", "DFW" }, { "Chrome", "NextGen", "CLT" } };
	}*/
}
