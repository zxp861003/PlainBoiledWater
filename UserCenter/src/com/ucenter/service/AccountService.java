package com.ucenter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fire.domain.ResultList;
import com.fire.webbase.common.exception.ApplicationException;
import com.ucenter.channel.channel.ChannelFactory;
import com.ucenter.channel.channel.IChannel;
import com.ucenter.common.AuthInfo;
import com.ucenter.common.ChannelConstants;
import com.ucenter.persistence.dbbean.AccountBean;
import com.ucenter.persistence.dbbean.AccountServer;
import com.ucenter.persistence.provider.AccountDataProvider;
import com.ucenter.persistence.provider.AccountServerDataProvider;

public class AccountService
{
	private static Map<String,AccountBean> accountMap = new HashMap<String,AccountBean>();
	private static Map<Integer,AccountBean> accountIdMap = new HashMap<Integer,AccountBean>();
	private static Map<Integer,AccountBean> dataIdMap = new HashMap<Integer,AccountBean>();
	private static Map<Integer,List<AccountServer>> accountServerListMap = new HashMap<Integer,List<AccountServer>>();
	
	private final static AccountService _instance = new AccountService();
	public final static AccountService getInstance() {
		return _instance;
	}
	
	public AccountService() { }

	@SuppressWarnings("unchecked")
	public static void initAccountData(){
		ResultList list = null;
		AccountDataProvider provider = AccountDataProvider.getInstance();
		try {
			list = provider.getAccountList(null);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
		if(list != null){
			List<AccountBean> accountList = list;
			for(AccountBean ac : accountList){
				accountIdMap.put(ac.getAccountId(),ac);
				accountMap.put(ac.getEmail(),ac);
				dataIdMap.put(ac.getId(),ac);
			}
		}
		
		AccountServerDataProvider serversProvider = AccountServerDataProvider.getInstance();
		try {
			list = serversProvider.findAll(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(list != null){
			List<AccountServer> accountServerList = list;
			List<AccountServer> tmpList = null;
			for(AccountServer as : accountServerList){
				tmpList = accountServerListMap.get(as.getAccountId());
				if(tmpList == null){
					tmpList = new ArrayList<AccountServer>();
					accountServerListMap.put(as.getAccountId(), tmpList);
				}
				tmpList.add(as);
			}
		}
		
	}
	
	public static synchronized AccountBean searchAccount(long accountId) {
		return accountIdMap.get((int)accountId);
	}
	public static synchronized AccountBean searchAccount(String account) {
		return accountMap.get(account);
	}
	public static synchronized AccountBean searchAccountById(int id) {
		return dataIdMap.get(id);
	}

	public static ResultList getAccountList(String deviceId) {
		AccountDataProvider provider = AccountDataProvider.getInstance();
		try {
			ResultList accountList = provider.getAccountList(deviceId);
			return accountList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized AccountBean userAuth(long sessionId,String account,String pwd, int userId,String sessionKey, String channel,int gameId, int areaId) {
		//TODO 暂时都走DeafaultChannel  后期具体实现
		IChannel channelObj = ChannelFactory.getChannel(channel);
		AccountBean accountBean = null;
		if(channelObj != null){
			
			AuthInfo auth = channelObj.auth(userId, pwd, channel);			
			if(auth != null){
				AccountDataProvider provider = AccountDataProvider.getInstance();
//				accountBean = searchAccount(userId);    // 正式环境用这个    查询不到则直接返回错误！
				accountBean = searchAccount(account);   //TODO 测试用这个
				if(accountBean == null){    
					accountBean = new AccountBean();
					accountBean.setNew(true);
					accountBean.setAccountId(userId);  //测试时这么用  正式环境不对
					accountBean.setEmail(account);   //暂时存在这 方便测试查询
					accountBean.setPassword(pwd);
					accountBean.setChannelId(ChannelConstants.getChannelCode(channel));
					accountBean.setCreateTime(new Date());
					accountBean.setGameId(gameId);
				} 
				accountBean.setLastAreaId(areaId);
				accountBean.setLastLoginTime(new Date());
				try {
					provider.save(accountBean);
					if(!accountMap.containsKey(accountBean.getEmail())){
						accountMap.put(accountBean.getEmail(), accountBean);
						accountIdMap.put(accountBean.getAccountId(), accountBean);
						dataIdMap.put(accountBean.getId(), accountBean);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				updateAccountServer(accountBean);
			}
		}
		
		return accountBean;
	}

	public synchronized void updateAccountServer(AccountBean accountBean){
		List<AccountServer> tmpList = accountServerListMap.get(accountBean.getAccountId());
		if(tmpList == null){
			tmpList = new ArrayList<AccountServer>();
			accountServerListMap.put(accountBean.getAccountId(), tmpList);
		}
		AccountServer as = null;
		for(AccountServer s : tmpList){
			if(s.getServerId() == accountBean.getLastAreaId()){
				as = s;
				break;
			}
		}
		if(as == null){
			as = new AccountServer();
			as.setNew(true);
			as.setAccountId(accountBean.getAccountId());
			as.setGameId(accountBean.getGameId());
			as.setServerId(accountBean.getLastAreaId());
			as.setLastLoginTime(System.currentTimeMillis());
			AccountServerDataProvider serversProvider = AccountServerDataProvider.getInstance();
			try {
				serversProvider.save(as);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static synchronized List<AccountServer> getAccountServerList(int accountId){
		return accountServerListMap.get(accountId);
	}
	
	public static synchronized AccountBean updateAccountInfo(int accountId,String nickName){
		
		AccountBean accountBean = searchAccount(accountId);
		if(accountBean == null){
			accountBean = new AccountBean();
			accountBean.setNew(true);
			accountBean.setAccountId(accountId);
//			accountBean.setEmail(nickName);
			accountBean.setDeviceName(nickName);  //TODO 暂时先设置在这  正式环境再换
			accountBean.setCreateTime(new Date());
		}
		accountBean.setLastLoginTime(new Date());
		AccountDataProvider provider = AccountDataProvider.getInstance();
		try {
			provider.save(accountBean);
			if(!accountMap.containsKey(accountBean.getEmail())){
				accountMap.put(accountBean.getEmail(), accountBean);
				accountIdMap.put(accountBean.getAccountId(), accountBean);
				dataIdMap.put(accountBean.getId(), accountBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return accountBean;
	}
}
