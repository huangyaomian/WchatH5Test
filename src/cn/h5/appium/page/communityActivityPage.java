package cn.h5.appium.page;

import java.io.File;
import java.io.PrintWriter;
import java.util.Set;

import cn.h5.appium.base.AndroidDriverBase;
import cn.h5.appium.util.GetByLocator;
import cn.h5.appium.util.Log;

public class communityActivityPage extends BasePage {
	private Log logger=Log.getLogger(communityActivityPage.class);
	public communityActivityPage(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
    
	
    
    
	// 验证社区活动banner
		public boolean activityBanner() throws Exception {
			Thread.sleep(10000);
			this.click(GetByLocator.getLocator("meBtn"));
			Thread.sleep(1000);
			this.click(GetByLocator.getLocator("collectionBtn"));
			Thread.sleep(1000);
			this.click(GetByLocator.getLocator("viewBtn"));
		
			Thread.sleep(5000);
			Set<String> contexts=driver.getContextHandles();
			//这里是用来打印你当前的context的，如果看不到tencent的webview，说明你的debug开的有问题
			for(String s:contexts){
				System.out.println(s);
			}
//			//切换driver到公众号的context上
			driver.context("WEBVIEW_com.tencent.mm:tools");
			Thread.sleep(7000);
			Thread.sleep(5000);
			File fp=new File("D:\\1.txt");
			String str=driver.getPageSouce();
			System.out.println(str);
			PrintWriter pfp= new PrintWriter(fp);
			pfp.print(str);
			pfp.close();
			this.click(GetByLocator.getLocator("closeBtn"));
//			//当你的公众号操作有页面切换时，很可能需要先切回原生的context再切到webview上才能继续执行
//			driver.context("NATIVE_APP");
//			driver.context("WEBVIEW_com.tencent.mm:tools");
//			driver.findElement(By.xpath("//*[@title='芦笋淮山百合炒鲜虫草']")).click();
			
			return true;
		}

}
