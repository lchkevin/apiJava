package com.apijava.apijava.controller;

import com.alibaba.fastjson.JSONObject;
import com.apijava.apijava.Utils.Tools;
import com.apijava.apijava.domain.ApiInfo;
import com.apijava.apijava.domain.TestResultSummary;
import com.apijava.apijava.service.ApiInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("gatling")
@Slf4j
public class APIToGatling {
    private final ApiInfoService apiInfoService;
    private final Tools tools;

    public APIToGatling(ApiInfoService apiInfoService, Tools tools) {
        this.apiInfoService = apiInfoService;
        this.tools = tools;
    }

    @RequestMapping(value = "/builderUser")
    public ModelAndView processUser() {
        String systemName = "user_cms";
        List<ApiInfo> apiInfoList;
        apiInfoList = apiInfoService.findAllBySystemName(systemName);
        ModelAndView modelAndView = new ModelAndView("/gatlingBuilder");
        if (noApiWriteToFile(apiInfoList, modelAndView)) return modelAndView;
        apiInfoList.forEach(apiInfo -> writeToFile(apiInfo, systemName));
        List<TestResultSummary> testResultSummaries = new ArrayList<>();
        TestResultSummary testResultSummary = new TestResultSummary(String.valueOf(apiInfoList.size()), String.valueOf(apiInfoList.size()), String.valueOf(0));
        testResultSummaries.add(testResultSummary);
        modelAndView.addObject("testResultSummaries", testResultSummaries);
        return modelAndView;
    }

    @RequestMapping(value = "/builderAdmin")
    public ModelAndView processAdmin() {
        String systemName = "admin_cms";
        List<ApiInfo> apiInfoList;
        apiInfoList = apiInfoService.findAllBySystemName(systemName);
        ModelAndView modelAndView = new ModelAndView("/gatlingBuilder");
        if (noApiWriteToFile(apiInfoList, modelAndView)) return modelAndView;
        apiInfoList.forEach(apiInfo -> writeToFile(apiInfo, systemName));
        List<TestResultSummary> testResultSummaries = new ArrayList<>();
        TestResultSummary testResultSummary = new TestResultSummary(String.valueOf(apiInfoList.size()), String.valueOf(apiInfoList.size()), String.valueOf(0));
        testResultSummaries.add(testResultSummary);
        modelAndView.addObject("testResultSummaries", testResultSummaries);
        return modelAndView;
    }

    private boolean noApiWriteToFile(List<ApiInfo> apiInfoList, ModelAndView modelAndView) {
        if (apiInfoList.isEmpty()) {
            List<TestResultSummary> testResultSummaries = new ArrayList<>();
            TestResultSummary testResultSummary = new TestResultSummary(String.valueOf(0), String.valueOf(0), String.valueOf(0));
            testResultSummaries.add(testResultSummary);
            modelAndView.addObject("testResultSummaries", testResultSummaries);
            return true;
        }
        return false;
    }

