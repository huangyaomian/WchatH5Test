package cn.h5.appium.page;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.Response;

import cn.h5.appium.base.AndroidDriverBase;
import cn.h5.appium.base.AndroidElementBase;

import com.google.common.collect.ImmutableMap;

/**
 * @author Administrator
 *
 */
public class BasePage extends RemoteWebElement {
	public String curActivity;
	public String pageSource;
	public AndroidDriverBase driver;

	public BasePage(AndroidDriverBase driver) {
		this.driver = driver;
		this.curActivity = getCurActivity();
		this.pageSource = getPageSource();
	}

	
	/**
	 * 获取当前activity
	 * @return
	 */
	public String getCurActivity() {
		return driver.currentActivity();
	}

	/**
	 * 获取当前页面源码
	 * @return
	 */
	public String getPageSource() {
		return driver.getPageSouce();
	}
	
	
	
/*	@Override
	public String getText() {
		Response response = execute(DriverCommand.GET_ELEMENT_TEXT, ImmutableMap.of("id", id));
		return (String) response.getValue();
	}*/
	
	/**
	 * @param by
	 * @return 返回文本
	 */
	public String getMyText(By by){
		try {
			return driver.findElement(by).getText();
		} catch (Exception e) {
			System.out.println("获取不到文本");
			return null;
		}
	}
	
	/**
	 * @param AndroidElement
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


	// 输入
	public void sendkeys(AndroidElement element, String value) {
		if (element != null && value != null) {
			element.sendKeys(value);
		} else {
			System.out.println("输入失败，元素没有定位到，是null 或者 value为空不输入");
		}
	}

	/**
	 * 输入数字英文字母
	 * @param by
	 * @param value
	 */
	public void setValue(By by, String value) {
		AndroidElement element = driver.findElement(by);
		try {
			setValue(element, value);
		} catch (Exception e) {
			System.out.println("出现异常了");
			// TODO: handle exception
		}
		
	}
	
	/**
	 * @param element
	 * @param value
	 */
	public void setValue(AndroidElement element, String value) {
		if (element != null && value!= null) {
			element.setValue(value);
		} else {
			System.out.println("点击失败，元素没有定位到，是null");
		}
	}
	
	
	/**
	 * 输入中文
	 * @param by
	 * @param value
	 */
	public void replaceValue(By by, String value) {
		AndroidElement element = driver.findElement(by);
		try {
			replaceValue(element, value);
		} catch (Exception e) {
			System.out.println("出现异常了");
			// TODO: handle exception
		}
		
	}
	
	/**
	 * 输入中文
	 * @param element
	 * @param value
	 */
	public void replaceValue(AndroidElement element, String value) {
		if (element != null && value!= null) {
			element.replaceValue(value);
		} else {
			System.out.println("点击失败，元素没有定位到，是null");
		}
	}

	// 直接定位并输入
	public void sendkeys(By by, String value) {
		AndroidElement element = driver.findElement(by);
		sendkeys(element, value);
	}

	
	/**
	 * @param element
	 */
	public void click(AndroidElement element) {
		if (element != null) {
			element.click();
		} else {
			System.out.println("点击失败，元素没有定位到，是null");
		}
	}
	
	/**
	 * // 点击元素
	 * @param element
	 */
	public void click(AndroidElementBase element) {
		if (element != null) {
			element.click();
		} else {
			System.out.println("点击失败，元素没有定位到，是null");
		}
	}

	/**
	 * 定位并点击
	 * @param by
	 */
	public void click(By by) {
		AndroidElement element = driver.findElement(by);
		if (element!=null) {
			click(element);
		}
		
	}

	// 清除
	public void clear(AndroidElement element) {
		if (element != null) {
			element.clear();
		} else {
			System.out.println("清除失败，元素没有定位到，是null");
		}
	}

