package rs.aik.testautomation.AIKTestAutomation.Core;

import rs.aik.testautomation.AIKTestAutomation.Config.ConfigurationManager;

/**
 * Supported browsers (at least for now)
 *
 * @// TODO: Mobile device drivers support?
 */

public enum BrowserTypes {
    FIREFOX("Firefox"),
    CHROME("Chrome"),
    EDGE("Edge"),
    IE("Internet Explorer - IE"),
    OPERA("Opera"),
    SAFARI("Safari");

    BrowserTypes(final String type) {
        this.browserType = type;
    }

    private String browserType;
    public String getBrowserType() {
        return this.browserType;
    }

    /**
     * Checking if framework supports specific driver based on driver's name
     * @param browserType   Browser name as string
     * @return  true - if there's support for the browser, otherwise returns false
     */
    public static boolean isSupportedBrowser(String browserType){
        boolean result = false;

        BrowserTypes[] browsers = BrowserTypes.values();
        StringBuilder sb = new StringBuilder();

        for(int cnt=0; cnt<browsers.length; cnt++){

            sb.append(browsers[cnt].getBrowserType().toLowerCase() + "; ");
        }
        if(sb.toString().contains(browserType.toLowerCase())){  result = true;  }

        return result;
    }

    /**
     * method getBrowserByName use parameter string and returns BrowserType if such a type exists
     * @param browserType Browser name as string
     * @return BrowserType with that name
     * */

    public static BrowserTypes getBrowserByName(String browserType) {
        if (isSupportedBrowser(browserType)) {
            return BrowserTypes.valueOf(browserType.toUpperCase());
        } else {
            return ConfigurationManager.DEFAULT_BROWSER;
        }
    }
}
