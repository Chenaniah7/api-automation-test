package com.gcw.apiautomation.runner;

import com.gcw.apiautomation.core.config.ConfigProvider;
import com.gcw.apiautomation.listenner.ValidationTestListener;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.*;
import org.testng.xml.*;

import java.lang.reflect.Method;
import java.util.*;

@Listeners(ValidationTestListener.class)
@Test
public class ValidationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationRunner.class.getName());
    public static final String VALIDATION_TEST_REPORT_LISTENER = "com.vimalselvam.testng.listener.ExtentTestNgFormatter";
    public static final String BASE_TEST_PACKAGE = "com.gcw.testautomation.tests";
    private static final String ENTITY = System.getProperty("entity");
    private static final String ENV = System.getProperty("env");
    private List<XmlSuite> suitesList = new ArrayList<>();


    public ValidationRunner() {
    }

    @BeforeSuite
    public void onStart() {
        if (ENTITY != null && ENV != null) {
            LOGGER.info("Validation test suites preparation started!");
            Set<String> testPackages = new HashSet<>();
            testPackages.add(String.format("%s.functionalValidation",ValidationRunner.BASE_TEST_PACKAGE));
            testPackages.add(String.format("%s.requestValidation",ValidationRunner.BASE_TEST_PACKAGE));
            testPackages.forEach(this::createTestNGTestSuites);
        }
        this.runSuites();
    }

    @AfterSuite
    public void onSuiteFinish(){
        LOGGER.info("Validation test finished!!");
        if (!ValidationTestListener.isValidationTestPassed()){
            LOGGER.info("There are some validation test failed");
            Assert.fail();
        }
    }

    private void createTestNGTestSuites(final String testPackage){
        final List<Map<String, String>> testClassList = this.getTests(testPackage);
        if (testClassList.isEmpty()){
            return;
        }

        testClassList.forEach(testClass -> {
            final String className = testClass.get("name");
            final XmlSuite testSuite = this.getSuite(className+"_Suite.xml", className);
            testSuite.setMethodSelectors(getMethodSelectors());
            this.addTest(testSuite,testClass);
            this.suitesList.add(testSuite);
        });
    }

    private List<Map<String,String>> getTests(final String packageName){
        final Reflections reflections = new Reflections(packageName);
        final Set<Class<?>> testClasses = reflections.getTypesAnnotatedWith(Test.class);
        System.out.println(testClasses.toString());
        final List<Map<String,String>> testList = new ArrayList<>();

        if (testClasses.isEmpty()){
            LOGGER.info("No test found in the package " + packageName);
            return testList;
        }

        for (final Class<?> testClass : testClasses){
            final Map<String, String> testMap = new HashMap<>();
            final Set<String> groups = getGroupsOnTestClass(testClass);
            if (groups.contains(ENTITY)){
                testMap.put("name",testClass.getSimpleName());
                testMap.put("class",testClass.getCanonicalName());
                testList.add(testMap);
                LOGGER.info(String.format("This test class: %s add to test list", testClass.getCanonicalName()));
                break;
            }else {
                LOGGER.info("The test groups not included the entity: " +ENTITY);
            }
        }
        return testList;
    }

    private Set<String> getGroupsOnTestClass(final Class<?> testClass){
        final List<String> groupsOnTestClass = Arrays.asList(testClass.getAnnotation(Test.class).groups());
        final Set<String> groups = new HashSet<>(groupsOnTestClass);

        final List<Method> testMethods = Arrays.asList(testClass.getMethods());
        testMethods.forEach(t -> {
            final Set<String> groupsOnTestMethod = getGroupsOnTestMethod(t);
            groups.addAll(groupsOnTestMethod);
        });

        return groups;
    }

    private Set<String> getGroupsOnTestMethod(final Method testMethod){
        Set<String> groups = new HashSet<>();
        Arrays.asList(testMethod.getAnnotations()).forEach(annotation -> {
            if ("org.testng.annotations.Test".equalsIgnoreCase(annotation.annotationType().getCanonicalName())){
                final List<String> groupsOnTestMethod = Arrays.asList(testMethod.getAnnotation(Test.class).groups());
                groups.addAll(groupsOnTestMethod);
            }
        });
        return groups;
    }

    private XmlSuite getSuite(final String fileName, final String name){
        final XmlSuite xmlSuite = new XmlSuite();
        xmlSuite.setFileName(fileName);
        xmlSuite.setName(name);
        xmlSuite.addListener(ValidationRunner.VALIDATION_TEST_REPORT_LISTENER);
        return xmlSuite;
    }

    private XmlMethodSelectors getMethodSelectors(){
        final XmlMethodSelector methodSelector = new XmlMethodSelector();
        final XmlMethodSelectors methodSelectors = new XmlMethodSelectors();

        final String expression = String.format("groups.containsKey(\"%s\") && groups.containsKey(\"%s\")",ValidationRunner.ENTITY,ValidationRunner.ENV);
        XmlScript xmlScript =  new XmlScript();
        xmlScript.setLanguage("beanshell");
        xmlScript.setScript(expression);
        methodSelector.setScript(xmlScript);
        methodSelectors.setMethodSelector(methodSelector);
        return methodSelectors;
    }

    private void addTest(final XmlSuite suite, final Map<String,String> test){
        final XmlTest xmlTest = new XmlTest(suite);
        xmlTest.setName(test.get("name"));
        final XmlClass xmlClass = new XmlClass(test.get("class"));
        final List<XmlClass> xmlClassList = new ArrayList<>();
        xmlClassList.add(xmlClass);
        xmlTest.setXmlClasses(xmlClassList);
    }

    private void runSuites(){
        final TestNG testNG = new TestNG();
        testNG.setXmlSuites(this.getSuitesList());
        testNG.run();
    }

    public List<XmlSuite> getSuitesList() {
        return suitesList;
    }

}

