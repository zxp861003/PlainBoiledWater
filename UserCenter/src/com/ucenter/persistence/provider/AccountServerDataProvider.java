package com.ucenter.persistence.provider;

import com.fire.persistence.dao.ISimpleEntityDao;
import com.fire.persistence.dao.SimpleDaoFactory;
import com.ucenter.persistence.dao.IAccountServerDao;
import com.ucenter.persistence.provider.base.ChargeBaseProvider;

public class AccountServerDataProvider extends ChargeBaseProvider
{

	private static AccountServerDataProvider _instance = null;
	protected ISimpleEntityDao getDao()
	{
		return SimpleDaoFactory.getDao(IAccountServerDao.class, getSystemKey());
	}

	public synchronized static AccountServerDataProvider getInstance()
	{
		if (_instance == null)
		{
			_instance = new AccountServerDataProvider();
		}
		return _instance;
	}

}
