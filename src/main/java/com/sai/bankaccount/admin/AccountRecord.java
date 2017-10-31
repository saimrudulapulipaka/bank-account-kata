package com.sai.bankaccount.admin;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @since 30 oct 2017
 * @author sai
 */
public class AccountRecord {

	public static final DateFormat DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy");
	private static final NumberFormat DDDD_DDDD = new DecimalFormat("0000000.0000"); 
	
	private static final String NA = "NA";
	private static final String ROW = "| %s    | %s | %s | %s |\n";
  protected String accountNumber;
	protected BigDecimal amount;
  protected AccountType accountType;
	private Date date;


	public AccountRecord() {
		super();
	}

  public AccountRecord(String accountNumber, BigDecimal amount,
      AccountType accountType, Date date)
  {
		super();
    this.accountNumber = accountNumber;
		this.amount = amount;
    this.accountType = accountType;
		this.date = date;
	}

	public void print(StringBuilder sb) {
    String accountRecordIdStr = accountNumber == null ? NA : accountNumber.toString();
		String dateStr = date == null ? NA : DD_MM_YYYY.format(date);
    String typeStr = accountType == null ? NA : accountType.toString();
		String amountStr = amount == null ? NA : DDDD_DDDD.format(amount);

    sb.append(String.format(ROW, StringUtils.leftPad(accountRecordIdStr, 6, ' '), dateStr,
        StringUtils.rightPad(typeStr, 11, ' '), amountStr));
	}

  public String getAccountRecordId()
  {
    return accountNumber;
	}

  public void setAccountRecordId(String string)
  {
    this.accountNumber = string;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

  public AccountType getAccountType()
  {
    return accountType;
	}

  public void setAccountType(AccountType accountType)
  {
    this.accountType = accountType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
