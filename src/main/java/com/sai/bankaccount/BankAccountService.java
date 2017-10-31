package com.sai.bankaccount;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sai.bankaccount.admin.AccountRecord;
import com.sai.bankaccount.admin.AccountRecordBuilder;
import com.sai.bankaccount.admin.AccountType;
import com.sai.bankaccount.admin.BankAccount;
import com.sai.bankaccount.filter.Filter;

/**
 * 
 * @since 30 oct 2017
 * @author sai
 */
public class BankAccountService {

	public BigDecimal computeBalance(BankAccount myAccount) {

		double debits = myAccount.getRecords()
			.stream()
        .filter(rec -> AccountType.DEBIT.equals(rec.getAccountType()))
			.mapToDouble(rec -> rec.getAmount().doubleValue())
			.sum();
		
		double credits = myAccount.getRecords()
				.stream()
        .filter(rec -> AccountType.CREDIT.equals(rec.getAccountType()))
				.mapToDouble(rec -> rec.getAmount().doubleValue())
				.sum();
		
		return BigDecimal.valueOf(credits - debits);
	}
	
	public BigDecimal deposit(BankAccount bankAccount, BigDecimal amount) {
    double balance = bankAccount.getBalance() + amount.doubleValue();
    bankAccount.setBalance(balance);
    return recordTransaction(bankAccount, amount, AccountType.CREDIT);
	}
	
  public BigDecimal withdrawal(BankAccount bankAccount, BigDecimal amount)
  {
    double balance = bankAccount.getBalance() - amount.doubleValue();
    bankAccount.setBalance(balance);
    return recordTransaction(bankAccount, amount, AccountType.DEBIT);
	}
	
	public boolean transfer(BankAccount fromBankAccount, BankAccount toBankAccount, BigDecimal amount) {
		
		try {
      BigDecimal withdrawalFrom = withdrawal(fromBankAccount, amount);
      deposit(toBankAccount, withdrawalFrom);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public String statment(BankAccount bankAccount) {
		return bankAccount.print(Optional.empty());
	}
	
  public String statment(BankAccount bankAccount, Filter filter)
  {
		List<Predicate<AccountRecord>> filters = Arrays.asList(filter).stream().map(f -> f.predicate()).collect(Collectors.toList());
		Predicate<AccountRecord> mergeFilters = filters.stream().reduce(Predicate::and).orElse(x->true);
		List<AccountRecord> collect = bankAccount.getRecords().stream().filter(mergeFilters).collect(Collectors.toList());
		return bankAccount.print(Optional.of(collect));
	}
	
	private BigDecimal recordTransaction(BankAccount bankAccount,
			BigDecimal amount, AccountType accountRecordType) {
		if(bankAccount == null) {
      throw new BankAccountException(
          "Technical problem: Bank account not initialized. Please contact bank directly");
		}
		
		AccountRecord accountRecord = new AccountRecordBuilder()
			.createBuilder()
        .id(String.valueOf(bankAccount.getRecords().size() + 1))
        .amount(amount)
        .accountType(accountRecordType)
        .date(new Date()).build();
		
		bankAccount.getRecords().add(accountRecord);
		return computeBalance(bankAccount);
	}
}
