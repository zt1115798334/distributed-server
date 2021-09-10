package com.example.distributedcommon.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Optional<String> doGet(String uri, Map<String, String> headerMap, Map<String, Object> paramMap) {
        showHttpInfo(uri, headerMap, paramMap);
        Optional<String> result = Optional.empty();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder(uri);
            builder.setParameters(createNameValuePair(paramMap));
            HttpGet httpGet = new HttpGet(builder.build());
            httpGet.setHeaders(createHeaders(headerMap));
            result = executeHttp(uri, headerMap, paramMap, httpClient, httpGet);
        } catch (IOException | URISyntaxException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", uri, headerMap, paramMap, e);
        }
        return result;
    }

    public Optional<String> doPostForm(String uri, Map<String, String> headerMap, Map<String, Object> paramMap) {
        showHttpInfo(uri, headerMap, paramMap);
        Optional<String> result = Optional.empty();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeaders(createHeaders(headerMap));
            httpPost.setEntity(new UrlEncodedFormEntity(createNameValuePair(paramMap), StandardCharsets.UTF_8));
            result = executeHttp(uri, headerMap, paramMap, httpClient, httpPost);
        } catch (IOException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", uri, headerMap, paramMap, e);
        }
        return result;
    }

    public Optional<String> doPostJSON(String uri, Map<String, String> headerMap, Map<String, Object> paramMap) {
        showHttpInfo(uri, headerMap, paramMap);
        Optional<String> result = Optional.empty();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeaders(createHeaders(headerMap));
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString(paramMap), ContentType.APPLICATION_JSON));
            result = executeHttp(uri, headerMap, paramMap, httpClient, httpPost);
        } catch (IOException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", uri, headerMap, paramMap, e);
        }
        return result;
    }

    public Optional<String> doPostText(String uri, Map<String, String> headerMap, String paramText) {
        showHttpInfo(uri, headerMap, paramText);
        Optional<String> result = Optional.empty();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeaders(createHeaders(headerMap));
            httpPost.setEntity(new StringEntity(paramText, ContentType.DEFAULT_TEXT));
            result = executeHttp(uri, headerMap, paramText, httpClient, httpPost);

        } catch (IOException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", uri, headerMap, paramText, e);
        }
        return result;
    }

    public Optional<String> doPostFile(String uri, Map<String, String> headerMap, List<File> files) {
        String paramText = "files size is: " + files.size();
        showHttpInfo(uri, headerMap, paramText);
        Optional<String> result = Optional.empty();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeaders(createHeaders(headerMap));
            httpPost.setEntity(createHttpEntityOfFile(files));
            result = executeHttp(uri, headerMap, paramText, httpClient, httpPost);
        } catch (IOException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", uri, headerMap, paramText, e);
        }
        return result;
    }

    public Optional<String> doPostFile(String uri, Map<String, String> headerMap, Map<String, Object> paramMap, List<File> files) {
        String paramText = "files size is: " + files.size();
        showHttpInfo(uri, headerMap, paramMap);
        showHttpInfo(uri, headerMap, paramText);
        Optional<String> result = Optional.empty();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeaders(createHeaders(headerMap));
            httpPost.setEntity(createHttpEntityOfParamFile(paramMap, files));
            result = executeHttp(uri, headerMap, paramText, httpClient, httpPost);
        } catch (IOException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", uri, headerMap, paramText, e);
        }
        return result;
    }

    public Optional<String> doPostMultipartFile(String uri, Map<String, String> headerMap, List<MultipartFile> multipartFiles) {
        String paramText = "multipartFiles size is: " + multipartFiles.size();
        showHttpInfo(uri, headerMap, paramText);
        Optional<String> result = Optional.empty();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeaders(createHeaders(headerMap));
            httpPost.setEntity(createHttpEntityOfMultipartFile(multipartFiles));
            result = executeHttp(uri, headerMap, paramText, httpClient, httpPost);
        } catch (IOException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", uri, headerMap, paramText, e);
        }
        return result;
    }

    public Optional<String> doPostMultipartFile(String uri, Map<String, String> headerMap, Map<String, Object> paramMap, List<MultipartFile> multipartFiles) {
        String paramText = "multipartFiles size is: " + multipartFiles.size();
        showHttpInfo(uri, headerMap, paramMap);
        showHttpInfo(uri, headerMap, paramText);
        Optional<String> result = Optional.empty();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeaders(createHeaders(headerMap));
            httpPost.setEntity(createHttpEntityOfMultipartFile(multipartFiles));
            result = executeHttp(uri, headerMap, paramText, httpClient, httpPost);
        } catch (IOException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", uri, headerMap, paramText, e);
        }
        return result;
    }

    public Optional<String> doPostHttpRequest(String uri, Map<String, String> headerMap, HttpServletRequest request) {
        String paramText = "";
        showHttpInfo(uri, headerMap, paramText);
        Optional<String> result = Optional.empty();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
            if (multipartResolver.isMultipart(request)) {
                // 转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 取得request中的所有文件名
                List<MultipartFile> multipartFiles = Lists.newArrayList(multiRequest.getFileMap().values());
                httpPost.setEntity(createHttpEntityOfMultipartFile(multipartFiles));
            }
            result = executeHttp(uri, headerMap, paramText, httpClient, httpPost);
        } catch (IOException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", uri, headerMap, paramText, e);
        }
        return result;
    }

    public void doGetDown(String uri, Map<String, String> headerMap, Map<String, Object> paramMap, HttpResponse response) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder(uri);
            builder.setParameters(createNameValuePair(paramMap));
            HttpGet httpGet = new HttpGet(builder.build());
            String responseBody = httpClient.execute(httpGet, httpResponse -> {
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status < 200 || status >= 300) {
                    log.error("请求失败，URL：{}, Headers：{},Params：{}, ", uri, headerMap, paramMap);
                }
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    EntityUtils.updateEntity(response, entity);
                }
                return "success";
            });
            log.info(responseBody);
        } catch (IOException | URISyntaxException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", uri, headerMap, paramMap, e);
        }
    }

    public void doGetDownPath(String uri, Map<String, String> headerMap, Map<String, Object> paramMap, Path path) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder(uri);
            builder.setParameters(createNameValuePair(paramMap));
            HttpGet httpGet = new HttpGet(builder.build());
            String responseBody = httpClient.execute(httpGet, httpResponse -> {
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status < 200 || status >= 300) {
                    log.error("请求失败，URL：{}, Headers：{},Params：{}, ", uri, headerMap, paramMap);
                }
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    InputStream inputStream = entity.getContent();
                    Files.copy(inputStream, path);
                }
                return "success";
            });
            log.info(responseBody);

        } catch (IOException | URISyntaxException e) {
            log.error("请求异常，URL：{}, Headers：{},Params：{}, Exception: {}", uri, headerMap, paramMap, e);
        }
    }

    private Optional<String> executeHttp(String uri, Map<String, String> headerMap, Map<String, Object> paramMap, CloseableHttpClient httpClient, HttpRequestBase httpRequestBase) throws IOException {
        String responseBody = httpClient.execute(httpRequestBase, httpResponse -> {
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status < 200 || status >= 300) {
                log.error("请求失败，URL：{}, Headers：{},Params：{}, ", uri, headerMap, paramMap);
            }
            HttpEntity entity = httpResponse.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        });
        return Optional.ofNullable(responseBody);
    }

    private Optional<String> executeHttp(String uri, Map<String, String> headerMap, String paramText, CloseableHttpClient httpClient, HttpRequestBase httpRequestBase) throws IOException {
        String responseBody = httpClient.execute(httpRequestBase, httpResponse -> {
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status < 200 || status >= 300) {
                log.error("请求失败，URL：{}, Headers：{},Params：{}, ", uri, headerMap, paramText);
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

    private HttpEntity createHttpEntityOfFile(List<File> files) throws FileNotFoundException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
        for (File file : files) {
            builder.addBinaryBody("file", new FileInputStream(file), ContentType.APPLICATION_OCTET_STREAM, file.getName());
        }
        return builder.build();
    }

    private HttpEntity createHttpEntityOfParamFile(Map<String, Object> paramMap, List<File> files) throws FileNotFoundException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            builder.addTextBody(entry.getKey(), entry.getValue().toString(), ContentType.TEXT_PLAIN.withCharset(StandardCharsets.UTF_8));
        }
        for (File file : files) {
            builder.addBinaryBody("file", new FileInputStream(file), ContentType.APPLICATION_OCTET_STREAM, file.getName());
        }
        return builder.build();

    }


    private HttpEntity createHttpEntityOfMultipartFile(List<MultipartFile> multipartFiles) throws IOException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
        for (MultipartFile multipartFile : multipartFiles) {
            builder.addBinaryBody("file", multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, multipartFile.getOriginalFilename());
        }
        return builder.build();
    }

    private HttpEntity createHttpEntityOfMultipartFile(Map<String, Object> paramMap, List<MultipartFile> multipartFiles) throws IOException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            builder.addTextBody(entry.getKey(), entry.getValue().toString(), ContentType.TEXT_PLAIN.withCharset(StandardCharsets.UTF_8));
        }
        for (MultipartFile multipartFile : multipartFiles) {
            builder.addBinaryBody("file", multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, multipartFile.getOriginalFilename());
        }
        return builder.build();
    }

    private void showHttpInfo(String uri, Map<String, String> headerMap, Map<String, Object> paramMap) {
        log.debug("请求信息，URL：{}, Headers：{},Params：{}, ", uri, headerMap, paramMap);
    }

    private void showHttpInfo(String uri, Map<String, String> headerMap, String paramText) {
        log.debug("请求信息，URL：{}, Headers：{},Params：{}, ", uri, headerMap, paramText);
    }

}
