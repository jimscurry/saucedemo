package com.swaglabs.Tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterMethod;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Simple TestNG test which demonstrates being instantiated via a DataProvider
 * in order to supply multiple browser combinations.
 *
 * @author Shadab Siddiqui
 */
public class TestBase {

    // public String buildTag = System.getenv("BUILD_TAG");

    // public String buildTag = "jenkinsTestAlex";

    // Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    //
    // public String buildTag = "TestNGSwagLabs " + timestamp;

    public String username = System.getenv("SAUCE_USERNAME");

    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");

// public String accesskey = System.getenv("ba4d50e2-766f-4c71-a9e6-2b9abde028b4");
    /**
     * ThreadLocal variable which contains the {@link WebDriver} instance which
     * is used to perform browser interactions with.
     */
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    /**
     * ThreadLocal variable which contains the Sauce Job Id.
     */
    private ThreadLocal<String> sessionId = new ThreadLocal<String>();

    /**
     * DataProvider that explicitly sets the browser combinations to be used.
     *
     * @param testMethod
     * @return Two dimensional array of objects with browser, version, and
     * platform information
     */
    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
            // new Object[]{"MicrosoftEdge", "latest", "Windows 10"},
            // new Object[]{"MicrosoftEdge", "latest-1", "Windows 10"},
            //
            // new Object[]{"firefox", "latest", "Windows 10"},
            // new Object[]{"firefox", "latest-1", "Windows 10"},
            //
            // new Object[]{"internet explorer", "11.0", "Windows 7"},
            //
            // new Object[]{"safari", "latest", "OS X 10.11"},
            // new Object[]{"safari", "latest-1", "OS X 10.11"},
            //
            // new Object[]{"chrome", "latest", "Windows 10"},
            // new Object[]{"chrome", "latest-1", "Windows 10"},

            // new Object[]{"chrome", "latest", "OS X 10.11"},
            // new Object[]{"chrome", "latest-1", "OS X 10.11"},
            // new Object[]{"chrome", "latest", "OS X 10.10"},
            // new Object[]{"chrome", "latest-1", "OS X 10.10"},

            // new Object[]{"firefox", "latest-1", "Windows 8.1"},
            // new Object[]{"firefox", "latest", "Windows 10"},
            // new Object[]{"firefox", "latest", "OS X 10.11"},


            /**
            *** use these when running headless
            **/

            // new Object[]{"firefox", "latest", "Linux"},
            // new Object[]{"firefox", "latest-1", "Linux"},
            // new Object[]{"firefox", "latest-2", "Linux"},
            // new Object[]{"chrome", "latest", "Linux"},
            // new Object[]{"chrome", "latest-1", "Linux"},
            // new Object[]{"chrome", "latest-2", "Linux"},
        };
    }

    /**
     * @return the {@link WebDriver} for the current thread
     */
    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    /**
     *
     * @return the Sauce Job id for the current thread
     */
    public String getSessionId() {
        return sessionId.get();
    }

    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to
     * use the capabilities defined by the browser, version and os parameters,
     * and which is configured to run against ondemand.saucelabs.com, using the
     * username and access key populated by the {@link #authentication}
     * instance.
     *
     * @param browser Represents the browser to be used as part of the test run.
     * @param version Represents the version of the browser to be used as part
     * of the test run.
     * @param os Represents the operating system to be used as part of the test
     * run.
     * @param methodName Represents the name of the test case that will be used
     * to identify the test on Sauce.
     * @return
     * @throws MalformedURLException if an error occurs parsing the url
     */
    protected void createDriver(String browser, String version, String os, String methodName)
            throws MalformedURLException, UnexpectedException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // set desired capabilities to launch appropriate browser on Sauce
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        // capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability("name", methodName);
        capabilities.setCapability("extendedDebugging", true);
        capabilities.setCapability("capturePerformance", true);
        // capabilities.setCapability("tunnelIdentifier", "allTheTests");
        // capabilities.setCapability("build", System.getenv("JOB_NAME") + " __ " + System.getenv("BUILD_NUMBER") + " __ " + System.getenv("BUILD_TAG"));
        capabilities.setCapability("build", System.getenv("BUILD_TAG"));
//        capabilities.setCapability("avoidProxy", true);

        //Getting the build name.
        // Using the Jenkins ENV var. You can use your own. If it is not set test will run without a build id.
        // if (buildTag != null) {
        //     capabilities.setCapability("build", buildTag);
        // }
        System.out.println(capabilities);

        // Launch remote browser and set it as the current thread
        webDriver.set(new RemoteWebDriver(
                new URL("https://" + username + ":" + accesskey + "@ondemand.saucelabs.com:443/wd/hub"),
                // new URL("https://" + username + ":" + accesskey + "@ondemand.us-east-1.saucelabs.com/wd/hub"), //app.us-east-1.saucelabs.com
                // capabilities));

        // set current sessionId
        // String id = ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
        // sessionId.set(id);
       // String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s",
       //         sessionId, System.getenv("JOB_NAME"));
       // System.out.println(message);
    }

    /**
     * Method that gets invoked after test. Dumps browser log and Closes the
     * browser
     */
    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
        ((JavascriptExecutor) webDriver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        webDriver.get().quit();
    }

    protected void annotate(String text) {
        ((JavascriptExecutor) webDriver.get()).executeScript("sauce:context=" + text);
    }
}
