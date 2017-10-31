package com.sai.test.kata.bankaccount;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.sai.bankaccount.BankAccountService;
import com.sai.bankaccount.admin.AccountRecord;
import com.sai.bankaccount.admin.AccountType;
import com.sai.bankaccount.admin.BankAccount;
import com.sai.bankaccount.admin.BankAccountBuilder;
import com.sai.bankaccount.admin.BankAccountId;
import com.sai.bankaccount.admin.CustomerId;
import com.sai.bankaccount.filter.DateFilter;
import com.sai.bankaccount.filter.Filter;

/**
 * 
 * @since 30 oct 2017
 * @author sai
 */
public class BankAccountServiceTest {

  private static final Object EMPTY_STATMENT = "" + "BankAccountId:FR001\n" + "CustomerId:1\n\n"
      + "+-----------+------------+-------------+--------------+\n"
      + "|    ID     |    Date    |  OPERATION  |    Amount    |\n"
      + "+-----------+------------+-------------+--------------+\n"
      + "+-----------+------------+-------------+--------------+";

  private String expectedStatement = "" + "BankAccountId:FR001\n" + "CustomerId:1\n\n"
      + "+-----------+------------+-------------+--------------+\n"
      + "|    ID     |    Date    |  OPERATION  |    Amount    |\n"
      + "+-----------+------------+-------------+--------------+\n"
      + "|      1    | %s | CREDIT      | 0001500,0000 |\n"
      + "|      2    | %s | CREDIT      | 0003000,0000 |\n"
      + "|      3    | %s | DEBIT       | 0001500,0000 |\n"
      + "|      4    | %s | CREDIT      | 0002000,0000 |\n"
      + "|      5    | %s | DEBIT       | 0001000,0000 |\n"
      + "+-----------+------------+-------------+--------------+";

  private String expectedStatementfilterDate = "" + "BankAccountId:FR001\n" + "CustomerId:1\n\n"
      + "+-----------+------------+-------------+--------------+\n"
      + "|    ID     |    Date    |  OPERATION  |    Amount    |\n"
      + "+-----------+------------+-------------+--------------+\n"
      + "|      1    | %s | CREDIT      | 0001500,0000 |\n"
      + "|      2    | %s | CREDIT      | 0001500,0000 |\n"
      + "|      3    | %s | CREDIT      | 0003000,0000 |\n"
      + "|      4    | %s | DEBIT       | 0001500,0000 |\n"
      + "|      5    | %s | DEBIT       | 0001500,0000 |\n"
      + "+-----------+------------+-------------+--------------+";

  private BankAccountService bankAccountService;

  private BankAccount myAccount1;

  private BankAccount myAccount2;

  private BankAccount anotherAccount;

  @Before
  public void setUp()
  {

    bankAccountService = new BankAccountService();

    myAccount2 = new BankAccountBuilder().createBuilder().id(new BankAccountId("FR001"))
        .customer(new CustomerId(1)).build();

    myAccount1 = new BankAccountBuilder().createBuilder().id(new BankAccountId("FR001"))
        .customer(new CustomerId(1)).record(new AccountRecord("1", BigDecimal.valueOf(0.0),
            AccountType.CREDIT, addDayToDate(new Date(), -5)))
        .build();

    myAccount1 = new BankAccountBuilder().createBuilder().id(new BankAccountId("FR001"))
        .customer(new CustomerId(1)).record(new AccountRecord("1", BigDecimal.valueOf(1500.0),
            AccountType.CREDIT, addDayToDate(new Date(), 2)))
        .build();

    anotherAccount = new BankAccountBuilder().createBuilder().id(new BankAccountId("FR002"))
        .customer(new CustomerId(2)).build();

    String today = AccountRecord.DD_MM_YYYY.format(new Date());
    Date todayDate = new Date();

    Date after2daysDate = addDayToDate(todayDate, 2);
    String after2days = AccountRecord.DD_MM_YYYY.format(after2daysDate);

    expectedStatement = String.format(expectedStatement, today, today, today, today, today);
    expectedStatementfilterDate =
        String.format(expectedStatementfilterDate, after2days, today, today, today, today);
  }

  @Test
  public void depositTest()
  {

    BigDecimal amount1 = BigDecimal.valueOf(3000);
    BigDecimal amount2 = BigDecimal.valueOf(1000);

    myAccount1.setBalance(Double.valueOf(1500));

    System.out
        .println(new Date() + " AVAILABLE ACCOUNT BALANCE : " + myAccount1.getBalance() + " \n ");

    System.out.println("Operation : Deposit \n");

    bankAccountService.deposit(myAccount1, amount1);
    System.out.println((assertThat(myAccount1.getBalance()).isPositive()) != null
        ? new Date() + " CREDIT : " + amount1 : "");

    bankAccountService.deposit(myAccount1, amount2);
    System.out.println(
        (assertThat(myAccount1.getBalance()).isPositive()) != null
            ? new Date() + " CREDIT : " + amount2 : "");

    System.out.println((assertThat(myAccount1.getBalance()).isPositive()) != null
        ? new Date() + " REMAINING ACCOUNT BALANCE : " + myAccount1.getBalance() + " \n "
        : "" + "\n");
  }

