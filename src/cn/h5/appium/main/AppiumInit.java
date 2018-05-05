package cn.h5.appium.main;

import java.util.ArrayList;
import java.util.List;

import cn.h5.appium.server.Port;
import cn.h5.appium.server.Servers;
import cn.h5.appium.util.DosCmd;
import cn.h5.appium.util.XmlUtil;

public class AppiumInit {
	public static void init(){
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		DosCmd dc=new DosCmd();
		if(dc.killServer()){
			try {
				System.out.println("开始启动服务");
				servers.startServers();
				System.out.println("服务启动完毕");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				List<String> classes=new ArrayList<String>();
//				classes.add("cn.crazy.appium.testcases.ZhihuTest");
//				classes.add("cn.crazy.appium.testcases.PerInfoTest");
//				classes.add("cn.crazy.appium.testcases.RegTest1");
				classes.add("cn.h5.appium.testcases.communityActivityCase");
				XmlUtil.createTestngXml(classes);
//				XmlUtil.createTestngXml("cn.crazy.appium.testcases.PerInfoTest");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("清除appium服务失败");
		}
	}
}
