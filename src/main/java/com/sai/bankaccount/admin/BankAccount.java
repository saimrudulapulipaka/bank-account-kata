package com.sai.bankaccount.admin;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;


/**
 * 
 * @since 30 oct 2017
 * @author sai
 */
public class BankAccount {

	private static final String NEW_LINE = "\n";
	private static final String DOUBLE_NEW_LINE = "\n\n";
	private static final String TAB = "\t";
	
	private static final String STATMENT_HEADER = 
			"+-----------+------------+-------------+--------------+\n" +
			"|    ID     |    Date    |  OPERATION  |    Amount    |\n" +
			"+-----------+------------+-------------+--------------+\n";
	
	private static final String STATMENT_FOOTER =
			"+-----------+------------+-------------+--------------+";
	
	private BankAccountId bankAccountId;
	private CustomerId customerId;
	private List<AccountRecord> records;
  private double balance;

	public BankAccount() {
		records = Lists.newArrayList();
	}

	public BankAccount(BankAccountId bankAccountId, CustomerId customerId,
			List<AccountRecord> records) {
		super();
		this.bankAccountId = bankAccountId;
		this.customerId = customerId;
		this.records = records;
	}

	public String print(Optional<List<AccountRecord>> filteredAccountRecord) {
		List<AccountRecord> localRecords = this.records;
		if(filteredAccountRecord.isPresent()) {
			localRecords = filteredAccountRecord.get();
		}
		
		StringBuilder sb = new StringBuilder();
		String bankAccountIdStr = bankAccountId.toString();
		String customerIdStr = customerId.toString();
		
    sb.append("BankAccountId:").append(bankAccountIdStr);
		sb.append(NEW_LINE);
    sb.append("CustomerId:").append(customerIdStr);
		sb.append(DOUBLE_NEW_LINE).append(STATMENT_HEADER);

		for(AccountRecord record : localRecords) {
			record.print(sb);
		}
		
		sb.append(STATMENT_FOOTER);
		
		return sb.toString();
	}

	public BankAccountId getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(BankAccountId bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public CustomerId getCustomerId() {
		return customerId;
	}

	public void setCustomerId(CustomerId customerId) {
		this.customerId = customerId;
	}

	public List<AccountRecord> getRecords() {
		return records;
	}

	public void setRecords(List<AccountRecord> records) {
		this.records = records;
	}

  public double getBalance()
  {
    return balance;
  }

  public void setBalance(double balance)
  {
    this.balance = balance;
  }

}
