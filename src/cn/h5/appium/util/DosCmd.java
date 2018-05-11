package cn.h5.appium.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangyaomian
 *
 */

/**
 * 此类完成dos或shell命令的执行封装
 * 
 */
public class DosCmd {
	private Log logger = Log.getLogger(DosCmd.class);
	String osName = System.getProperty("os.name");

	/**
	 * execute dos command执行doc命令
	 * 
	 * @param dos
	 *            command,String
	 * @return boolean.succeed is true,Failure is false
	 * 
	 */
	public boolean execCmd(String cmdString) {
		Runtime p = Runtime.getRuntime();
//		Process process = null;
		try {
			if (osName.toLowerCase().contains("mac")) {
				String[] command = { "/bin/sh", "-c", cmdString };
				p.exec(command);
			} else if (osName.toLowerCase().contains("win")) {
				p.exec("cmd /c " + cmdString);
			}
			System.out.println("正在执行指令，请等待10s");
			Thread.sleep(10000);
			System.out.println("dos命令执行完成");
			logger.debug("execute  command " + cmdString + " Succeed");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error("execute  command " + cmdString + " Failure", e);
			return false;
		}
	}

	/**
	 * get result of command, after execute dos command
	 * 
	 * @param dos
	 *            command,String
	 * @return List<String>
	 */
	public List<String> execCmdConsole(String cmdString) throws InterruptedException {
		List<String> dosRes = new ArrayList<String>();
		try {
			Process process = null;
			System.out.println(cmdString);
			if (osName.toLowerCase().contains("mac")) {
				String[] command = { "/bin/sh", "-c", cmdString };
				process = Runtime.getRuntime().exec(command);
			} else if (osName.toLowerCase().contains("win")) {
				process = Runtime.getRuntime().exec("cmd /c " + cmdString);
			}
			InputStream in = process.getInputStream();
			BufferedReader inr = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = inr.readLine()) != null) {
				dosRes.add(line);
			}
			process.waitFor();
			process.destroy();
			logger.debug("get result of command after execute dos command " + cmdString + " Succeed ");
		} catch (IOException e) {
			logger.error("get result of command after execute dos command " + cmdString + " Failure", e);
		}
		return dosRes;
	}

	/**
	 * get result of command, after execute dos command
	 * 
	 * @param dos
	 *            command,String
	 * @return List<String>
	 */
	public void execCmdInput(String cmdString, String uuid) throws InterruptedException {
		try {
			Process process = null;
			System.out.println(cmdString);
			if (osName.toLowerCase().contains("mac")) {
				String[] command = { "/bin/sh", "-c", "adb shell input text" + cmdString };
				process = Runtime.getRuntime().exec(command);
			} else if (osName.toLowerCase().contains("win")) {
				process = Runtime.getRuntime().exec("cmd /c adb -s " + uuid + " shell input text " + cmdString);
			}
			process.waitFor();
			process.destroy();
			logger.debug("get result of command after execute dos command " + cmdString + " Succeed ");
		} catch (IOException e) {
			logger.error("get result of command after execute dos command " + cmdString + " Failure", e);
		}
	}

	/**
	 * kill server by pid of server
	 * 
	 * @param pid
	 * @return boolean
	 */
	public boolean killServer() {
		System.err.println("开始关闭appium");
		String command = "taskkill -F -PID node.exe";
		if (osName.toLowerCase().contains("mac")) {
			command = "killall node";
		} else if (osName.toLowerCase().contains("win")) {
			command = "taskkill -F -PID node.exe";
		} else {
			command = "taskkill -F -PID node.exe";
		}
		if (execCmd(command)) {
			logger.debug("kill server node  Succeed");
			return true;
		} else {
			logger.error("kill server node Failure");
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("os.name"));
		DosCmd dc = new DosCmd();
		List<String> devicesList = dc.execCmdConsole("appium");
		dc.killServer();
		for (String string : devicesList) {
			System.out.println(string);
		}
	}
}
