package com.psldemo.hotel.entites;

import lombok.Data;

@Data
public class ChargeRequest {
  public enum Currency {
    EUR, USD, INR;
  }

  private String description;
  private int amount;
  private Currency currency;
  private String stripeEmail;
  private String stripeToken;
  
  
  private String cardNumber;
  private String expMonth;
  private String expYear;
  private String cvc;
}
