package com.ucenter.game;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.fire.net.message.AbstractMessage;
import com.fire.net.message.handler.AbstractSessionAction;
import com.fire.net.server.base.gm.GMSocketServer;
import com.ucenter.ServiceManager;

/**
 */
public class UCenterServer
{
	private static final Logger logger = Logger.getLogger(UCenterServer.class);

	public static UCenterServer instance;
	public static GMSocketServer sServer;
	/**
	 * 服务器启动成功时间戳
	 */

	private UCenterServer()
	{
	}

	public static void startUCServer()
	{
		UCenterServer ucServer = UCenterServer.getInstance();
		try
		{
			logger.error("============当前版本启动时间：" + getCurrentTime() + "============");
			ucServer.start();

		} catch (Exception e)
		{
			logger.error("服务初始失败!", e);
		}

	}
	public static UCenterServer getInstance()
	{
		if (instance == null)
		{
			instance = new UCenterServer();
		}
		return instance;
	}

	public void systemExit()
	{
		logger.error("系统进程关闭！System.exit(0)");
		System.exit(0);
	}

	/**
	 * 准备启动服务的数据
	 */
	public boolean start()
	{
		try
		{
			sServer = new GMSocketServer();
			sServer.setListeningPort(28003);
			sServer.initialize();
			ServiceManager.initUCMessageHandler();
			sServer.startService();
			
			
			
//			int servicePort = StrUtils.parseint(Global.getProperty("SERVICE_PORT"), 4180);
//			GMSocketServer sServer = new GMSocketServer();
//			sServer.setListeningPort(servicePort);
//			sServer.initialize();
//			sServer.addHandler(LogConst.CS_PREHAND, CSPreHandMessage.class, PreHandAction.getInstance());
//			sServer.addHandler(LogConst.CS_MSG_LOG, CSGeneralLogMessage.class, GeneralLogAction.getInstance());
//			sServer.startService();
			
			
//			UCMessageInitializer messageInitializer = new UCMessageInitializer();
//			SimpleSSNettyInitializer initializer = new SimpleSSNettyInitializer(messageInitializer);
			
//			SimpleServer simpleServer = new SimpleServer();
//			simpleServer.initialize(initializer, messageInitializer);

//			SocketAddress address = new InetSocketAddress(4190);
//			simpleServer.openPort(address);
			logger.error("ucenter服务启动! " + 28003);
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 当前启动服务器时间
	 * 
	 * @return
	 */
	private static final String getCurrentTime()
	{
		String currentTime = "";
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			currentTime = sdf.format(new Date());
		} catch (Exception e)
		{
			logger.error("auth服务启动时获取启动时间失败，错误信息：" + e.getMessage(), e);
		}
		return currentTime;
	}

	public static void addMessageHandler(int commandId,Class<? extends AbstractMessage> msg,AbstractSessionAction handler){
		sServer.addHandler(commandId, msg, handler);
	}
}
