package rs.aik.testautomation.AIKTestAutomation.Action;

import okhttp3.*;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import rs.aik.testautomation.AIKTestAutomation.Data.DataManager;
import rs.aik.testautomation.AIKTestAutomation.Data.HTTPManager;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Utilities;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

public class HTTPAction {
    //region settings
    static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    static final MediaType JSONAttach = MediaType.get("multipart/form-data; charset=utf-8");
    //endregion settings

    //region - Testrail methods -
    public static void addRun(int projectId, int[] lista){
        //String url = DataManager.UserDeatailsApiUrl;
        String auth = Utilities.getBasic64(DataManager.testrailUsername, DataManager.testrailPassword);
        String projId = String.valueOf(projectId);//30
        String url = "index.php?/api/v2/add_run/".concat(projId);
        String listS = "[";
        for(int i : lista){
            String s = String.valueOf(i);
            if(listS.equals("["))
                listS = listS.concat(s);
            else
                listS = listS.concat(", ").concat(s);
        }
        listS = listS.concat("]");
        String requestB = "{\n" +
                "    \"suite_id\": 1,\n" +
                "    \"name\": \"Automation test - Web - " + Utilities.getDDMMYYYYDate(Utilities.now()) + "\",\n" +
                "    \"include_all\": false,\n" +
                "    \"case_ids\": " + listS + "\n" +
                "}";
        RequestBody body = RequestBody.create(JSON, requestB);
        String a = body.toString();

        try{
            url = DataManager.testrailBaseURL.concat(url);
            Utilities.saveStringToFile(requestB, "testdata/addrun.txt");
            Utilities.saveStringToFile("BEGIN", "testdata/addresults.txt");
            DataManager.userApiResponse  = HTTPManager.callPostMethod(url, body, auth);
            //List<Map<String, Object>> products = (List<Map<String, Object>>) DataManager.userApiResponse.get("");
            //Map<String, Object> firstProduct = products.get(0);
            //String runId = firstProduct.get("ID").toString();
            String runId = DataManager.userApiResponse.get("id").toString();
            DataManager.userObject.put("RunID", runId);
            return;
        } catch (Exception e) {
            Log.info("ERROR sending request");
        }

    }

    public static void addResultsForCasesByOne(int[] lista) throws SQLException {
        String auth = Utilities.getBasic64(DataManager.testrailUsername, DataManager.testrailPassword);
        /*
        String runId = DataManager.userObject.get("RunID").toString();
        if(runId.contains(".")){
            runId = runId.substring(0, runId.indexOf("."));
        }
        */
        //String url = "?/api/v2/add_results_for_cases/".concat(runId);
        //url = DataManager.testrailBaseURL.concat(url);
        String oneCase;
        for(int i : lista){
            oneCase = Utilities.getOneCase(i);
            /*
            String listS = "[".concat(oneCase).concat("]");
            String requestB = "{ " +
                    "    \"results\": " + listS +
                    "}";
            RequestBody body = RequestBody.create(JSON, requestB);
            //String a = body.toString();

            try{

                Utilities.addStringToFile(requestB, "testdata/addresults.txt");
                DataManager.userApiResponseList  = HTTPManager.callPostMethodList(url, body, auth);
                //List<Map<String, Object>> products = (List<Map<String, Object>>) DataManager.userApiResponse.get("");
                //Map<String, Object> firstProduct = products.get(0);
                //String resultId = firstProduct.get("ID").toString();
                String resultId = DataManager.userApiResponseList.get(0).get("id").toString();
                DataManager.userObject.put("ResultID", resultId);
                Utilities.addStringToFile("Done! ", "testdata/addresults.txt");
                //return;
            } catch (Exception e) {
                Log.info("ERROR sending request");
                try {
                    Utilities.addStringToFile(e.getMessage(), "testdata/addresults.txt");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            */
        }
    }

