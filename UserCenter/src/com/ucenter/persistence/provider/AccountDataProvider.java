package com.ucenter.persistence.provider;

import java.util.HashMap;
import java.util.Map;

import com.fire.domain.ResultList;
import com.fire.persistence.dao.ISimpleEntityDao;
import com.fire.persistence.dao.SimpleDaoFactory;
import com.fire.webbase.common.exception.ApplicationException;
import com.ucenter.persistence.dao.IAccountBeanDao;
import com.ucenter.persistence.dbbean.AccountBean;
import com.ucenter.persistence.provider.base.ChargeBaseProvider;

public class AccountDataProvider extends ChargeBaseProvider
{

	private static AccountDataProvider _instance = null;
	protected ISimpleEntityDao getDao()
	{
		return SimpleDaoFactory.getDao(IAccountBeanDao.class, getSystemKey());
	}

	public synchronized static AccountDataProvider getInstance()
	{
		if (_instance == null)
		{
			_instance = new AccountDataProvider();
		}
		return _instance;
	}
	
	// *****************
	public AccountBean findAccount(String userName) throws ApplicationException
	{
		if (userName == null || userName.length() <= 0)
			return null;

		IAccountBeanDao dao = (IAccountBeanDao)getDao();
		return dao.findAccount(userName);
	}
	
	public ResultList getAccountList(String deviceId) throws ApplicationException
	{
		Map<String,String> searchParams = new HashMap<String,String>();
		
		if (deviceId != null && deviceId.length() > 0)
			searchParams.put("deviceType", deviceId);
	
		IAccountBeanDao dao = (IAccountBeanDao)getDao();
		return dao.findAll(searchParams);
	}
	
}
