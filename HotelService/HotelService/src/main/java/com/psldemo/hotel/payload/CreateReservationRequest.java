package com.psldemo.hotel.payload;

import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class CreateReservationRequest {
  @Size(max = 50, message = "Size is exceeded")
  @NotNull(message = "Please choose room")
  private String hotelRoom;

  @NotNull(message = "Please enter username")
  private String userId;

  @NotNull(message = "Please enter room price")
  private Double price;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
  @NotNull(message = "Please enter start date")
  private Date startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
  @NotNull(message = "Please enter end date")
  private Date endDate;

  @NotNull(message = "Please enter number of adult")
  private Integer numOfAdult;

  @NotNull(message = "Please enter number of children")
  private Integer numOfChildren;

  @Size(max = 50, message = "Size is exceeded")
  @NotNull(message = "Please enter your full name")
  private String contactNameSurname;

  @Size(min = 10, max = 10, message = "Phone number should be exact 10 characters")
  @NotNull(message = "Please enter your phone number")
  private String contactPhone;

  @Email(message = "Please enter valid email")
  @NotNull(message = "Please enter your email")
  @Size(min = 5, max = 254)
  private String contactEmail;

  @Size(max = 500, message = "Size is exceeded")
  private String note;

  private Boolean approved;
  
  public enum Currency {
    EUR, USD, INR;
  }

  @Size(max = 200, message = "Size is exceeded")
  private String description;
  
  private int amount;
  
  private Currency currency;
  
  @Email
  private String stripeEmail;
  
  private String stripeToken;
  
  private String cardNumber;
  
  @Size(min=2, max=2)
  private String expMonth;
  
  @Size(min=4, max=4)
  private String expYear;
  
  @Size(min=3, max=3)
  private String cvc;
}
