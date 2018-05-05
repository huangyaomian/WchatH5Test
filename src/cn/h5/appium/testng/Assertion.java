package cn.h5.appium.testng;

import org.testng.Assert;
import org.testng.Reporter;

import cn.h5.appium.base.AndroidDriverBase;
import cn.h5.appium.base.CrazyPath;


public class Assertion  {
	  private  AndroidDriverBase driver;
	  public Assertion(AndroidDriverBase driver){
		  this.driver=driver;
	  }
	  public  void assertEquals(Object actual, Object expected,String fileName){
	        try{
	            Assert.assertEquals(actual, expected);
	        }catch(AssertionError e){
	        	fail(fileName);
	        }
	  }
	  public  void assertEquals(Object actual, Object expected, String fileName,String message){
	        try{
	            Assert.assertEquals(actual, expected, message);
	        }catch(AssertionError e){
	        	fail(fileName,message);
	        }
	  }
	  public  void verifyEquals(Object actual, Object expected,String fileName){
	        try{
	            Assert.assertEquals(actual, expected);
	        }catch(AssertionError e){
	        	try {
	        		driver.takeScreen(CrazyPath.path, "\\images\\"+Thread.currentThread().getId()+fileName);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        }
	  }
	  public  void verifyEquals(Object actual, Object expected,String fileName,String message){
	        try{
	            Assert.assertEquals(actual, expected, message);
	        }catch(AssertionError e){
	           	try {
	        		driver.takeScreen(CrazyPath.path, "\\images\\"+Thread.currentThread().getId()+fileName);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        }
	  }
	  public void fail(String fileName){
		  try {
      			//System.out.println(CrazyPath.path+"\\images\\"+Thread.currentThread().getId()+fileName);
      			Reporter.log("<a href=file:///C:/Users/Administrator/workspace/CrazyAppium1/test-output/html/images/" + Thread.currentThread().getId()+fileName + " target=_blank>Failed Screen Shot</a>", true);  
      			Reporter.log("<img src=file:///C:/Users/Administrator/workspace/CrazyAppium1/test-output/html/images/"+Thread.currentThread().getId()+fileName +" style=width:30px;height:30px img/>", true);
				driver.takeScreen(CrazyPath.path+"\\test-output\\html\\images\\",Thread.currentThread().getId()+fileName);
		  } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
		  Assert.fail();
	  }
	  public void fail(String fileName,String message){
		  try {
      			//System.out.println(CrazyPath.path+"\\images\\"+Thread.currentThread().getId()+fileName);
//      			Reporter.log("<a href=http://localhost:8080/jenkins/job/test/crazyappium/images/" + Thread.currentThread().getId()+fileName + " target=_blank>Failed Screen Shot</a>", true);  
//      			Reporter.log("<img src=http://localhost:8080/jenkins/job/test/crazyappium/images/"+Thread.currentThread().getId()+fileName +" style=width:30px;height:30px img/>", true);
			  Reporter.log("<a href=file:///C:/Users/Administrator/workspace/CrazyAppium1/test-output/html/images/" + Thread.currentThread().getId()+fileName + " target=_blank>Failed Screen Shot</a>", true);  
    			Reporter.log("<img src=file:///C:/Users/Administrator/workspace/CrazyAppium1/test-output/html/images/"+Thread.currentThread().getId()+fileName +" style=width:30px;height:30px img/>", true);
				driver.takeScreen(CrazyPath.path+"\\test-output\\html\\images\\",Thread.currentThread().getId()+fileName);
		  } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
		  Assert.fail(message);
	  }
}
