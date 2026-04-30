package rs.aik.testautomation.AIKTestAutomation.Data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import rs.aik.testautomation.AIKTestAutomation.Wait.WaitHelpers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Class for static methods for call web service methods
 */
public class HTTPManager {
    //region settings
    static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = client = new OkHttpClient.Builder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .build();
    //endregion settings

    //region Base methods
    /**
     * Method that call Get web method. Url creates with base url from framework, method from parameter @methodUrl,
     * and request from parameter @requestBody
     * @param methodUrl Relative path to method
     * @param requestClient Request string
     * @return Map<String, Object> response that caller will parse
     */
    public static Map<String, Object> callGetMethod(String methodUrl, String requestClient) {
        Map<String, Object> result = null;
        String baseUrl = DataManager.webServiceBaseURL;
        String url = baseUrl + methodUrl + requestClient;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        WaitHelpers.waitForSeconds(3);
        try {
            boolean doWork = true;
            int n = 1;
            Response response = null;
            while (doWork) {
                response = client.newCall(request).execute();
                WaitHelpers.waitForSeconds(2);
                if (n > 3)
                    doWork = false;
                n++;
                if ((response.code() != 204) && (response.code() != 404))
                    doWork = false;
            }
            if (response.code() == 200) {
                String responseString = response.body().string();
                Gson gson = new Gson();
                Type mapType = new TypeToken<Map<String, Object>>() {
                }.getType();
                result = gson.fromJson(responseString, mapType);
                Log.info("Sent request to server, got response " + responseString);
                //return result;
            }
        } catch (Exception e) {
            Log.info("ERROR sending request");
        }
        return result;
    }

    /**
     * Method that call Post web method. Url creates with base url from framework, method from parameter @methodUrl,
     * and request from parameter @requestBody
     * @param methodUrl Path to method
     * @param requestBody Request
     * @return Map<String, Object> response that caller will parse
     */
    public static Map<String, Object> callPostMethod(String methodUrl, RequestBody requestBody) {
        Map<String, Object> result = null;
        String baseUrl = DataManager.webServiceBaseURL;
        String url = baseUrl + methodUrl;
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()){
            String responseString = response.body().string();
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            result = gson.fromJson(responseString, mapType);
            Log.info("Sent request to server, got response " + responseString);
        } catch (IOException e) {
            Log.info("ERROR sending request");
        }
        return result;
    }

    /**
     * Method that call Post web method with authorization. Url creates with base url from framework, method from parameter @methodUrl,
     * and request from parameter @requestBody
     * @param methodUrl  Path to method
     * @param requestBody Request
     * @param authorization Base64 authorization
     * @return Map<String, Object> response that caller will parse
     */
    public static Map<String, Object> callPostMethod(String methodUrl, RequestBody requestBody, String authorization) {
        Map<String, Object> result = null;
        String baseUrl = DataManager.webServiceBaseURL;
        //String url = baseUrl + methodUrl;
        String url = methodUrl;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", authorization)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()){
            String responseString = response.body().string();
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            result = gson.fromJson(responseString, mapType);
            Log.info("Sent request to server, got response " + responseString);
        } catch (IOException e) {
            Log.info("ERROR sending request");
        }
        return result;
    }

    public static Map<String, Object> callPostMethod(String methodUrl, RequestBody requestBody, String authorization, String contentType) {
        Map<String, Object> result = null;
        String baseUrl = DataManager.webServiceBaseURL;
        //String url = baseUrl + methodUrl;
        String url = methodUrl;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", authorization)
                .addHeader("Content-Type", contentType)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()){
            String responseString = response.body().string();
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            result = gson.fromJson(responseString, mapType);
            Log.info("Sent request to server, got response " + responseString);
        } catch (IOException e) {
            Log.info("ERROR sending request");
        }
        return result;
    }

    public static List<Map<String, Object>> callPostMethodList(String methodUrl, RequestBody requestBody, String authorization) {
        List<Map<String, Object>> result = null;
        String baseUrl = DataManager.webServiceBaseURL;
        //String url = baseUrl + methodUrl;
        String url = methodUrl;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", authorization)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()){
            String responseString = response.body().string();
            Gson gson = new Gson();
            Type mapType = new TypeToken<List<Map<String, Object>>>() {}.getType();
            result = gson.fromJson(responseString, mapType);
            Log.info("Sent request to server, got response " + responseString);
        } catch (IOException e) {
            Log.info("ERROR sending request");
        }
        return result;
    }

    //public static Map<String, Object> callPostMSimple(String methodUrl, String requestBody, String authorization) {
    //    Map<String, Object> result = null;
    //    String baseUrl = DataManager.webServiceBaseURL;
    //    //String url = baseUrl + methodUrl;
    //    String url = methodUrl;
    //    Request request = new Request.Builder()
    //            .url(url)
    //            .addHeader("Authorization", authorization)
    //            .(requestBody)
    //            .build();
    //}
    //endregion methods
}