    private void writeToFile(ApiInfo apiInfo,String systemName) {
        String uri = apiInfo.getUri();
        if (uri.contains("XXXX")) {
            uri = uri.replace("XXXX", "${myToken}");
        }
        String result = "package " + systemName + "\n\n";
        result += "import io.gatling.core.Predef._\n" +
                "import io.gatling.core.feeder.RecordSeqFeederBuilder\n" +
                "import io.gatling.core.structure.ScenarioBuilder\n" +
                "import io.gatling.http.Predef._\n" +
                "import io.gatling.http.protocol.HttpProtocolBuilder\n" +
                "\n\n" +
                "import scala.concurrent.duration._\n\n";
        result += "class " + apiInfo.getGatlingTestName() + " extends Simulation {\n";
        if (apiInfo.getSystemName().startsWith("user")) {
            result += "val accounts: RecordSeqFeederBuilder[String] = csv(\"accounts.csv\").random\n\n";
        }

        result += "val httpProtocol: HttpProtocolBuilder = http\n" +
                "    .baseURL(\"http://306.web.com\")\n" +
                "    .inferHtmlResources()\n" +
                "    .acceptHeader(\"*/*\")\n" +
                "    .acceptEncodingHeader(\"gzip, deflate\")\n" +
                "    .acceptLanguageHeader(\"zh-CN,zh;q=0.9\")\n" +
                "    .contentTypeHeader(\"application/json\")\n" +
                "    .userAgentHeader(\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36\")\n\n" +
                "  val minWaitMs: FiniteDuration = 1500 milliseconds\n" +
                "  val maxWaitMs: FiniteDuration = 2500 milliseconds\n" +
                "  val rampUpTimeSecs = 1\n" +
                "  val testTimeSecs = 10\n" +
                "  val noOfUsers = 1\n\n" +
                "  val headers_0 = Map(\n" +
                "    \"Origin\" -> \"http://306.web.com\",\n" +
                "    \"device_token\" -> \"667b985dd14e01c17b1d2f966fc48109\")\n\n";

        result += "  val headers_1 = Map(\n";
        if (!apiInfo.getHeader().isEmpty()) {
            result += toHeaderTxtString(apiInfo.getHeader());
        }
        result += "    \"device_token\" -> \"297553f1036aaa40b9b0493df3f1f27e\",\n" +
                "      \"Origin\" -> \"http://c6.web.com\")\n\n" +
                "val scn = scenario(\"" + apiInfo.getGatlingTestName() + "\")\n" +
                "    .during(testTimeSecs) {\n" +
                "       feed(accounts)\n" +
                "        .pace(minWaitMs, maxWaitMs)\n" +
                "        .exec(http(\"request_0\")\n";
        if (apiInfo.getSystemName().startsWith("user")) {
            result +=
                    "          .post(\"/api/v1/account/webapi/account/users/webEncryptLogin\")\n" +
                            "          .headers(headers_0)\n" +
                            "          .body(StringBody(\n" +
                            "            \"\"\"{\"hash\":\"C0lEeEVfPtj/jZLnlpL8bu038kD4.HsoJyGcFIuglEbM99BHNLhMW\",\"password\":\"MWE4NzhjNzU2YjFmOTdkNTkwOTEzMDhlNDkxMjYwNmIzOWRlZWM5ZWUyNjJjMjYyZDM2NTMwYWM2YmRjNDk3NDJjZWUyNzFmYWRkNzEzNGI5YmFmNzIzZDY4NTU5NjdhMWQyMDllMWNiYTE4ODg1ODcyYjBiODAxM2NlOThmZjIxMmNhNzRlNWIxYTg0MDhlZDkyZDAwM2E3YTUzMzIwNzFlMTgwYzM2N2I4ZjA2YTBmMTUxMTUxMGJmMGE2MWZiZDk4NDlkYzZlMDQxMjQxM2M2N2MwYWY0YzQwYmZiYTY1OTliYmM2NGNhMWY2ZTI1OTQ0YzQxNTY3YTczOTNkOQ==\",\n" +
                            "              |\"username\":\"${username}\",\"validateCode\":\"009527\",\"webUniqueCode\":\"IP3bEG\"}\"\"\".stripMargin)).asJSON\n" +
                            "          .check(status.is(200))\n" +
                            "          .check(jsonPath(\"$..access_token\") saveAs \"myToken\")\n";
        } else if (apiInfo.getSystemName().startsWith("admin")) {
            result +=
                    "          .post(\"/api/v1/account/admins/manager/login\")\n" +
                            "          .headers(headers_0)\n" +
                            "          .body(StringBody(\n" +
                            "            \"\"\"{\"username\":\"auto\",\"otpCode\":\"009527\",\"password\":\"123456\"}\"\"\".stripMargin)).asJSON\n" +
                            "          .check(status.is(200))\n" +
                            "          .check(jsonPath(\"$..access_token\") saveAs \"myToken\")\n";
        }
        result +=
                "          .resources(http(\"" + apiInfo.getGatlingTestName() + "\")\n" +
                        "               ." + apiInfo.getHttpMethod() + "(\"" + uri + "\")\n" +
                        "               .headers(headers_1)\n";
        if (!(apiInfo.getSetBody() == null)) {
            result += "               .body(StringBody(\"\"\"" + apiInfo.getSetBody() +"\"\"\"))\n";

        }

        result += "               .check(status.is(200))))\n" +
                "        }\n" +
                "  setUp(scn.inject(rampUsers(noOfUsers) over rampUpTimeSecs)).protocols(httpProtocol)\n" +
                "}";

        byte[] sourceByte = result.getBytes();
        try {
            File file = new File("C:\\Users\\kevin\\workspace\\testGatling1\\src\\test\\scala\\" + apiInfo.getSystemName() + "\\" + apiInfo.getGatlingTestName() + ".scala");
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(sourceByte);
            outputStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private String toHeaderTxtString(String header) {
        String[] result = new String[1];
        JSONObject object = tools.toJSONObject(header);
        object.keySet().forEach(headerKey -> result[0] = result[0] + "    \"" + headerKey + "\" -> \"" + object.get(headerKey) + "\",\n");
//        result[0] = result[0].replace(result[0].charAt(result[0].lastIndexOf(",")), ' ');
        result[0] = result[0].replace("null ", "");
        return result[0];
    }

    public static void main(String[] args) {
        Tools tools = new Tools();
        String result1 = "Content-Type: application/json,Accept: */*";

        String[] result = new String[1];
        JSONObject object = tools.toJSONObject(result1);
        object.keySet().forEach(headerKey -> result[0] = result[0] + "    \"" + headerKey.trim() + "\" -> \"" + object.get(headerKey) + "\",\n");
        log.info(String.valueOf(result[0].lastIndexOf(",")));
        result[0] = result[0].replace(result[0].charAt(result[0].lastIndexOf(",")), ' ');
        result[0] = result[0].replace("null", "");
        log.info(result[0]);
    }
}
