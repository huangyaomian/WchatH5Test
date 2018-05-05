package cn.h5.appium.base;

import static io.appium.java_client.android.AndroidKeyCode.BACKSPACE;
import static io.appium.java_client.android.AndroidKeyCode.KEYCODE_MOVE_END;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cn.h5.appium.util.DosCmd;
import cn.h5.appium.util.Log;
/**
 * 封装自己的driver类，包含重写及自主封装的方法
 * @author 
 * @param <V>
 *
 */
/**
 * 封装自己的driver类，包含重写及自主封装的方法
 * @author 
 * @param <V>
 *
 */
@SuppressWarnings("unchecked")
public class AndroidDriverBase extends AndroidDriver<AndroidElement> {
	
	private Log logger=Log.getLogger(AndroidDriverBase.class);
	public  String input;
	public  String udid;
	/**
	 * 构造方法
	 * @param remoteAddress
	 * @param desiredCapabilities
	 * @throws Exception 
	 */
	public AndroidDriverBase(String server,String port,String capsPath,String udid,String input) throws Exception{
		super(new URL(server+":"+port+"/wd/hub"),new CrazyCapabilities().initCaps(capsPath, udid));
		this.input=input;
		this.udid=udid;
	}
	
	/**
	 * 构造方法，这个driver中是有APP_WAIT_ACTIVITY
	 * @param server
	 * @param port
	 * @param capsPath
	 * @param udid
	 * @param input
	 * @param waitFlag
	 * @throws Exception
	 */
	public AndroidDriverBase(String server,String port,String capsPath,String udid,String input,int waitFlag) throws Exception{
		super(new URL(server+":"+port+"/wd/hub"),new CrazyCapabilities().initCapsWait(capsPath, udid));
		this.input=input;
		this.udid=udid;
	}
	

	/**
	 * 获取app应用占用的屏幕宽度
	 * @return 返回宽度
	 */
	public int appScreenWidth(){
		int width=super.manage().window().getSize().getWidth();
		System.out.println("获取app应用占用的屏幕宽度为->"+width);
		return width;
	}
	
	/**
	 * 获取app应用占用的屏幕高度
	 * @return 返回高度
	 */
	public int appScreenHeight(){
		int height=super.manage().window().getSize().getHeight();
		System.out.println("获取app应用占用的屏幕宽度为->"+height);
		return height;
	}
	
	/**
	 * 判断元素是否存在
	 * @param by By.id("xxxx") By.xpath("xxx")  NoSuchWebElementException
	 * @return 存在则返回true，不存在则返回false
	 */
	public boolean isElementExist(By by){
		try {
			return super.findElement(by).isDisplayed();
//			return true;
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 判断元素是否存在
	 * @param AndroidElement el
	 * @return 存在则返回true，不存在则返回false
	 */
	public boolean isElementExist(AndroidElement el){
		try {
			return el.isDisplayed();
//			return true;
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			return false;
		}
	}

	
	/**
	 * 在指定超时时间内元素是否存在，如存在则立即返回结果，如不存在则在超时后返回结果
	 * @param by 定位对象
	 * @param timeout 超时时间
	 * @return 指定时间内任意时间该元素出现则停止等待返回true，指定时间内没出现则返回false
	 */
	public boolean isElementExist(By by,int timeout){
		try {
			new WebDriverWait(this,timeout).until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			logger.debug("element not found");
			return false;
		}
	}
	
	/**
	 * 再3秒时间内，看toast是否存在   需要appium版本1.6以上，而且要用uiautomator2模式
	 * @param string
	 * @return 指定时间内任意时间该元素出现则停止等待返回true，指定时间内没出现则返回false
	 */
	public boolean isToast(String toast){
		WebDriverWait wait = null;
		try {
			try {
				 wait = new WebDriverWait(this,3);
			} catch (Exception e) {
				System.out.println("wait出错了");
				return false;
			}
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text,'"+ toast + "')]")));
			System.out.println("找到了toast");
			return true;
		} catch (Exception e) {
			System.out.println("找不到toast");
			return false;
		}
	}
	
//	try {
//	    b = batteryLevel.wait(Until.textEquals(newL + " %"), 10000);
//	} catch (StaleObjectException e) {
//	    UiObject2 okCancelDialog = device.findObject(By.textContains("Connect charger"));
//	    if(okCancelDialog != null){
//	        UiObject2 okButton = device.findObject(By.clazz(Button.class.getName()).text("OK"));
//	        okButton.click();
//	        device.waitForWindowUpdate("",10000);
//	        b = batteryLevel.wait(Until.textEquals(newL + " %"), 10000);
//	    }
//	}
	
