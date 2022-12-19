package com.example.oauth2.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author songbo
 * @version 1.0
 * @date 2022/11/20 15:06
 */
@Slf4j
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

    /**
     * 构建请求体
     *
     * @param mediaType
     * @param formParam
     * @return
     */
    public static RequestBody buildRequestBody(String mediaType, Map<String, Object> formParam) {
        switch (mediaType) {
            case MEDIA_TYPE_JSON:
                String content = MapUtils.isEmpty(formParam) ? "" : JSONObject.toJSONString(formParam);
                return RequestBody.create(content, MediaType.parse("application/json"));

            case MEDIA_TYPE_FORM:
                MultipartBody.Builder formBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                if (MapUtils.isNotEmpty(formParam)) {
                    formParam.entrySet().stream().forEach(entry -> formBuilder.addFormDataPart(entry.getKey(), entry.getValue().toString()));
                }
                return formBuilder.build();

            case MEDIA_TYPE_FORM_URLENCODED:
                FormBody.Builder builder = new FormBody.Builder();
                if (MapUtils.isNotEmpty(formParam)) {
                    formParam.entrySet().stream().forEach(entry -> builder.add(entry.getKey(), entry.getValue().toString()));
                }
                return builder.build();

            case MEDIA_TYPE_NONE:
                return RequestBody.create("", null);

            default:
                throw new IllegalArgumentException("Unsupported mediaType:" + mediaType);
        }
    }

    /**
     * @param baseUrl 接口地址
     * @param headers 请求头
     * @param params  请求参数
     * @return
     * @throws IOException
     */
    public static String executeGetOkHttpApi(String baseUrl, Map<String, String> headers, Map<String, Object> params) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        if (MapUtils.isNotEmpty(params)) {
            for (String key : params.keySet()) {
                urlBuilder.setQueryParameter(key, params.get(key).toString());
            }
        }
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .headers(headers == null ? new Headers.Builder().build() : Headers.of(headers))
                .get()
                .build();
        Response response = OkHttpUtil.getClient().newCall(request).execute();
        if (response == null) {
            return null;
        }
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return null;
        }
        String bodyString = responseBody.string();
        if (StringUtils.isBlank(bodyString)) {
            return null;
        }
        log.info("executeGetOkHttpApi返回:{}", bodyString);
        return bodyString;
    }

    /**
     * @param baseUrl     接口地址
     * @param headers     请求头
     * @param requestBody 请求体
     * @return
     * @throws IOException
     */
    public static String executePostOkHttpApi(String baseUrl, Map<String, String> headers, RequestBody requestBody) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl)
                .headers(headers == null ? new Headers.Builder().build() : Headers.of(headers))
                .post(requestBody)
                .build();
        Response response = OkHttpUtil.getClient().newCall(request).execute();
        if (response == null) {
            return null;
        }
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return null;
        }
        String bodyString = responseBody.string();
        if (StringUtils.isBlank(bodyString)) {
            return null;
        }
        log.info("executePostOkHttpApi返回:{}", bodyString);
        return bodyString;
    }

}
