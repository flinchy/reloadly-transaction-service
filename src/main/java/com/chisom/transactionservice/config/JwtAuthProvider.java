package com.chisom.transactionservice.config;

import com.chisom.transactionservice.config.model.UsernamePasswordAuthenticationTokenImpl;
import com.chisom.transactionservice.dto.response.ValidateTokenResponse;
import com.chisom.transactionservice.model.UserDetailsImpl;
import com.chisom.transactionservice.utils.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConfigUtils configUtils;

    private final RestTemplate restTemplate;

    @Autowired
    public JwtAuthProvider(ConfigUtils configUtils, RestTemplate restTemplate
    ) {
        this.configUtils = configUtils;
        this.restTemplate = restTemplate;
    }

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails,
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
    ) {
        // NO-OPERATIONS required
    }

    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken authToken) {
        try {

            UsernamePasswordAuthenticationTokenImpl token = (UsernamePasswordAuthenticationTokenImpl) authToken;
            //verify from auth server
            ValidateTokenResponse validateTokenResponse = validateUserFromAuthServer(token);

            List<GrantedAuthority> grantedAuthorities;

            if (validateTokenResponse != null) {

                grantedAuthorities = validateTokenResponse.getAuthorities()
                        .stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                return new UserDetailsImpl(validateTokenResponse.getUser_name(), grantedAuthorities);
            }

            throw new RuntimeException("handle this exception later");

        } catch (Exception ex) {
            log.error("caught an exception ",ex);
            throw new RuntimeException("Invalid AccessToken", ex);
        }
    }

    /**
     * validate token from the authorization server.
     *
     * @param token token
     * @return ValidateTokenResponse
     */
    private ValidateTokenResponse validateUserFromAuthServer(
            UsernamePasswordAuthenticationTokenImpl token
    ) {
        //add basic auth so that the authorization server will recognize this client app (Transaction service)
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION,  configUtils.getBasicAuth());

        HttpEntity<UsernamePasswordAuthenticationTokenImpl> entity = new HttpEntity<>(headers);
        return restTemplate.postForEntity(
                configUtils.getValidateUrl() + getToken(token.getToken()), entity,
                ValidateTokenResponse.class).getBody();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationTokenImpl.class.isAssignableFrom(authentication);
    }

    private String getToken(String token) {

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return "";
    }
}
