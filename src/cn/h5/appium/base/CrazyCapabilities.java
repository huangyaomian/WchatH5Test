package cn.h5.appium.base;


import java.io.File;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import cn.h5.appium.util.ProUtil;

public class CrazyCapabilities {
	public  DesiredCapabilities initCaps(String capsPath,String udid){
		ProUtil p=new ProUtil(capsPath);
		File apkPath=new File(p.getPro("apkpath"));
		DesiredCapabilities caps=new DesiredCapabilities();
		try {
			caps.setCapability(AndroidCapabilityType.APP, apkPath.getAbsolutePath());
			caps.setCapability(AndroidCapabilityType.DEVICE_NAME, p.getPro(AndroidCapabilityType.DEVICE_NAME));
			caps.setCapability(AndroidCapabilityType.APP_PACKAGE, p.getPro(AndroidCapabilityType.APP_PACKAGE));
			caps.setCapability(AndroidCapabilityType.APP_ACTIVITY, p.getPro(AndroidCapabilityType.APP_ACTIVITY));
			caps.setCapability(AndroidCapabilityType.NO_RESET, Boolean.getBoolean(p.getPro(AndroidCapabilityType.NO_RESET))); //涓嶉渶瑕佸啀娆″畨瑁�
			caps.setCapability(AndroidCapabilityType.NO_SIGN, Boolean.getBoolean(p.getPro(AndroidCapabilityType.NO_SIGN)));
			caps.setCapability(AndroidCapabilityType.UNICODE_KEY_BOARD, Boolean.getBoolean(p.getPro(AndroidCapabilityType.UNICODE_KEY_BOARD)));
			caps.setCapability(AndroidCapabilityType.RESET_KEY_BOARD, Boolean.getBoolean(p.getPro(AndroidCapabilityType.RESET_KEY_BOARD)));
			caps.setCapability(AndroidCapabilityType.AUTOMATION_NAME,p.getPro(AndroidCapabilityType.AUTOMATION_NAME));
			caps.setCapability(AndroidCapabilityType.UDID,p.getPro(AndroidCapabilityType.UDID));
			File chromedriver=new File(p.getPro(AndroidCapabilityType.CHROMEDRIVER_PATH));//这个地方是配置Chromedriver的位置D:/chromedriver.exe
			caps.setCapability(AndroidCapabilityType.CHROMEDRIVER_EXECUTABLE, chromedriver.getAbsolutePath());
			ChromeOptions options=new ChromeOptions();
			options.setExperimentalOption("androidProcess", "com.tencent.mm:tools");
			caps.setCapability(ChromeOptions.CAPABILITY, options);
			caps.setCapability(AndroidCapabilityType.NEW_COMMAND_TIMEOUT, p.getPro(AndroidCapabilityType.NEW_COMMAND_TIMEOUT));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return caps;
	}


	/**
	 * 初始化caps的方法，带wiatactivity
	 * @param capsPath
	 * @param udid
	 * @return
	 */
	public  DesiredCapabilities initCapsWait(String capsPath,String udid){
		ProUtil p=new ProUtil(capsPath);
		File apkPath =new File(p.getPro("apkpath"));
		DesiredCapabilities caps=new DesiredCapabilities();
		try {
			caps.setCapability(AndroidCapabilityType.APP, apkPath.getAbsolutePath());
			caps.setCapability(AndroidCapabilityType.DEVICE_NAME, p.getPro(AndroidCapabilityType.DEVICE_NAME));
			caps.setCapability(AndroidCapabilityType.APP_PACKAGE, p.getPro(AndroidCapabilityType.APP_PACKAGE));
			caps.setCapability(AndroidCapabilityType.APP_ACTIVITY, p.getPro(AndroidCapabilityType.APP_ACTIVITY));
			caps.setCapability(AndroidCapabilityType.APP_WAIT_ACTIVITY, p.getPro(AndroidCapabilityType.APP_WAIT_ACTIVITY));
			caps.setCapability(AndroidCapabilityType.NO_RESET, Boolean.getBoolean(p.getPro(AndroidCapabilityType.NO_RESET))); //涓嶉渶瑕佸啀娆″畨瑁�
			caps.setCapability(AndroidCapabilityType.NO_SIGN, Boolean.getBoolean(p.getPro(AndroidCapabilityType.NO_SIGN)));
			caps.setCapability(AndroidCapabilityType.UNICODE_KEY_BOARD, Boolean.getBoolean(p.getPro(AndroidCapabilityType.UNICODE_KEY_BOARD)));
			caps.setCapability(AndroidCapabilityType.RESET_KEY_BOARD, Boolean.getBoolean(p.getPro(AndroidCapabilityType.RESET_KEY_BOARD)));
			caps.setCapability(AndroidCapabilityType.AUTOMATION_NAME,p.getPro(AndroidCapabilityType.AUTOMATION_NAME));
			caps.setCapability(AndroidCapabilityType.UDID,p.getPro(AndroidCapabilityType.UDID));
			File chromedriver=new File(p.getPro(AndroidCapabilityType.CHROMEDRIVER_PATH));//这个地方是配置Chromedriver的位置D:/chromedriver.exe
			caps.setCapability(AndroidCapabilityType.CHROMEDRIVER_EXECUTABLE, chromedriver.getAbsolutePath());
			ChromeOptions options=new ChromeOptions();
			options.setExperimentalOption("androidProcess", "com.tencent.mm:tools");
			caps.setCapability(ChromeOptions.CAPABILITY, options);
			caps.setCapability(AndroidCapabilityType.NEW_COMMAND_TIMEOUT, p.getPro(AndroidCapabilityType.NEW_COMMAND_TIMEOUT));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return caps;
	}
}
