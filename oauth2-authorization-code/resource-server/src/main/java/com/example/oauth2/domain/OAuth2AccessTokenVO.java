package com.example.oauth2.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author songbo
 * @version 1.0
 * @date 2022/12/13 19:49
 */
@ToString
@Data
public class OAuth2AccessTokenVO implements Serializable {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("expires_in")
    private Integer expiresIn;
    @SerializedName("scope")
    private String scope;
    @SerializedName("error")
    private String error;
    @SerializedName("error_description")
    private String errorDescription;
}
