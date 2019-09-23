package com.ucenter.game.action;

import kilim.Pausable;

import com.fire.net.channelobj.RemoteNode;
import com.fire.net.message.handler.AbstractSessionAction;
import com.ucenter.game.message.AGCheckTokenMessage;
import com.ucenter.game.message.GACheckTokenMessage;
import com.ucenter.persistence.dbbean.AccountBean;
import com.ucenter.service.AccountService;

public class GACheckTokenAction extends AbstractSessionAction<GACheckTokenMessage> {

	@Override
	public void processMessage(GACheckTokenMessage message, RemoteNode p) throws Pausable {
		//TODO 服务器二次认证     
		AGCheckTokenMessage sendMsg = new AGCheckTokenMessage();
		AccountBean account = AccountService.getInstance().userAuth(message.getSessionId(),message.getAccount(),message.getPassword(),message.getAccountId(), message.getSessionKey(), message.getChannel(), message.getGameId(),message.getAreaId());
		
		if(account != null) {
			sendMsg.setAccount(account);
			sendMsg.setUserSessionId(message.getUserSessionId());
		}

		p.writeAndFlush(sendMsg);
	}

	private GACheckTokenAction() {

	}

	// *************************************
	private final static GACheckTokenAction _instance = new GACheckTokenAction();

	public final static GACheckTokenAction getInstance() {
		return _instance;
	}

}
