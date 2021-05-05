package reporter;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
    private static final ThreadLocal<ExtentTest> extentTestMap = new ThreadLocal<>();

    private ExtentTestManager() {
    }

    protected static synchronized ExtentTest getTest() {
        return extentTestMap.get();
    }

    protected static synchronized ExtentTest createTest(String testName) {
        return createTest(testName, "");
    }

    protected static synchronized ExtentTest createTest(String testName, String desc) {
        ExtentTest test = ExtentManager.getInstance().createTest(testName, desc);
        extentTestMap.set(test);
        return test;
    }
}