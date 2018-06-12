package rh.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.camel.impl.DefaultExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UrlValidator {

    @Autowired
    CamelContext ctx;

    @Handler
    public UrlValidation validate(UrlValidation validationRequest) {
        String url = validationRequest.getUrl();

        UrlValidation validationResponse = new UrlValidation();
        validationResponse.setUrl(url);

        ProducerTemplate producerTemplate = ctx.createProducerTemplate();
        Exchange ex = producerTemplate.send("rest:get:/?throwExceptionOnFailure=false&host=" + url, new DefaultExchange(ctx));
        String response = getResponseCode(ex);

        if ("200".equals(response)) {
            validationResponse.setValid(Boolean.TRUE);
        } else {
            validationResponse.setValid(Boolean.FALSE);
            validationResponse.setError(getErrorText(ex));
        }


        return validationResponse;
    }

    private String getResponseCode(Exchange ex) {
        return ex.getOut().getHeader(Exchange.HTTP_RESPONSE_CODE, String.class);
    }

    private String getErrorText(Exchange ex) {
        Optional responseText = Optional.ofNullable(ex.getOut().getHeader(RestletConstants.RESTLET_RESPONSE, String.class));
        return String.format("%s", responseText.orElse("no detail"));
    }
}
