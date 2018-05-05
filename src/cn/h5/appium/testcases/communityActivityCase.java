package cn.h5.appium.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.h5.appium.base.AndroidDriverBase;
import cn.h5.appium.page.communityActivityPage;
import cn.h5.appium.testng.Assertion;
import cn.h5.appium.util.ProUtil;

public class communityActivityCase extends CaseBaseTest {
	public AndroidDriverBase driver;
	public communityActivityPage communityActivityText;
	public Assertion as;
	public ProUtil p;
	
	@BeforeClass
	@Parameters({ "udid", "port" })
	public void beforeClass(String udid, String port) {
		try {
			System.out.println("读到的udid是："+udid+"读到的port是："+port);
			driver=driverInit(udid, port);
			driver.implicitlyWait(10);
//			driver = LoginRegisterCase.driver;
			as=new Assertion(driver);
			communityActivityText  = new communityActivityPage(driver);
			p=new ProUtil("configs/caps.properties");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("执行测试test");
	}
	
	@Test(priority=0)
	public void activityBannerCase() throws Exception{
		boolean flag = communityActivityText.activityBanner();
		as.assertEquals(flag, true, "验证社区活动banner");
	}
	

	
	
	
	
	
	@AfterMethod
	public void afterMethod(){
			System.out.println("方法执行完成");
	}
	@AfterSuite
	public void quit(){
		driver.quit();
	}


}
