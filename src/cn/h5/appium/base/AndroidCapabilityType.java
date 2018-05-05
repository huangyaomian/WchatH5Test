package cn.h5.appium.base;


import io.appium.java_client.remote.AndroidMobileCapabilityType;
//import io.appium.java_client.remote.MobileCapabilityType;

public interface AndroidCapabilityType extends AndroidMobileCapabilityType {
	String NO_SIGN="noSign";
	String UNICODE_KEY_BOARD="unicodeKeyboard";
	String RESET_KEY_BOARD="resetKeyboard";
	String AUTO_LAUNCH="autoLaunch";
	String NEW_COMMAND_TIMEOUT = "newCommandTimeout";
	String AUTOMATION_NAME = "automationName";
	String APPIUM = "appium";
	String APP_PACKAGE = "appPackage";
	String APP_ACTIVITY = "appActivity";
	String APP_WAIT_ACTIVITY = "appWaitActivity";
	String CHROME_OPTIONN = "chromeOptions";
	String DEVICE_NAME = "deviceName";
	String UDID = "udid";
	String NO_RESET = "noReset";
	String APP = "app";
	String CHROMEDRIVER_PATH = "chromedriverPath";
}
