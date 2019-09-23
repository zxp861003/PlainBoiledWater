package com.ucenter.persistence.provider;

import com.fire.persistence.dao.ISimpleEntityDao;
import com.fire.persistence.dao.SimpleDaoFactory;
import com.fire.webbase.common.exception.ApplicationException;
import com.ucenter.persistence.dao.IIpFilterDao;
import com.ucenter.persistence.dbbean.IpFilter;
import com.ucenter.persistence.provider.base.ChargeBaseProvider;

public class IpFilterDataProvider extends ChargeBaseProvider
{
	private static IpFilterDataProvider _instance = null;
	protected ISimpleEntityDao getDao()
	{
		return SimpleDaoFactory.getDao(IIpFilterDao.class, getSystemKey());
	}

	public synchronized static IpFilterDataProvider getInstance()
	{
		if (_instance == null)
		{
			_instance = new IpFilterDataProvider();
		}
		return _instance;
	}
	
	// ********************
	public IpFilter saveIpFilter(IpFilter filter) throws ApplicationException
	{
		if (filter == null)
			return null;

		IpFilter co = null;
		IIpFilterDao dao = (IIpFilterDao)getDao();
		try
		{
			co = (IpFilter) dao.save(filter);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return co;
	}
}
