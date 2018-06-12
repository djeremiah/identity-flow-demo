package rh.demo;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PolicyServiceApplication {

    public static void main(String... args){
        SpringApplication.run(PolicyServiceApplication.class);
    }

    @Bean
    public RouteBuilder routeBuilder(){
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                // API Call
                from("direct:process-validation")
                    .to("rest:post:validation/{field}?host={{api.host}}");

                // Initiate Policy
                rest().get("policy").route()
                    .removeHeaders("*")
                    .transform().method("policyStore", "newPolicy")
                    .setHeader("policy", body())
                    .to("direct:next-activity");

                //Stage 1
                from("direct:image-prompt")
                    .transform().groovy("request.body.need(new rh.demo.prompt.ImagePrompt(request.headers['policy']))");

                //Stage 2
                from("direct:ssn-prompt")
                    .transform().groovy("request.body.need(new rh.demo.prompt.SsnPrompt(request.headers['policy']))");

                from("direct:phone-prompt")
                    .transform().groovy("request.body.need(new rh.demo.prompt.PhonePrompt(request.headers['policy']))");

                //End
                from("direct:report-policy-data")
                    .setHeader("activityId").constant("report")
                    .to("bean:policyStore?method=completeActivity")
                    .transform().method("policyStore","getPolicyData");

                rest().post("policy/{policy}/{field}").route()
                    .to("direct:process-validation")
                    .choice()
                        .when().jsonpath("$[?(@.valid == true)]")
                            .to("bean:policyStore?method=addPolicyData")
                            .setHeader("activityId").header("field")
                            .to("bean:policyStore?method=completeActivity")
                            .to("direct:next-activity")
                        .otherwise()
                            .setHeader("policyRequest").groovy("new rh.demo.policy.PolicyRequest(request.headers['policy'])")
                            .transform().groovy("request.headers['policyRequest'].alert(request.body)")
                        .end()
                    .removeHeaders("*");

                from("direct:next-activity")
                    .transform().groovy("new rh.demo.policy.PolicyRequest(request.headers['policy'])")
                    .recipientList().method("policyStore","nextActivity").parallelProcessing()
                    .end();

            }
        };
    }
}
