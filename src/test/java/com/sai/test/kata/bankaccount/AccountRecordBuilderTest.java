package com.sai.test.kata.bankaccount;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.sai.bankaccount.admin.AccountRecord;
import com.sai.bankaccount.admin.AccountRecordBuilder;
import com.sai.bankaccount.admin.AccountType;


/**
 * 
 * @since 30 oct 2017
 * @author sai
 */
public class AccountRecordBuilderTest {

	private AccountRecord accountRecord;

	@Before
	public void setUp() {
		
		accountRecord = new AccountRecordBuilder()
			.createBuilder()
        .id(("1234"))
        .amount(BigDecimal.valueOf(1500))
        .accountType(AccountType.DEBIT).build();
	}
	
	@Test
	public void test() {
    System.out.println("Id: " + accountRecord.getAccountRecordId() + " Amount: "
        + accountRecord.getAmount() + " RecordType: " + accountRecord.getAccountType());
	}
}
