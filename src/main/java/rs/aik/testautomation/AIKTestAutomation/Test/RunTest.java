package rs.aik.testautomation.AIKTestAutomation.Test;
import org.junit.runner.JUnitCore;
import rs.aik.testautomation.AIKTestAutomation.Core.BaseMobile;
import rs.aik.testautomation.AIKTestAutomation.Data.DataManager;
import rs.aik.testautomation.AIKTestAutomation.Data.ExcelFactory;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Utilities;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.List;

public class RunTest {

    // helper main class to run Cucumber tests

    public static void main(String[] args) throws URISyntaxException, IOException {

            DataManager.datamap = ExcelFactory.getDataFromExcel("testdata/data.xlsx","Sheet1");
            if(DataManager.getDataFromHashDatamap("1", "isMock").equals("0")) {
                BaseMobile.AppiumServerJava appiumServer = new BaseMobile.AppiumServerJava();

                int port = 4723;
                if (!appiumServer.checkIfServerIsRunnning(port)) {

                    System.out.println("Starting Appium Server on port - " + port);
                    appiumServer.startServer();
                    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Stopping Appium server");
                            appiumServer.stopServer();
                        }
                    }));
                } else {
                    System.out.println("Appium Server already running on Port - " + port);
                }
            }
        List<String> envList = DataManager.envList;
        int run = 1;
        for(String env : envList) {
            DataManager.setDataForEnv(env);
            for(String browser : DataManager.webBrowsersList) {
                DataManager.webBrowser = browser;
                Utilities.replaceTestExcelFile();
                Utilities.replaceTestrailExcelFile();
                Utilities.cleanScreenShotFolder();
                //JUnitCore.runClasses(Runner.class);
                try{
                    JUnitCore.runClasses(Runner.class);
                    //JUnitCore.main("si.nlb.testautomation.NLBTestAutomation.Test.Runner");
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }
                //JUnitCore.main("si.nlb.testautomation.NLBTestAutomation.Test.Runner");
                URL rootResource = RunTest.class.getClassLoader().getResource(".");
                String targetFolder;
                if(rootResource == null) {
                    targetFolder = new File("./target").getAbsolutePath().replace("%20", " ");
                } else {
                    File currentDir = new File(RunTest.class.getClassLoader().getResource(".").getFile());
                    targetFolder = currentDir.getParent();
                }
                File cucumber = new File(targetFolder.replace("%20", " "), "cucumber");
                File destinationFile = new File(cucumber, "report" + env + "_" + browser + "_" + run + ".html");
                if(destinationFile.exists()) {
                    destinationFile.delete();
                }

                Files.move(Paths.get(new File(cucumber, "report.html").getAbsolutePath().replace("%20", " ")),
                        Paths.get(destinationFile.getAbsolutePath().replace("%20", " ")));
                Utilities.saveResults(DataManager.resultContainer);
                //Added for running failed tests again
                //DataManager.tryCallTestrail();
                Utilities.replaceExecutionListWithFailedExecutionList();
                Utilities.savePreviousTestResults();
                try {
                    Utilities.archiveStatusFile();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //Utilities.copyFileToServer();
                run++;
            }
        }
    }
}
