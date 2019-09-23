package com.ucenter.persistence.dao.mysqlmap;

import com.fire.persistence.dao.entitydao.EntityDaoImpl;
import com.fire.webbase.common.exception.ApplicationException;
import com.ibatis.dao.client.DaoManager;
import com.ucenter.persistence.dao.IAccountBeanDao;
import com.ucenter.persistence.dbbean.AccountBean;

public class AccountBeanDaoImpl extends EntityDaoImpl implements IAccountBeanDao
{

	public AccountBeanDaoImpl(DaoManager daoManager)
	{
		super(daoManager);
	}

	protected void init()
	{
		dataName = "AccountBean";
		tableIdName = "id";
	}

	public AccountBean findAccount(String sdkUserId) throws ApplicationException
	{
		return (AccountBean) this.queryForObject("findAccount", sdkUserId);
	}

//	@Override
//	public AccountBean searchAccount(long accountId)
//	{
//		return (AccountBean) this.queryForObject("selectAccount", accountId);
//	}

}