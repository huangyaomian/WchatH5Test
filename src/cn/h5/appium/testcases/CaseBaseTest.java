package cn.h5.appium.testcases;


import cn.h5.appium.base.AndroidDriverBase;
import cn.h5.appium.base.AndroidSpecific;
import cn.h5.appium.base.CrazyPath;
import cn.h5.appium.util.ProUtil;

public class CaseBaseTest {
	
	public static AndroidDriverBase driver;

	public AndroidDriverBase driverInit(String udid, String port)
			throws Exception {
		ProUtil p = new ProUtil(CrazyPath.globalPath);
		String server=p.getPro("server");
		String capsPath=CrazyPath.capsPath;
		System.out.println(capsPath);
		//获取原本输入法，方便后续设置回去
		String input=AndroidSpecific.getDefaultInput(udid);
		System.out.println("连接"+udid+"端口"+port);
		System.out.println("开始创建server连接");
		driver = new AndroidDriverBase(server, port, capsPath, udid, input);
		return driver;
	}
}
