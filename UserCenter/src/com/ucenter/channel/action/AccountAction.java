package com.ucenter.channel.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fire.utils.StrUtils;
import com.ucenter.channel.action.base.BaseChargeAction;
import com.ucenter.persistence.dbbean.AccountBean;
import com.ucenter.service.AccountService;

public class AccountAction extends BaseChargeAction
{
	protected static final Logger loger = Logger.getLogger(AccountAction.class);

	protected String defaultMethod(HttpServletRequest request, HttpServletResponse response)
	{
		String accountId = request.getParameter("uuid");
		String deviceId = request.getParameter("deviceId");
		
		if(StrUtils.isEmpty(accountId) && StrUtils.isEmpty(deviceId)){
			return "ERROR";
		}
		
		if (!StrUtils.isEmpty(accountId)){
			AccountBean account = AccountService.searchAccount(Integer.parseInt(accountId));
			
			if(account == null)
				return "ERROR";
			
			return account.toJson();
		}

		if(!StrUtils.isEmpty(deviceId)){

			String resultText = "";
			List<AccountBean> accountList = (List<AccountBean>)AccountService.getAccountList(deviceId);
			
			if(accountList != null && accountList.size()>0){
			
				for(int i=0;i<accountList.size();i++){
					resultText = resultText + accountList.get(i).toJson() + "#";
				}
				return resultText.substring(0,resultText.length()-1);
			}else{
				
				return "ERROR";
			}
			
		}	
		return "ERROR";
	}
	
	protected String stateMethod(HttpServletRequest request, HttpServletResponse response)
	{
		String accountuuid = request.getParameter("uuid");
		if (StrUtils.isEmpty(accountuuid))
			return "ERROR";
		
//		AccountService.changeState(accountuuid);
		
		return "OK";
	}
	
}
