package com.sai.bankaccount.filter;

import java.util.function.Predicate;

import com.sai.bankaccount.admin.AccountRecord;

/**
 * 
 * @since 30 oct 2017
 * @author sai
 */
public class Filter {

	protected Predicate<AccountRecord> predicate;

	public Predicate<AccountRecord> predicate() {
		return predicate;
	}
}
