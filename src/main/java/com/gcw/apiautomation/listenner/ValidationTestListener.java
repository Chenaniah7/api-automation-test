package com.gcw.apiautomation.listenner;

import com.gcw.apiautomation.runner.ValidationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ValidationTestListener extends TestListenerAdapter implements ITestListener {
    public static final Logger LOGGER = LoggerFactory.getLogger(ValidationTestListener.class.getName());

    public static boolean validationTestPassed = true;

    @Override
    public void onTestStart(ITestResult result) {
        ValidationTestListener.LOGGER.info("Test started ======> {}",result.getMethod().getMethodName());
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        ValidationTestListener.LOGGER.info("Test success ======> {}",result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ValidationTestListener.LOGGER.info("Test Failure ======> {}",result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        if (testContext.getFailedTests().size() > 0){
            ValidationTestListener.setValidationTestPassed(false);
        }
    }

    public static boolean isValidationTestPassed() {
        return validationTestPassed;
    }

    public static void setValidationTestPassed(boolean validationTestPassed) {
        ValidationTestListener.validationTestPassed = validationTestPassed;
    }
}
