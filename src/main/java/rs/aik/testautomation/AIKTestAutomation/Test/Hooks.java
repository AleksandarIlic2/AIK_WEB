package rs.aik.testautomation.AIKTestAutomation.Test;

import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.junit.AssumptionViolatedException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import rs.aik.testautomation.AIKTestAutomation.Core.BaseMobile;
import rs.aik.testautomation.AIKTestAutomation.Data.DataManager;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Utilities;
import rs.aik.testautomation.AIKTestAutomation.Core.Base;
import rs.aik.testautomation.AIKTestAutomation.Data.ExcelFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class Hooks {
    String FeatureName = "";
    String ScName = "";
    String FeatureId = "";
    String FeaturePriority = "";

    String dbWriteResults = "";
    LocalDateTime t;
    public static String resourceId;



    /*@BeforeStep
    public void beforeStepFunction(Scenario scenario){
        Utilities.checkIfPopupInterruptTesting();
    }*/

    /**
     * checks if should skip scenario based on listForExecution,
     * creates driver and gets data from excel file testdata/<Feature Name>.xlsx to be used in test
     * */

    @Before
    public void beforeHookFunction(Scenario scenario) throws IOException, InterruptedException {
        try{
            if (DataManager.envName.equals(null)){
                //
                }
        } catch (Exception e){
        List<String> envList = DataManager.envList;
        int run = 1;
        for(String env : envList) {
            DataManager.setDataForEnv(env);
        }
        }

        ScName = scenario.getName();
        DataManager.userObject.clear();
        boolean isIEOnly = scenario.getSourceTagNames().contains("@IE");
        boolean shouldRunOnBrowser = !isIEOnly || DataManager.webBrowser.equals("IE");
        if(!DataManager.listForExecution.contains(ScName) || !shouldRunOnBrowser) {
            throw new AssumptionViolatedException("not in execution");
        }
        Base.createDriver();
        DataManager.configurationMap = ExcelFactory.getDataFromExcel("testdata/configuration.xlsx","Sheet1");
        DataManager.datamap = ExcelFactory.getDataFromExcel("testdata/data.xlsx","Sheet1");
        /*if(DataManager.getDataFromHashDatamap("1", "isMock").equals("0")){
            BaseMobile.createDriver();
        }*/
        FeatureName = ExcelFactory.ReadExcelByKey("testdata/listForExecution.xlsx","TestName",ScName,"Feature");
        FeatureId = ExcelFactory.ReadExcelByKey("testdata/listForExecution.xlsx","TestName",ScName,"ID");
        FeaturePriority = ExcelFactory.ReadExcelByKey("testdata/listForExecution.xlsx","TestName",ScName,"Priority");
        DataManager.datamap = ExcelFactory.getDataFromExcel("testdata/" + FeatureName + ".xlsx","Sheet1");
        dbWriteResults = DataManager.getDataFromConfiguration("1","dbWriteResults");
        //String dbEngineReport = DataManager.getDataFromConfiguration("1","dbEngineReport");
        //String dbConnectionStringReport = DataManager.getDataFromConfiguration("1","dbConnectionStringReport");

        Log.startTestCase(ScName);
        t = Utilities.now();
    }

    /**
     * checks scenario result and logs it, if failed saves browser screenshot to testdata folder,
     * closes web driver
     * */

    @After
    public void afterHookFunction(Scenario scenario) throws SQLException {
        if (scenario.isFailed()) {
            try {
                //Dodatak za failed list of execution
                ExcelFactory.WriteFailedTestResultInNewExecutionList(ScName,FeatureId,FeaturePriority);
                String errorMessage = "";
                try {
                    if(!(Log.lastError.equals(null)))
                        errorMessage = Log.lastError;
                }catch (Exception ex) {}
                String strDate = new SimpleDateFormat("dd_MM_yyyy_HH_mm").format(Calendar.getInstance().getTime());
                File file = ((TakesScreenshot) Base.driver).getScreenshotAs(OutputType.FILE);
                String screenshotName = Utilities.removeSpecials(scenario.getName()) + "_" + strDate + ".jpg";
                FileUtils.copyFile(file, new File("results/" + screenshotName));
                FileUtils.copyFile(file, new File("results/LastSession/" + screenshotName));
                Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Test Scenario " + ScName + " failed.!!!!!!!!!!!!!!!!!!!!!!!!");
                ExcelFactory.WriteTestResult(ScName, "FAILED",screenshotName);
                if (DataManager.dbWriteResults.equals("ON")){
                try {
                    DataManager.WriteTestResult(ScName, "FAILED","Screen_" + scenario.getName() + "_" + strDate + ".png");
                } catch (SQLException throwables) {
                    Log.error(throwables.getMessage());
                }}
                Log.error("FAILED!");
                HashMap<String, Object> nh = new HashMap<>();
                nh.put("name", ScName);
                nh.put("id", FeatureId);
                nh.put("priority", FeaturePriority);
                nh.put("message", errorMessage);
                nh.put("result", "FAIL");
                boolean toAdd = true;
                boolean toRemove = false;
                HashMap<String, Object> forRemove = null;
                for(HashMap<String, Object> item : DataManager.resultContainer){
                    if(item.get("id").toString().equals(FeatureId)){
                        if(item.get("result").equals("FAIL"))
                            toAdd = false;
                        else{
                            forRemove = item;
                            toRemove = true;
                        }

                    }
                }
                if(toRemove)
                    DataManager.resultContainer.remove(forRemove);
                if(toAdd)
                    DataManager.resultContainer.add(nh);
                Base.closeDriver();
            } catch (Exception e) {
                String errorMessage = "";
                try {
                    if(!(e.getMessage().equals(null))){
                        errorMessage = e.getMessage();
                    }
                }catch (Exception ex){}
                Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Screenshot for scenario \""+scenario.getName()+"\" failed.!!!!!!!!!!!!!!!!!!!!!!!!");
                ExcelFactory.WriteTestResult(ScName, "FAILED","failed");
                DataManager.dbWriteResults = DataManager.getDataFromConfiguration("1","dbWriteResults");
                if (DataManager.dbWriteResults.equals("ON")){
                try {
                    DataManager.WriteTestResult(ScName, "FAILED","failed");
                } catch (SQLException throwables) {
                    Log.error(throwables.getMessage());
                }}
                Log.error("Exception: "+e+".");
                Log.error("FAILED!");
                HashMap<String, Object> nh = new HashMap<>();
                nh.put("name", ScName);
                nh.put("id", FeatureId);
                nh.put("priority", FeaturePriority);
                nh.put("message", errorMessage);
                nh.put("result", "FAIL");
                boolean toAdd = true;
                boolean toRemove = false;
                HashMap<String, Object> forRemove = null;
                for(HashMap<String, Object> item : DataManager.resultContainer){
                    if(item.get("id").toString().equals(FeatureId)){
                        if(item.get("result").equals("FAIL"))
                            toAdd = false;
                        else{
                            forRemove = item;
                            toRemove = true;
                        }
                    }
                }
                if(toRemove)
                    DataManager.resultContainer.remove(forRemove);
                if(toAdd)
                    DataManager.resultContainer.add(nh);
                Base.closeDriver();
            }
        }

        else if(!scenario.getStatus().toString().equals("SKIPPED"))
        {
            Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!! TestScenario \""+ScName+"\" finished successfully.!!!!!!!!!!!!!!!!!!!!!!!!");
            ExcelFactory.WriteTestResult(ScName, "PASSED","");
            DataManager.dbWriteResults = DataManager.getDataFromConfiguration("1","dbWriteResults");
            if (DataManager.dbWriteResults.equals("ON")){
            try {
                DataManager.WriteTestResult(ScName, "PASSED","");
            } catch (SQLException throwables) {
                Log.error(throwables.getMessage());
            }}
            Log.info("PASSED!");

            Log.endTestCase(ScName);
            HashMap<String, Object> nh = new HashMap<>();
            nh.put("name", ScName);
            nh.put("id", FeatureId);
            nh.put("priority", FeaturePriority);
            nh.put("result", "PASSED");
            nh.put("time", Utilities.timeBetween(Utilities.now(), t));
            boolean toAdd = true;
            for(HashMap<String, Object> item : DataManager.resultContainer){
                if(item.get("id").toString().equals(FeatureId)){
                    toAdd = false;
                }
            }
            if(toAdd)
                DataManager.resultContainer.add(nh);
            Base.closeDriver();
        }
    }
}
