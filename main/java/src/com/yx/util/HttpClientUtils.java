package com.yx.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HttpClientUtils 工具类
 * 基于 org.apache.httpcomponents 包 HttpClient
 *
 * Created by join on 2018/9/14.
 */
public class HttpClientUtils {
    //Http返回码 200
    private static final int RESPONSE_STATUS_OK = 200;

    //设置连接超时时间，单位毫秒。
    private static final int CONNECT_TIME_OUT = 5000;

    //设置从connect Manager获取Connection 超时时间，单位毫秒。
    private static final int CONNECT_REQUEST_TIME_OUT = 5000;

    //请求获取数据的超时时间，单位毫秒。
    private static final int SOCKET_TIME_OUT = 5000;

    /**
     * GET请求
     *
     * @param scheme
     * @param host
     * @param path
     * @param values
     * @param connectTimeout 连接超时时间
     * @return
     * @throws RuntimeException
     */
    public static String get(final String scheme, final String host, final String path,
                             final Map<String, String> values, final int connectTimeout) throws RuntimeException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme)
                    .setHost(host)
                    .setPath(path);
            if (values != null) {
                Set<String> keys = values.keySet();
                for (String key : keys) {
                    String val = values.get(key);
                    builder.setParameter(key, val);
                }
            }

            URI uri = builder.build();

            HttpGet httpGet = new HttpGet(uri);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(CONNECT_REQUEST_TIME_OUT)
                    .setSocketTimeout(SOCKET_TIME_OUT).build();
            httpGet.setConfig(requestConfig);

            return execute(httpClient, httpGet);

        } catch (IOException | URISyntaxException ex) {
            String message = String.format("GET请求失败：scheme:%s,host:%s,path:%s", scheme, host, path);
            throw new RuntimeException(message, ex);
        }
    }

    /**
     * GET请求，默认5秒超时
     *
     * @param scheme
     * @param host
     * @param path
     * @param values
     * @return
     * @throws RuntimeException
     */
    public static String get(final String scheme, final String host, final String path,
                             final Map<String, String> values) throws RuntimeException {

        return get(scheme, host, path, values);
    }

    /**
     * get请求
     *
     * @param uri
     * @return
     * @throws RuntimeException
     */
    public static String get(final String uri, final int connectTimeout) throws RuntimeException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(uri);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(CONNECT_REQUEST_TIME_OUT)
                    .setSocketTimeout(SOCKET_TIME_OUT).build();
            httpGet.setConfig(requestConfig);

            return execute(httpClient, httpGet);

        } catch (IOException ex) {
            throw new RuntimeException(String.format("GET请求失败:%s", uri), ex);
        }
    }

    /**
     * GET请求，默认5秒超时
     *
     * @param uri
     * @return
     * @throws RuntimeException
     */
    public static String get(final String uri) throws RuntimeException {
        return get(uri, CONNECT_TIME_OUT);
    }

    /**
     * post请求(默认utf-8编码)
     *
     * @param uri
     * @param values
     * @return
     * @throws Exception
     */
    public static String post(final String uri, final Map<String, String> values) throws Exception {
        return post(uri, values, "utf-8");
    }

    /**
     * post请求
     *
     * @param uri
     * @param values
     * @param charset
     * @return
     * @throws RuntimeException
     */
    public static String post(final String uri, final Map<String, String> values, final String charset)
            throws RuntimeException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);

            List<NameValuePair> nameValuePairs = new ArrayList<>();
            if (values != null) {
                Set<String> keys = values.keySet();
                keys.forEach((key) -> nameValuePairs.add(new BasicNameValuePair(key, values.get(key))));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, charset));

            return execute(httpClient, httpPost);

        } catch (IOException ex) {
            throw new RuntimeException(String.format("POST请求失败:%s", uri), ex);
        }
    }

    /**
     * 通用请求处理方法
     *
     * @param httpClient
     * @param httpReuqest
     * @return
     * @throws IOException
     */
    private static String execute(final CloseableHttpClient httpClient,
                                  final HttpUriRequest httpReuqest, final String charset)
            throws IOException, RuntimeException {
        try (CloseableHttpResponse response = httpClient.execute(httpReuqest)) {
            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() != RESPONSE_STATUS_OK) {
                throw new RuntimeException("远程接口返回状态非200");
            }

            //获取响应实体
            HttpEntity entity = response.getEntity();

            if (entity == null) {
                throw new RuntimeException("远程接口没有相应");
            }

            return EntityUtils.toString(entity, charset);
        }
    }

    /**
     * 通用请求处理方法，默认执行utf-8解码
     *
     * @param httpClient
     * @param httpReuqest
     * @return
     * @throws IOException
     * @throws RuntimeException
     */
    private static String execute(final CloseableHttpClient httpClient, final HttpUriRequest httpReuqest)
            throws IOException, RuntimeException {
        return execute(httpClient, httpReuqest, "utf-8");
    }
}
