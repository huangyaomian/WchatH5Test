package cn.h5.appium.main;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class ExecMain {
	
	private static final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";  
	private static String path=System.getProperty("user.dir");

	public static void main(String[] args) throws Exception {
		System.setProperty(ESCAPE_PROPERTY, "false"); 
		AppiumInit.init();
        List<String> suites = new ArrayList<String>();
        suites.add(System.getProperty("user.dir")+"/testng.xml");
        System.out.println(suites);
        TestNG testNG = new TestNG();
        testNG.setTestSuites(suites);
        testNG.run();
//		   String encoding = System.getProperty("file.encoding");
//	        System.out.println("Default System Encoding: " + encoding);
		//tt.runSuitesLocally();
	}
}
