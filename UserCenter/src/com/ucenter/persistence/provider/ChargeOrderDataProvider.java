package com.ucenter.persistence.provider;

import com.fire.persistence.dao.ISimpleEntityDao;
import com.fire.persistence.dao.SimpleDaoFactory;
import com.fire.webbase.common.exception.ApplicationException;
import com.ucenter.persistence.dao.IChargeOrderDao;
import com.ucenter.persistence.dbbean.ChargeOrder;
import com.ucenter.persistence.provider.base.ChargeBaseProvider;

public class ChargeOrderDataProvider extends ChargeBaseProvider
{

	private static ChargeOrderDataProvider _instance = null;
	protected ISimpleEntityDao getDao()
	{
		return SimpleDaoFactory.getDao(IChargeOrderDao.class, getSystemKey());
	}

	public synchronized static ChargeOrderDataProvider getInstance()
	{
		if (_instance == null)
		{
			_instance = new ChargeOrderDataProvider();
		}
		return _instance;
	}

	// ********************
	public ChargeOrder saveOrder(ChargeOrder order) throws ApplicationException
	{
		if (order == null)
			return null;

		ChargeOrder co = null;
		IChargeOrderDao dao = (IChargeOrderDao)getDao();
		try
		{
			co = (ChargeOrder) dao.save(order);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return co;
	}
}
