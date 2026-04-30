package rs.aik.testautomation.AIKTestAutomation.Action;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import rs.aik.testautomation.AIKTestAutomation.Data.DataManager;
import rs.aik.testautomation.AIKTestAutomation.Data.HTTPManager;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Utilities;

import java.util.List;
import java.util.Map;

public class HTTPWatson {
    //region • settings •
    static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    static final MediaType JSONAttach = MediaType.get("multipart/form-data; charset=utf-8");
    //endregion • settings •

    public static void callFecSec(String transactionId, String userId, String customerId){
        //String url = DataManager.UserDeatailsApiUrl;
        //String auth = Utilities.getBasic64(DataManager.testrailUsername, DataManager.testrailPassword);
        String auth = Utilities.getBasic64("dbp", "dbp_123");
        String baseUrl = "https://tvrtesbis01.nlb.si:50100";
        String url;
        String requestB = "{\n" +
                "  \"requestHeader\": {\n" +
                "    \"applicationID\": \"AW5601\",\n" +
                //"    \"transactionTimestamp\": \"2023-01-30-10.43.06.000001\",\n" +
                "    \"transactionTimestamp\": \"{{transactionTimestamp}}\",\n" +
                //"    \"transactionID\": \"1675071786150\",\n" +
                "    \"transactionID\": \"{{transactionID}}\",\n" +
                "    \"bankID\": 1000,\n" +
                //"    \"userID\": \"1175460\",\n" +
                "    \"userID\": \"{{userID}}\",\n" +
                //"    \"customerID\": \"1175460\",\n" +
                "    \"customerID\": \"{{customerID}}\",\n" +
                "    \"customerType\": \"RETAIL\",\n" +
                "    \"language\": \"SI\"\n" +
                "  },\n" +
                "  \"request\": {\n" +
                //"    \"processInstanceID\": \"13507122-6945-4bc2-ba61-53085387fc50\",\n" +
                "    \"processInstanceID\": \"{{processInstanceID}}\",\n" +
                "    \"watsonIterationID\": \"1\",\n" +
                "    \"typeOfBusiness\": \"PERSONAL_LOAN\",\n" +
                "    \"productType\": \"445\",\n" +
                "    \"campaignNumber\": \"\",\n" +
                "    \"standardOffer\": \"YES\",\n" +
                "    \"monthlyExpense\": 100,\n" +
                "    \"employmentStatus\": \"EMPLOYED\",\n" +
                //"    \"expectedLiveDT\": \"2023-01-30\",\n" +
                "    \"expectedLiveDT\": \"{{expectedLiveDT}}\",\n" +
                "    \"accountNumber\": \"\",\n" +
                "    \"dependantFamilyMembers\": {\n" +
                "      \"numberOfAdults\": 0,\n" +
                "      \"numberOfChildren\": 0\n" +
                "    }\n" +
                "  }\n" +
                "}";
        requestB = requestB
                .replace("{{transactionTimestamp}}", Utilities.getNowYYYYMMDD_HHMMSSPlus())
                .replace("{{transactionID}}",transactionId)
                .replace("{{userID}}",userId)
                .replace("{{customerID}}",customerId)
                .replace("{{expectedLiveDT}}",Utilities.getNowYYYYMMDD_HHMMSSPlus());

        RequestBody body = RequestBody.create(JSON, requestB);
        //String a = body.toString();

        try{
            url = baseUrl.concat("/rest/Dbp/Origination/fecSec");

            DataManager.userApiResponse  = HTTPManager.callPostMethod(url, body, auth);
            List<Map<String, Object>> products = (List<Map<String, Object>>) DataManager.userApiResponse.get("");
            //Map<String, Object> firstProduct = products.get(0);
            //String runId = firstProduct.get("ID").toString();
            //String runId = DataManager.userApiResponse.get("id").toString();
            //DataManager.userObject.put("RunID", runId);
            return;
        } catch (Exception e) {
            Log.info("ERROR sending request");
        }

    }
}
