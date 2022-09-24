# api-automationtest-core
This is a api automation test framework, based on JAVA.

通过java + RestAssured + testNG编写的一个接口自动化测试框架，可以很方便的完成接口自动化测试

### 使用说明
1. 通过BaseStepFactory.getBaseStep()方法初始化一个BaseStep对象，在这个过程中会根据传入的java参数{entity}和{env}初始化Entity对象和Config对象
2. 往basestep对象中添加接口信息来组装接口，如url，headers，queryParams等等
3. 调用getResource()方法发送http请求(GET),同理有post,put等对应的封装好的方法
4. 请求成功后的所有response信息都会存在baseStep对象的Entity对象里面
5. 从BaseStep对象的Entity里面提取出我们所需要的信息并校验
#### 示例
       @Test(groups = {"phoneAddress"})
    public void testFrameworkHappyCase() throws JsonProcessingException {
        baseStep.setBaseUri(uri);
        baseStep.updateQueryParam("key",key);
        baseStep.updateQueryParam("phone",phone);
        baseStep.getResource();
        Assert.assertEquals(baseStep.getResourceCode(), HttpStatus.SC_OK);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(baseStep.getResponseJson());
        PhoneAddress phoneAddress = objectMapper.readValue(jsonNode.get("result").toString(), new TypeReference<PhoneAddress>() {});
        Assert.assertEquals(phoneAddress.getProvince(),"广东");
    }

### 使用maven test自动执行测试用例
可直接在项目目录下打开terminal终端，然后输**mvn test -Denv={测试环境} -Dentity={接口对象名称}**，即可自动执行测试用例