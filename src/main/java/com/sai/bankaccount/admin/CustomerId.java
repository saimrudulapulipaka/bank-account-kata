package com.sai.bankaccount.admin;

/**
 * 
 * @since 30 oct 2017
 * @author sai
 */
public class CustomerId {
	
  private Integer id;

  public CustomerId(Integer id)
  {
		super();
    this.id = id;
	}

	@Override
	public String toString() {
    return id.toString();
	}
}
