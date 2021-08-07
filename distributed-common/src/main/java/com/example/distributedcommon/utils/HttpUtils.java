package com.example.distributedcommon.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/8/5 13:58
 * description:
 */
@Slf4j
public class HttpUtils {

    private static volatile HttpUtils INSTANCE = new HttpUtils();

    private HttpUtils() {

    }

    private static synchronized void syncInit() {
        if (INSTANCE == null) {
            INSTANCE = new HttpUtils();
        }
    }

    public static HttpUtils getInstance() {
        if (INSTANCE == null) {
            syncInit();
        }
        return INSTANCE;
    }

    public Optional<String> doGet(String url,  Map<String, Object> paramMap) {
        return doGet(url, Collections.emptyMap(), paramMap);
    }

    public Optional<String> doPostForm(String url,  Map<String, Object> paramMap) {
        return doPostForm(url, Collections.emptyMap(), paramMap);
    }

    public Optional<String> doPostJSON(String url,  Map<String, Object> paramMap) {
        return doPostJSON(url, Collections.emptyMap(), paramMap);
    }

    public Optional<String> doGet(String url, Map<String, String> headerMap, Map<String, Object> paramMap) {
        showHttpInfo(url, headerMap, paramMap);
        Optional<String> result = Optional.empty();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder(url);
            builder.setParameters( createNameValuePair(paramMap));
            HttpGet httpGet = new HttpGet(builder.build());
            httpGet.setHeaders(createHeaders(headerMap));
            result = executeHttp(url, headerMap, paramMap,  httpClient, httpGet);
        } catch (IOException | URISyntaxException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", url, headerMap, paramMap, e);
        }
        return result;
    }

    public Optional<String> doPostForm(String url, Map<String, String> headerMap, Map<String, Object> paramMap) {
        showHttpInfo(url, headerMap, paramMap);
        Optional<String> result = Optional.empty();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeaders(createHeaders(headerMap));
            httpPost.setEntity(new UrlEncodedFormEntity(createNameValuePair(paramMap), StandardCharsets.UTF_8));
            result = executeHttp(url, headerMap, paramMap,  httpClient, httpPost);
        } catch (IOException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", url, headerMap, paramMap, e);
        }
        return result;
    }

    public Optional<String> doPostJSON(String url, Map<String, String> headerMap, Map<String, Object> paramMap) {
        showHttpInfo(url, headerMap, paramMap);
        Optional<String> result = Optional.empty();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeaders(createHeaders(headerMap));
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString(paramMap), ContentType.APPLICATION_JSON));
            result = executeHttp(url, headerMap, paramMap,  httpClient, httpPost);
        } catch (IOException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", url, headerMap, paramMap, e);
        }
        return result;
    }

    public void doGetDown(String url, Map<String, String> headerMap, Map<String, Object> paramMap, HttpResponse response) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder(url);
            builder.setParameters( createNameValuePair(paramMap));
            HttpGet httpGet = new HttpGet(builder.build());
            String responseBody = httpClient.execute(httpGet, httpResponse -> {
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status < 200 || status >= 300) {
                    log.error("请求失败，URL：{}, Headers：{},Params：{}, ", url, headerMap, paramMap);
                }
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    EntityUtils.updateEntity(response, entity);
                }
                return "success";
            });
            log.info(responseBody);
        } catch (IOException | URISyntaxException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", url, headerMap, paramMap, e);
        }
    }

    private Optional<String> executeHttp(String url, Map<String, String> headerMap, Map<String, Object> paramMap,  CloseableHttpClient httpClient, HttpRequestBase httpRequestBase) throws IOException {
        String responseBody = httpClient.execute(httpRequestBase, httpResponse -> {
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status < 200 || status >= 300) {
                log.error("请求失败，URL：{}, Headers：{},Params：{}, ", url, headerMap, paramMap);
            }
            HttpEntity entity = httpResponse.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        });
        return Optional.ofNullable(responseBody);
    }

    private BasicHeader[] createHeaders(Map<String, String> headers) {
        return headers.entrySet().parallelStream()
                .map(entry -> new BasicHeader(entry.getKey(), entry.getValue()))
                .toArray(BasicHeader[]::new);
    }

    private List<NameValuePair> createNameValuePair(Map<String, Object> params) {
        return params.entrySet().parallelStream()
                .map(entry -> new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())))
                .collect(Collectors.toList());
    }

    private void showHttpInfo(String url, Map<String, String> headerMap, Map<String, Object> paramMap) {
        log.info("请求信息，URL：{}, Headers：{},Params：{}, ", url, headerMap, paramMap);
    }

    public static void main(String[] args) {
        HttpUtils httpUtils = HttpUtils.getInstance();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", "tests");
        paramMap.put("age", "111");
        String s1 = httpUtils.doGet("http://localhost:8898/student/testGet", Collections.emptyMap(), paramMap).orElse("");
        String s2 = httpUtils.doPostForm("http://localhost:8898/student/testPostFor", Collections.emptyMap(), paramMap).orElse("");
        String s3 = httpUtils.doPostJSON("http://localhost:8898/student/testPostJSON", Collections.emptyMap(), paramMap).orElse("");
        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);
        System.out.println("s3 = " + s3);
    }
}