  @Test
  public void withdrawalTest()
  {
    myAccount1.setBalance(Double.valueOf(4000));

    BigDecimal amount1 = BigDecimal.valueOf(1500);
    BigDecimal amount2 = BigDecimal.valueOf(1000);

    System.out
        .println(new Date() + " AVAILABLE ACCOUNT BALANCE : " + myAccount1.getBalance() + " \n ");

    System.out.println("Operation : Withdrawal \n");

    bankAccountService.withdrawal(myAccount1, amount1);
    System.out.println(
        (assertThat(myAccount1.getBalance()).isPositive()) != null
            ? new Date() + " DEBIT : " + amount1
            : "" + "\n");

    bankAccountService.withdrawal(myAccount1, amount2);
    System.out.println(
        (assertThat(myAccount1.getBalance()).isPositive()) != null
            ? new Date() + " DEBIT : " + amount2 : "");

    System.out.println((assertThat(myAccount1.getBalance()).isPositive()) != null
        ? new Date() + " REMAINING ACCOUNT BALANCE : " + myAccount1.getBalance() + " \n "
        : "" + " \n ");
  }

  @Test
  public void containPositiveBalanceDuringFirstDeposit()
  {
    BigDecimal balance = bankAccountService.deposit(myAccount1, BigDecimal.valueOf(50));
    assertThat(balance).isPositive();
  }

  @Test
  public void containNegativeBalanceDuringFirstWithdraw()
  {
    BigDecimal balance = bankAccountService.withdrawal(anotherAccount, BigDecimal.valueOf(50));
    assertThat(balance).isNegative();
    assertThat(balance).isEqualTo(BigDecimal.valueOf(-50.0));
  }

  @Test
  public void printStatmentTest() throws ParseException
  {
    generateMyAccountTransactions();
    System.out.println("Balance : " + myAccount1.getBalance());
    String statment = bankAccountService.statment(myAccount2);
    System.out.println("\nBelow is the History of operations : (today)");
    System.out.println(statment);
    assertThat(statment).isEqualTo(expectedStatement);
  }

  @Test
  public void emptyStatmentNoOperationInDateSlot() throws ParseException
  {
    generateMyAccountTransactions();

    Filter dateFilter = new DateFilter(AccountRecord.DD_MM_YYYY.parse("10/10/2017"),
        AccountRecord.DD_MM_YYYY.parse("15/10/2017"));

    String statment = bankAccountService.statment(myAccount1, dateFilter);
    System.out.println("\nBelow is the History of operations :");
    System.out.println("When no operations performed during the date slots \n");
    System.out.println(statment);
    assertThat(statment).isEqualTo(EMPTY_STATMENT);
  }

  @Test
  public void printStatmentByFilteringDate() throws ParseException
  {
    Date today = new Date();
    Date before5days = addDayToDate(today, -5);
    Date after5days = addDayToDate(today, 5);

    Filter dateFilter = new DateFilter(before5days, after5days);

    bankAccountService.deposit(myAccount1, BigDecimal.valueOf(1500));
    bankAccountService.deposit(myAccount1, BigDecimal.valueOf(3000));
    bankAccountService.withdrawal(myAccount1, BigDecimal.valueOf(1500));
    bankAccountService.withdrawal(myAccount1, BigDecimal.valueOf(1500));
    String statment = bankAccountService.statment(myAccount1, dateFilter);
    System.out
        .println("\n\nBelow is the History of operations filtered by date :");
    System.out.println(statment);
    assertThat(statment).isEqualTo(expectedStatementfilterDate);
  }

  private void generateMyAccountTransactions()
  {
    bankAccountService.deposit(myAccount2, BigDecimal.valueOf(1500));
    bankAccountService.deposit(myAccount2, BigDecimal.valueOf(3000));
    bankAccountService.withdrawal(myAccount2, BigDecimal.valueOf(1500));
    bankAccountService.deposit(myAccount2, BigDecimal.valueOf(2000));
    bankAccountService.withdrawal(myAccount2, BigDecimal.valueOf(1000));
  }

  private Date addDayToDate(Date date, int nbDays)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DATE, nbDays);
    return cal.getTime();
  }
}