package com.lojzes.oauth.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OAuth2LoginController {

  @Autowired private OAuth2AuthorizedClientService authorizedClientService;

  @RequestMapping("/userinfo")
  public String userinfo(OAuth2AuthenticationToken authentication, Model model) {
    // authentication.getAuthorizedClientRegistrationId() returns the
    // registrationId of the Client that was authorized during the oauth2Login() flow
    OAuth2AuthorizedClient authorizedClient =
        this.authorizedClientService.loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(), authentication.getName());

    OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

    model.addAttribute("accessToken", accessToken.getTokenValue());
    model.addAttribute("tokenType", accessToken.getTokenType().getValue());

    String principal = authorizedClient.getPrincipalName();
    model.addAttribute("principal", principal);

    return "user-info";
  }
}
