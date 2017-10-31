package com.sai.bankaccount.admin;

import java.util.List;

/**
 * 
 * @since 30 oct 2017
 * @author sai
 */
public class BankAccountBuilder {
	
	private BankAccount bankAccount;

	public BankAccountBuilder() {
		bankAccount = new BankAccount();
	}
	
	public BankAccountBuilder createBuilder() {
		return this;
	}
	
  public BankAccountBuilder id(BankAccountId id)
  {
    bankAccount.setBankAccountId(id);
		return this;
	}
	
  public BankAccountBuilder customer(CustomerId id)
  {
    bankAccount.setCustomerId(id);
		return this;
	}
	
	public BankAccountBuilder records(List<AccountRecord> records) {
		bankAccount.setRecords(records);
		return this;
	}
	
	public BankAccountBuilder record(AccountRecord record) {
		bankAccount.getRecords().add(record);
		return this;
	}
	
	public BankAccount build() {
		return bankAccount;
	}
	
}
