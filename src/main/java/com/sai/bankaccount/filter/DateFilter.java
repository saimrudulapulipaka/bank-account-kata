package com.sai.bankaccount.filter;

import java.util.Date;

/**
 * 
 * @since 30 oct 2017
 * @author sai
 */
public class DateFilter extends Filter {

	private Date from;
	private Date to;
	
	public DateFilter(Date from, Date to) {
		super();
		this.setFrom(from);
		this.to = to;
		
		this.predicate = x -> x.getDate().compareTo(from) > 0 
				&& x.getDate().compareTo(to) < 0; 
	}

  public Date getFrom()
  {
    return from;
  }

  public void setFrom(Date newFrom)
  {
    this.from = newFrom;
  }
}