	/**
	 * 
	 * @param string
	 * @return 返回toast文本
	 */
	public String getToastText(String toastText){
		try {
			String toasttextString = this.findElement(By.xpath(".//*[contains(@text,'"+ toastText + "')]")).getText();
			return toasttextString;
		} catch (Exception e) {
			System.out.println("获取不到Toast文本");
			return null;
		}
	}
	/**
	 * 
	 * @param string
	 * @return 返回文本
	 */
	public String getMyText(By by){
		try {
			return this.findElement(by).getText();
		} catch (Exception e) {
			System.out.println("获取不到文本");
			return null;
		}
	}
	
	/**
	 * 
	 * @param string
	 * @return 返回文本
	 */
	public String getMyText(AndroidElement el){
		try {
			return el.getText();
		} catch (Exception e) {
			System.out.println("获取不到文本");
			return null;
		}
	}
	/**
	 * 查找元素存在则返回该元素对象，不存在则返回null
	 * @param by
	 * @return
	 */
	@Override
	public AndroidElement findElement(By by){
		try {
			AndroidElement element=(AndroidElement) super.findElement(by);
			return element;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("element is null");
			logger.debug("element is null");
			return null;
		}
	}
	
	public AndroidElementBase findElementMy(By by){
		try {
			AndroidElementBase element=(AndroidElementBase) super.findElement(by);
			return element;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("element is null");
			logger.debug("element is null");
			return null;
		}
	}
	
//	 @Override 
	 public List<AndroidElement> findElements(By by) {
	    	try {
	    		 return super.findElements(by);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("element is null");
				logger.debug("element is null");
				return null;
			}
	    }
	 
//	 public List<AndroidElementBase> findElementsMy(By by) {
//	    	try {
//	    		 return super.findElements(by);
//			} catch (Exception e) {
//				// TODO: handle exception
//				System.out.println("element is null");
//				logger.debug("element is null");
//				return null;
//			}
//	    }
	 
	
	/**
	 * 在指定超时时间内查找元素是否存在，如存在则立即返回AndroidElement对象，如不存在则在超时后返回null
	 * @param by
	 * @param timeout 单位是秒
	 * @return
	 */
	public AndroidElement findElement(final By by,int timeout){
		try {
			AndroidElement element=new WebDriverWait(this, timeout).until(new ExpectedCondition<AndroidElement>() {
				@Override
				public AndroidElement apply(WebDriver driver) {
					// TODO Auto-generated method stub
					return (AndroidElement) driver.findElement(by);
				}
			});
			return element;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}
	
	
//	@Override
//	public List findElements(By by){
//		List<AndroidElement> elementList=super.findElements(by);
//		return elementList;
//	}
	
	
	/**
	 * 滑动屏幕方法，通过参数实现各方向滑动
	 * @param direction 方向参数，值为"up"、"down"、"right"、"left"
	 * @param duration 滑动时间，单位毫秒
	 */
	public void swipe(String direction,int duration){
		try {
			SwipeScreen swipeSreen=new SwipeScreen(this);
			swipeSreen.swipe(direction, duration);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("滑动出错，错误如下："+e);
		}
	}
	
	/**
	 * 滑动屏幕方法，通过参数实现各方向滑动，可定义滑动次数
	 * @param direction 方向参数，值为"up"、"down"、"right"、"left"
	 * @param duration 滑动时间，单位毫秒
	 * @param number 滑动次数   3就滑动3次
	 */
	public void swipe(String direction , int duration , int number){
		try {
			SwipeScreen swipeSreen=new SwipeScreen(this);
			for (int i = 0; i < number; i++) {
				swipeSreen.swipe(direction, duration);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
	/**
	 * 在元素上滑动
	 * @param element 元素对象
	 * @param derction 方向参数，值为"up"、"down"、"right"、"left"
	 * @param duration 毫秒
	 */
	public void swipeOnElement(AndroidElement element,String derction,int duration){
	String derc=derction.toLowerCase();
	//获取控件起始坐标
	int xAxisStartPoint = element.getLocation().getX();
	int yAxisStartPoint = element.getLocation().getY();
	System.out.println("起始坐标为"+xAxisStartPoint);
	System.out.println("起始坐标为"+yAxisStartPoint);
	// 获取控件尺寸
	int xWidth = element.getSize().getWidth();
	System.out.println("宽度为"+xWidth);
	int yHeight = element.getSize().getHeight();
	System.out.println("高度为"+yHeight);
		switch (derc) {
		case "up":
			this.swipe(xAxisStartPoint + xWidth/2 , yAxisStartPoint + yHeight*9/10 , xAxisStartPoint + xWidth/2 , yAxisStartPoint + yHeight/10 , duration); 
			break;
		case "down":
			this.swipe(xAxisStartPoint + xWidth/2 , yAxisStartPoint + yHeight/10 , xAxisStartPoint + xWidth/2 , yAxisStartPoint + yHeight*9/10 , duration); 
			break;
		case "left":
			this.swipe(xAxisStartPoint + xWidth*9/10 , yAxisStartPoint + yHeight/2 , xAxisStartPoint + xWidth/10 , yAxisStartPoint + yHeight/2 , duration); 
			break;
		case "right":
			this.swipe(xAxisStartPoint + xWidth/10 , yAxisStartPoint + yHeight/2 , xAxisStartPoint + xWidth*9/10 , yAxisStartPoint + yHeight/2 , duration); 
			break;
		default:
			System.out.println("方向参数错误");
			break;
		}
	}
	
	public void swipeOnElement(By by,String derction,int duration){
		AndroidElement	element=this.findElement(by);
		this.swipeOnElement(element, derction, duration);
	}
	
	/**
	 * 向某方向滑动直到某元素出现
	 * @param by 查找对象
	 * @param direction 方向
	 * @param duration 每次滑动时间，单位毫秒
	 * @param findCount 查找次数
	 */
	public boolean swipeUntilElementAppear(By by,String direction,int duration,int findCount){
//		this.implicitlyWait(3);
		boolean flag=false;
		while(!flag&&findCount>0){
			try {
				super.findElement(by);
				flag=true;
			} catch (Exception e) {
				// TODO: handle exception
				this.swipe(direction,duration);
				findCount--;
			}
		}
		return flag;
	}
	
	public boolean swipeUntilElementAppear(AndroidElement element,String direction,int duration,int findCount){
//		this.implicitlyWait(3);
		boolean flag=false;
		while(!flag&&findCount>0){
			flag=this.isElementExist(element);
			if(!flag){
				this.swipe(direction,duration);
				findCount--;
			}
		}
		return flag;
	}
	
	public AndroidElement swipeUntilElement(By by,String direction,int duration,int findCount){
		//this.implicitlyWait(3);
		AndroidElement element=null;
		boolean flag=false;
		while(!flag&&findCount>0){
			try {
				element=(AndroidElement) super.findElement(by);
				flag=true;
			} catch (Exception e) {
				// TODO: handle exception
				this.swipe(direction,duration);
				findCount--;
			}
		}
		return element;
	}
	
	
	
	
	/**
	 * 与服务端断开连接,断开前重置输入法
	 */
	public void quit(){
		this.resetInput();
		super.quit();
	}
	
	
	
	
	/**
	 * 单击某元素，使用tap方式
	 * @param element
	 * @param duration
	 */
//	public void tapSingle(AndroidElement element){
//		super.tap(1, element,100);
//	}
//	
	
	
	
	/**
	 * touchaction的方式点击元素
	 * @param element
	 */
	public void tap(AndroidElement element){
		try {
			TouchAction ta=new TouchAction(this);
			ta.tap(element).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 点击某个坐标点
	 * @param x
	 * @param y
	 */
	public void clickByCoordinate(int x,int y){
		try {
			TouchAction ta=new TouchAction(this);
			ta.tap(x,y).release().perform();
			System.out.println("点击坐标"+x+"  "+y+"成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("点击坐标"+x+"  "+y+"报错");
		}
	}
	
	/**
	 * 元素长按
	 * @param by
	 */
	public void longPress(By by){
		try {
			AndroidElement element=(AndroidElement) super.findElement(by);
			TouchAction ta=new TouchAction(this);
			ta.longPress(element).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 元素长按
	 * @param AndroidElement
	 */
	public void longPress(AndroidElement element){
		try {
			TouchAction ta=new TouchAction(this);
			ta.longPress(element).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 元素1移动到元素2
	 * @param AndroidElement
	 */
	public void moveElementTo(AndroidElement element1 , AndroidElement element2 ){
		try {
			TouchAction ta=new TouchAction(this);
			ta.longPress(element1).moveTo(element2).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 元素1移动某坐标
	 * @param AndroidElement
	 */
	public void moveElementTo(AndroidElement element1 , int x , int y ){
		try {
			TouchAction ta=new TouchAction(this);
			ta.longPress(element1).moveTo(x,y).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 在某一个坐标点上长按
	 * @param x
	 * @param y
	 */
	public void longPress(int x,int y){
		try {
			TouchAction ta=new TouchAction(this);
			ta.longPress(x,y).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前页面源码
	 */
	public String getPageSouce(){
		return super.getPageSource();
	}
	
	/**
	 * 隐式等待
	 * @param timeout
	 */
	public void implicitlyWait(int timeout){
		super.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}
	/**
	 * 获取手机分辨率
	 * @return
	 */
	public int[] getMobileSize(){
		//adb shell wm size
		DosCmd dos=new DosCmd();
		int[] sizeInt=new int[2];
		try {
			List<String> resList=dos.execCmdConsole("adb -s "+udid+" shell wm size");
			String[] size=new String[2];
			if(resList.size()>0){
					size=resList.get(0).split(": ")[1].split("x");
			}
			sizeInt[0]=Integer.valueOf(size[0]);
			sizeInt[1]=Integer.valueOf(size[1]);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sizeInt;
	}
	
	/**
	 * 重置输入法
	 * @param input
	 */
	public void resetInput(){
		DosCmd dc=new DosCmd();
		try {
//			dc.execCmdConsole("adb shell \"ime set "+input+"\"");
			dc.execCmdConsole("adb -s "+ udid +" shell settings put secure default_input_method "+ input);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 切换输入法
	 * @param input
	 */
	public void resetInput(String input){
		DosCmd dc=new DosCmd();
		try {
			dc.execCmdConsole("adb shell \"ime set "+input+"\"");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断两个字符串集合list是否元素对应相等
	 * @param strSrc
	 * @param strDes
	 * @return
	 */
	public Boolean listStrEquals(List<String> strSrc,List<String> strDes){
		Boolean flag=false;
		if((!strSrc.isEmpty()&&strSrc!=null)&&(!strDes.isEmpty()&&strDes!=null)){
			if(strSrc.size()==strDes.size()){
				for(int i=0;i<strDes.size();i++){
					if(strSrc.get(i).equals(strDes.get(i))){
						flag=true;
						continue;
					}else{
						flag=false;
						//System.out.println(strSrc.get(i)+" "+strDes.get(i));
						break;
					}
				}
			}else{
				System.out.println("两个list大小不相等");
			}
		}else{
			System.out.println("list为空或者为null");
		}
		return flag;
	}
	
	/**
	 * 从指定androidelement集合中根据索引选取某一个
	 * @param srcList
	 * @param index
	 * @return
	 */
	public AndroidElement selectElementFromList(List<AndroidElement> srcList,int index){
		AndroidElement element=null;
		if(!srcList.isEmpty()&&srcList!=null){
			if(index>=0&&index<srcList.size()){
				element=srcList.get(index);
			}else{
				System.out.println("集合大小为"+srcList.size()+"但是索引参数传值为"+index);
			}
		}else{
			System.out.println("集合为空或者为null");
		}
		return element;
	}
	
	/**
	 * 从指定androidelement集合中根据索引选取某一个
	 * @param srcList
	 * @param index
	 * @return
	 */
	public AndroidElement selectElementFromList(By by,int index){
		List<AndroidElement> srcList=this.findElements(by);
		AndroidElement element=null;
		if(!srcList.isEmpty()&&srcList!=null){
			if(index>=0&&index<srcList.size()){
				element=srcList.get(index);
			}else{
				System.out.println("集合大小为："+srcList.size()+"但是索引参数传值为："+index);
			}
		}else{
			System.out.println("集合为空或者为null");
		}
		return element;
	}
	
	/**
	 * 从AndroidElement集合中选出text值与预期相符的唯一元素
	 * @param srcList
	 * @param strFind
	 * @return
	 */
	public AndroidElement selectElementFromList(List<AndroidElement> srcList,String strFind){
		AndroidElement element=null;
		if(!srcList.isEmpty()&&srcList!=null){
			for(AndroidElement ae:srcList){
				if(ae.getAttribute("text").equals(strFind)){
					element=ae;
					break;
				}
			}
		}else{
			System.out.println("集合为空或者为null");
		}
		return element;
	}
	
	/**
	 * 实现滑动前后的字符串集合list的对比           某方向滑动直到边界，如底部，顶部(在没有边界标识的时候使用)
	 * @param direction
	 * @param strSrc
	 * @param strDes
	 * @return
	 */
	public void swipeUntilBoundary(String direction,By by){
		boolean flag=false;
		List<String> strSrc=new ArrayList<String>();
		List<String> strDes=new ArrayList<String>();
		while(!flag){
			//滑动前定位元素并将元素的text添加到集合strSrc里
			List<AndroidElement> elementOld = super.findElements(by);
			for(AndroidElement ae:elementOld){
				strSrc.add(ae.getText());
			}
			this.swipe(direction, 500);
			this.wait(1000);
			//滑动后定位元素并将元素的text添加到集合strSrc里
			List<AndroidElement> elementNew = super.findElements(by);
			for(AndroidElement ae:elementNew){
				strDes.add(ae.getText());
			}
			flag=this.listStrEquals(strSrc,strDes);
			strSrc.clear();
			strDes.clear();
		}
	}
	
	/**
	 * 设备返回键操作
	 */
	public void pressBack(){
		this.wait(500);
		super.pressKeyCode(AndroidKeyCode.BACK);
	}
	
	/**
	 * 多次返回
	 * @param number
	 */
	public void pressBack(int number){
		if(number>0){
			for(int i=0;i<number;i++){
				this.pressBack();
				System.out.println("执行第"+String.valueOf(i+1)+"次返回");
			}
		}else{
			System.out.println("参数有误");
		}
	}
	
	/**
	 * 设备home键操作
	 */
	public void pressHome(){
		this.wait(500);
		super.pressKeyCode(AndroidKeyCode.HOME);
	}
	
	/**
	 * 设备回车键操作
	 */
	public void pressEnter(){
		this.wait(500);
		super.pressKeyCode(AndroidKeyCode.ENTER);
	}
	
	/**
	 * 手机键盘删除操作
	 */
	public void pressBackspace(){
		this.wait(200);
		super.pressKeyCode(AndroidKeyCode.BACKSPACE);
	}
	
	/**
	 * 多次手机键盘删除操作
	 */
	public void pressBackspace(int numbers){
		if(numbers>0){
			for(int i=0;i<numbers;i++){
				this.pressBackspace();
				System.out.println("执行第"+String.valueOf(i+1)+"次删除");
			}
		}else{
			System.out.println("参数有误");
		}
	}
	
	/**
	 * 唤醒屏幕
	 */
	public void wakeUp() {
		try {
			if(super.isLocked()){
				AndroidSpecific.wakeUp(udid);
			}else{
				System.out.println("未锁屏不用唤醒");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 等待，死等
	 * @param milliSecond
	 */
	public void wait(int milliSecond){
		try {
			Thread.sleep(milliSecond);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * //截图全屏
	 * @param String path
	 * @param String fileName
	 */
	public void takeScreen(String path,String fileName) throws Exception{
		File srcFile=super.getScreenshotAs(OutputType.FILE);
		System.out.println(path+fileName);
		FileUtils.copyFile(srcFile,new File(path+fileName));
	}
	
	/**
	 * //针对元素进行截图
	 * @param AndroidElement element
	 * @param String path
	 * @param String fileName
	 */
	public void takeScreenForElement(AndroidElement element,String path,String fileName) throws Exception{
		 // 获得element的位置和大小
		 Point location = element.getLocation();
		 Dimension size = element.getSize();
		 byte[] imageByte=super.getScreenshotAs(OutputType.BYTES);
		 // 创建全屏截图
		 BufferedImage originalImage =ImageIO.read(new ByteArrayInputStream(imageByte));
		 // 截取element所在位置的子图。
		 BufferedImage croppedImage = originalImage.getSubimage(
		  location.getX(),
		  location.getY(),
		  size.getWidth(),
		  size.getHeight());
		 try {
			 System.out.println(path+fileName+".png");
			  ImageIO.write(croppedImage, "png", new File(path+fileName+".png"));
				//ImageIO.write(im, formatName, output)
		 }catch (IOException e) {
				// TODO Auto-generated catch block
			  e.printStackTrace();
		 }
		 
	}
	
	
//	 /**
//     * 打开指定的App和Activity,只能打开起始Activity
//     * @param activityName activity的名字
//     */
//    public void startActivity(String activityName,String appPackage) {
//    	Activity ac = new Activity(appPackage,activityName);
//        super.startActivity(ac.setAppWaitActivity());
//    }
//    
	 /**
     * 打开指定的App和Activity,只能打开起始Activity
     * @param activityName activity的名字activityName
     */
    public void startActivity(String appPackage,String activityName) {
    	try {
    		Activity ac = new Activity(appPackage,activityName);
    		super.startActivity(ac);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("打开指定的App和Activity出现异常");
		}
    }
    
    /**
     * 获取当前界面的所有EditText，并依次输入内容
     * @param str 要输入的数组
     * @param elementLists 元素集合
     */
    public void inputManyText(List<AndroidElement> elementLists,String[] str) {
    	if (!elementLists.isEmpty()&&elementLists!=null) {
    		if (str.length==elementLists.size()) {
    			for (int i = 0; i < str.length; i++) {
                	elementLists.get(i).click();
                    clearText(elementLists.get(i));   //清除内容
                    elementLists.get(i).sendKeys(str[i]);
                }
			}else{
				System.out.println("数组的长度跟元素集合长度不一样");
			}
		}else{
			System.out.println("元素集合为空或为null");
		}
    }
    
    /**
     * 逐字删除编辑框中的文字
     *
     * @param element 文本框架控件
     */
    public void clearText(AndroidElement element) {
        String text = element.getText();
        //跳到最后
        super.pressKeyCode(KEYCODE_MOVE_END);
        for (int i = 0; i < text.length(); i++) {
            //循环后退删除
        	super.pressKeyCode(BACKSPACE);
        }

    }
    
    /**
     * 坐标滑动  单位毫秒
     * @param seconds 毫秒
     * @param element 文本框架控件
     */
	public void swipe(int startx, int starty, int endx, int endy, int seconds) {
        (new TouchAction(this)).press(startx, starty).waitAction(Duration.ofMillis(seconds)).moveTo(endx, endy).release().perform();
    }
    
}
