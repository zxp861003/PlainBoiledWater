package com.ucenter;

import com.fire.task.CommonTaskService;
import com.ucenter.game.UCenterServer;
import com.ucenter.game.UcenterMessages;
import com.ucenter.game.action.ChargeReceiptAction;
import com.ucenter.game.action.GACheckTokenAction;
import com.ucenter.game.action.PreHandAction;
import com.ucenter.game.message.GACheckTokenMessage;
import com.ucenter.game.message.RSChargeReceiptMessage;
import com.ucenter.game.message.RSPreHandMessage;
import com.ucenter.job.LinkScheduleCheckTask;
import com.ucenter.service.AccountService;

public class ServiceManager
{
	protected void init() throws Exception
	{
		// cache
		UCenterServer.startUCServer();
//		initUCMessageHandler();
		
		activeServices();
		// task
//		CommonTaskService.getInstance().scheduleOnceTask(new ServiceDelayInitializeTask(), 10000);
//		CommonTaskService.getInstance().scheduleAtFixedRate(new OrderScheduleCheckTask(), 15000, 1000);
		CommonTaskService.getInstance().scheduleAtFixedRate(new LinkScheduleCheckTask(), 15000, 60000);
	}

	private void activeServices() {
		AccountService.initAccountData();
	}

	public static void initUCMessageHandler() {
		UCenterServer.addMessageHandler(UcenterMessages.RSMSG_PREHAND,RSPreHandMessage.class, PreHandAction.getInstance());
		UCenterServer.addMessageHandler(UcenterMessages.RSMSG_CHARGE_RECEIPT,RSChargeReceiptMessage.class, ChargeReceiptAction.getInstance());
		UCenterServer.addMessageHandler(UcenterMessages.E_GA_CHECK_TOKEN,GACheckTokenMessage.class, GACheckTokenAction.getInstance());
	}
}
