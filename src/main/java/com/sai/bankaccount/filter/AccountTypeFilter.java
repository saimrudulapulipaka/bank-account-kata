package com.sai.bankaccount.filter;

import com.sai.bankaccount.admin.AccountType;

/**
 * 
 * @since 30 oct 2017
 * @author sai
 */
public class AccountTypeFilter extends Filter {

  private AccountType accountType;

  public AccountTypeFilter(AccountType accountType)
  {
		super();
    this.setAccountType(accountType);
    this.predicate = x -> accountType.equals(x.getAccountType());
	}

  public AccountType getAccountType()
  {
    return accountType;
  }

  public void setAccountType(AccountType accountType)
  {
    this.accountType = accountType;
  }
}