    public static void addResultsForCases(int[] lista) throws SQLException {
        String auth = Utilities.getBasic64(DataManager.testrailUsername, DataManager.testrailPassword);
        String runId = DataManager.userObject.get("RunID").toString();
        if(runId.contains(".")){
            runId = runId.substring(0, runId.indexOf("."));
        }
        String url = "?/api/v2/add_results_for_cases/".concat(runId);
        String oneCase;
        String listS = "[";
        for(int i : lista){
            oneCase = Utilities.getOneCase(i);
            if(listS.equals("["))
                listS = listS.concat(oneCase);
            else
                listS = listS.concat(", ").concat(oneCase);
        }
        listS = listS.concat("]");
        String requestB = "{ " +
                "    \"results\": " + listS +
                "}";
        RequestBody body = RequestBody.create(JSON, requestB);
        String a = body.toString();

        try{
            url = DataManager.testrailBaseURL.concat(url);
            Utilities.saveStringToFile(requestB, "testdata/addresults.txt");
            DataManager.userApiResponseList  = HTTPManager.callPostMethodList(url, body, auth);
            //List<Map<String, Object>> products = (List<Map<String, Object>>) DataManager.userApiResponse.get("");
            //Map<String, Object> firstProduct = products.get(0);
            //String resultId = firstProduct.get("ID").toString();
            String resultId = DataManager.userApiResponseList.get(0).get("id").toString();
            DataManager.userObject.put("ResultID", resultId);
            Log.info("End add results");
            return;
        } catch (Exception e) {
            Log.info("ERROR sending request");
        }

    }

    public static void addAttachment(){
        String auth = Utilities.getBasic64(DataManager.testrailUsername, DataManager.testrailPassword);
        String ResultID = DataManager.userObject.get("ResultID").toString();
        if(ResultID.contains(".")){
            ResultID = ResultID.substring(0, ResultID.indexOf("."));
        }
        String url = "index.php?/api/v2/add_attachment_to_result/".concat(ResultID);
        String oneCase;

        String resultsFolder = new File("results").getAbsolutePath().replace("%20", " ");
        String requestB = resultsFolder.concat("\\TestResults.xlsx");
        String bodyStr = "{ \"key\" = \"attachment\", \"value\" = \"" + requestB + "\" }";
        RequestBody body = RequestBody.create(JSON, bodyStr);
        String a = body.toString();

        try{
            url = DataManager.testrailBaseURL.concat(url);
            DataManager.userApiResponse  = HTTPManager.callPostMethod(url, body, auth, "value:multipart/form-data");
            List<Map<String, Object>> products = (List<Map<String, Object>>) DataManager.userApiResponse.get("");
            Map<String, Object> firstProduct = products.get(0);
            return;
        } catch (Exception e) {
            Log.info("ERROR sending request");
        }

    }
    //endregion - Testrail methods -

    //region examples from old project
    public static void activateUser(String userId) {
        String url = DataManager.UserActiveApiURL;
        RequestBody body = RequestBody.create(JSON, "{\"Number\":\""+ userId +"\"}");

        try{
            Map<String,Object> response  = HTTPManager.callPostMethod(url, body);
            return;
        } catch (Exception e) {
            Log.info("ERROR sending request");
        }
    }

    public static void UserDetails(String userId) {
        String url = DataManager.UserDeatailsApiUrl;
        RequestBody body = RequestBody.create(JSON, "{\"Number\":\""+ userId +"\"}");
        String a = body.toString();

        try{
            DataManager.userApiResponse  = HTTPManager.callPostMethod(url, body);
            List<Map<String, Object>> products = (List<Map<String, Object>>) DataManager.userApiResponse.get("");
            Map<String, Object> firstProduct = products.get(0);
            return;
        } catch (Exception e) {
            Log.info("ERROR sending request");
        }
    }

    /**
     * Method that returns OTP code for entered phone number
     * @param toNumber Phone number
     * @return 6-digit string
     */
    public static String OTPCode(String toNumber) {
        String res = "";
        String url = "/otp/guess";
        String req = "?"
                + "startDate=" + Utilities.GetDateTimeNowMinusMinutes(65).replace(":", "%3A")
                + "&endDate="+ Utilities.GetDateTimeNowMinusMinutes(-1).replace(":", "%3A")
                + "&toNumber=%2B"+ toNumber
                + "&consumed=false";
        try{
            DataManager.userApiResponse = HTTPManager.callGetMethod(url, req);
            res = DataManager.userApiResponse.get("otp").toString();
            return res;
        } catch (Exception e) {
            Log.info("ERROR sending request");
        }
        return res;
    }
    //endregion examples from old project
}
