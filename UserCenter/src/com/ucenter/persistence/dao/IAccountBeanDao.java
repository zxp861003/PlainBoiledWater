package com.ucenter.persistence.dao;

import com.fire.persistence.dao.ISimpleEntityDao;
import com.fire.webbase.common.exception.ApplicationException;
import com.ucenter.persistence.dbbean.AccountBean;

public interface IAccountBeanDao extends ISimpleEntityDao
{
	public AccountBean findAccount(String userName) throws ApplicationException;
//
//	public AccountBean searchAccount(long accountId);
}