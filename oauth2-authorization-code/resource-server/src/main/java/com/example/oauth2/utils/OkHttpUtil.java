package com.example.oauth2.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author songbo
 * @version 1.0
 * @date 2022/11/20 15:06
 */
public class OkHttpUtil {

    private static OkHttpClient okHttpClient;

    public static final String MEDIA_TYPE_NONE = "none";
    public static final String MEDIA_TYPE_JSON = "application/json";
    public static final String MEDIA_TYPE_FORM = "form-data";
    public static final String MEDIA_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";

    private OkHttpUtil() {
    }

    public static OkHttpClient getClient() {
        if (okHttpClient == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    public static MediaType getJsonMediaType() {
        return MediaType.parse("application/json");
    }

    public static RequestBody buildRequestBody(String mediaType, Map<String, String> formParam) throws JsonProcessingException {
        switch (mediaType) {
            case MEDIA_TYPE_JSON:
                if (MapUtils.isNotEmpty(formParam)) {
                    RequestBody.create("", null);
                }
                String content = JSONObject.toJSONString(formParam);
                return RequestBody.create(content, getJsonMediaType());

            case MEDIA_TYPE_FORM:
                MultipartBody.Builder formBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                if (MapUtils.isNotEmpty(formParam)) {
                    formParam.entrySet().stream().forEach(entry -> formBuilder.addFormDataPart(entry.getKey(), entry.getValue()));
                }
                return formBuilder.build();

            case MEDIA_TYPE_FORM_URLENCODED:
                FormBody.Builder builder = new FormBody.Builder();
                if (MapUtils.isNotEmpty(formParam)) {
                    formParam.entrySet().stream().forEach(entry -> builder.add(entry.getKey(), entry.getValue()));
                }
                return builder.build();

            case MEDIA_TYPE_NONE:
                return RequestBody.create("", null);

            default:
                throw new IllegalArgumentException("Unsupported mediaType:" + mediaType);
        }
    }

}
