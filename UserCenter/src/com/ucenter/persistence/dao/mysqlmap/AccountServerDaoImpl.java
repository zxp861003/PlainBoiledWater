package com.ucenter.persistence.dao.mysqlmap;

import com.fire.persistence.dao.entitydao.EntityDaoImpl;
import com.ibatis.dao.client.DaoManager;
import com.ucenter.persistence.dao.IAccountServerDao;

public class AccountServerDaoImpl extends EntityDaoImpl implements IAccountServerDao
{

	public AccountServerDaoImpl(DaoManager daoManager)
	{
		super(daoManager);
	}

	protected void init()
	{
		dataName = "AccountServer";
		tableIdName = "id";
	}


}