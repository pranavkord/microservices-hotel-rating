package com.psldemo.hotel.services;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.psldemo.hotel.entites.ChargeRequest;
import com.psldemo.hotel.payload.StripeTokenResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
public class StripeService {

  @Value("${stripe.secret_key}")
  private String secretKey;

  @PostConstruct
  public void init() {
    Stripe.apiKey = secretKey;
  }

  public String getToken(String cardNumber, String expMonth, String expYear, String cvc) {
    final String uri = "https://api.stripe.com/v1/tokens";

    HttpHeaders headers = new HttpHeaders();

    // Basic authorization
    String plainCreds = secretKey;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);
    headers.add("Authorization", "Basic " + base64Creds);

    // sending x-www-form-url-encoded body
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("card[number]", cardNumber);
    map.add("card[exp_month]", expMonth);
    map.add("card[exp_year]", expYear);
    map.add("card[cvc]", cvc);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

    // Making API call to stripe for token
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<StripeTokenResponse> response =
        restTemplate.postForEntity(uri, request, StripeTokenResponse.class);

    return response.getBody().getId();
  }

  public Charge charge(ChargeRequest chargeRequest, String token) throws StripeException {
    Map<String, Object> chargeParams = new HashMap<>();
    chargeParams.put("amount", chargeRequest.getAmount());
    chargeParams.put("currency", chargeRequest.getCurrency());
    chargeParams.put("description", chargeRequest.getDescription());
    chargeParams.put("source", token);
    return Charge.create(chargeParams);
  }
}