	// 逐个清除，对于密码输入框是无效的
	public void clearOneByOne(AndroidElement element) {
		if (element != null) {
			element.click();
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_MOVE_END);
			String text = element.getText();
			for (int i = 0; i < text.length(); i++) {
				driver.pressBackspace();
			}
		} else {
			System.out.println("逐个清除失败，元素没有定位到，是null");
		}
	}

	// 输入内容直到正确
	public void sendkeysUntilCorrect(AndroidElement element, String str) {
		if (element != null) {
			boolean flag = true;
			element.sendKeys(str);
			while (flag) {
				if (str.equals(element.getText())) {
					flag = false;
				} else {
					element.sendKeys(str);
				}
			}
		} else {
			System.out.println("输入内容直到正确失败，元素为null");
		}
	}

	// 逐个输入数字，模拟的是键盘输入，15610112934
	public void sendKeysOneByOne(String text) {
		char[] chr = text.toCharArray();// {1,5,6,1,x,x,x,x}
		for (int i = 0; i < chr.length; i++) {
			int c = Integer.valueOf(String.valueOf(chr[i]));
			int number = 0;
			switch (c) {
			case 0:
				// driver.pressKeyCode(AndroidKeyCode.KEYCODE_0);
				number = AndroidKeyCode.KEYCODE_0;
				break;
			case 1:
				number = AndroidKeyCode.KEYCODE_1;
				break;
			case 2:
				number = AndroidKeyCode.KEYCODE_2;
				break;
			case 3:
				number = AndroidKeyCode.KEYCODE_3;
				break;
			case 4:
				number = AndroidKeyCode.KEYCODE_4;
				break;
			case 5:
				number = AndroidKeyCode.KEYCODE_5;
				break;
			case 6:
				number = AndroidKeyCode.KEYCODE_6;
				break;
			case 7:
				number = AndroidKeyCode.KEYCODE_7;
				break;
			case 8:
				number = AndroidKeyCode.KEYCODE_8;
				break;
			case 9:
				number = AndroidKeyCode.KEYCODE_9;
				break;
			default:
				System.out.println("数值不对");
				break;
			}
			driver.pressKeyCode(number);
		}
	}

	// 坐标元素点击，针对一些能定位到整体元素但具体元素无法定位并且具有规律性的元素,获取每一个子元素的中心点坐标
	// 思路：获取整体元素，将整体元素分为多行多列元素，取每一个子元素的中心坐标进行点击
	public List<int[]> getElementByCoordinates(AndroidElement element,
			int rows, int columns) {
		int[] coordinate = new int[2];
		List<int[]> elementResolve = new ArrayList<int[]>();
		if (element != null) {
			int startx = element.getLocation().getX();// 起始点坐标x
			int starty = element.getLocation().getY();// 起始点坐标y
			int offsetx = element.getSize().getWidth();// 该元素的宽
			int offsety = element.getSize().getHeight();// 该元素的高
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					coordinate[0] = startx + (offsetx / 2 * columns)
							* (2 * j + 1);
					coordinate[1] = starty
							+ (offsety / (2 * rows) * (2 * i + 1));
					elementResolve.add(coordinate);
				}
			}
		}
		return elementResolve;
	}

	// 根据整体元素拆分后的规律子元素索引点击元素
	public void clickElementByCoordinate(AndroidElement element, int rows,
			int columns, int index) {
		if (element != null) {
			List<int[]> elementResolve = getElementByCoordinates(element, rows,
					columns);
			if (!elementResolve.isEmpty() && elementResolve != null) {
				driver.clickByCoordinate(elementResolve.get(index)[0],
						elementResolve.get(index)[1]);
			} else {
				System.out.println("坐标集合为空");
			}
		} else {
			System.out.println("元素为null");
		}
	}

	// 输入安全键盘的密码, 128606
	public void sendkeysPwd(List<int[]> pwd, int[] password) {
		if (password.length > 0) {
			for (int i = 0; i < password.length; i++) {
				if (password[i] == 0) {
					driver.clickByCoordinate(pwd.get(10)[0], pwd.get(10)[1]);
				} else {
					driver.clickByCoordinate(pwd.get(password[i] - 1)[0],
							pwd.get(password[i] - 1)[1]);
				}
			}
		}
	}

	// 输入安全键盘的密码, 128606
	public void sendkeysPwd(AndroidElement element, int rows, int columns,
			int[] password) {
		if (element != null) {
			List<int[]> pwd = getElementByCoordinates(element, rows, columns);
			if (password.length > 0) {
				for (int i = 0; i < password.length; i++) {
					if (password[i] == 0) {
						driver.clickByCoordinate(pwd.get(10)[0], pwd.get(10)[1]);
					} else {
						driver.clickByCoordinate(pwd.get(password[i] - 1)[0],
								pwd.get(password[i] - 1)[1]);
					}
				}
			}
		}

	}

	// 九宫格手势解锁,参数indexs是密码数字组成的数组，参数indexs={1,2,5,6,8,9}
	/**
	 * 九宫格手势解锁
	 * 
	 * @throws
	 */
	public void wakeByGestures(AndroidElement element, int[] indexs) {
		if (element != null) {
			List<int[]> elementResolve = getElementByCoordinates(element, 3, 3);
			TouchAction ta = null;
			if (indexs.length > 0) {
				ta = new TouchAction(driver).press(
						elementResolve.get(indexs[0] - 1)[0],
						elementResolve.get(indexs[0] - 1)[1]).waitAction(
						Duration.ofMillis(500));
			}
			for (int i = 1; i < indexs.length; i++) {
				ta.moveTo(
						elementResolve.get(indexs[i] - 1)[0]
								- elementResolve.get(indexs[i - 1] - 1)[0],
						elementResolve.get(indexs[i] - 1)[1]
								- elementResolve.get(indexs[i - 1] - 1)[1])
						.waitAction(Duration.ofMillis(500));
			}
			ta.release().perform();
		}
	}
	
	
	
	/**
	 * 从字符串中获取所有的数字
	 * 
	 * @throws 
	 */
	public String getNumForString(String string) {
		String regEx="[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(string);
		return m.replaceAll("").trim();
	}
}
