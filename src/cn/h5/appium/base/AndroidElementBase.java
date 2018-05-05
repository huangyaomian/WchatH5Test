package cn.h5.appium.base;

import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.Response;

import cn.h5.appium.util.Log;

import com.google.common.collect.ImmutableMap;

public class AndroidElementBase extends AndroidElement{
	
	private Log logger=Log.getLogger(AndroidElementBase.class);
	
	@Override
	public String getText(){
		try {
			Response response = execute(DriverCommand.GET_ELEMENT_TEXT,ImmutableMap.of("id", id));
			return (String) response.getValue();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("text is null");
			logger.debug("text is null");
			return null;
		}
	}
	
//    public void replaceValue(String value) {
//        CommandExecutionHelper.execute(this, replaceElementValueCommand(this, value));
//    }

}
