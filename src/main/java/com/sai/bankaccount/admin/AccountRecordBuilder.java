package com.sai.bankaccount.admin;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @since 30 oct 2017
 * @author sai
 */
public class AccountRecordBuilder
{

  private AccountRecord accountRecord;

  public AccountRecordBuilder()
  {
    accountRecord = new AccountRecord();
  }

  public AccountRecordBuilder createBuilder()
  {
    return this;
  }

  public AccountRecordBuilder id(String id)
  {
    accountRecord.setAccountRecordId(id);
    return this;
  }

  public AccountRecordBuilder amount(BigDecimal amount)
  {
    accountRecord.setAmount(amount);
    return this;
  }

  public AccountRecordBuilder accountType(AccountType accountType)
  {
    accountRecord.setAccountType(accountType);
    return this;
  }

  public AccountRecordBuilder date(Date date)
  {
    accountRecord.setDate(date);
    return this;
  }

  public AccountRecord build()
  {
    return accountRecord;
  }
}
