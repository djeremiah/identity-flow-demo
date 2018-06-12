package rh.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Component
public class Test extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:init?repeatCount=1")
            .process(exchange -> {
                List<String> fields = Arrays.asList("image", "phone", "ssn");
                exchange.getIn().setBody(fields);
            })
            .split(body())
                .setHeader("field", body())
                .transform(simple("/home/dmurphy/Repos/identity-flow-demo/data/${body}.json", File.class))
                .to("direct:process-validation");


    }
}
