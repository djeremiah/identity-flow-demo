package rh.demo.prompt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhonePrompt {

    @JsonRawValue
    private final String schema = "{\"$schema\": \"http://json-schema.org/draft-04/schema#\",\"description\": \"\",\"type\": \"object\",\"properties\": {\"tel\": {\"type\": \"string\"},\"region\": {\"type\": \"string\"}},\"required\": [\"tel\"]}";
    private final String callbackUrl;


    public PhonePrompt() {
        this.callbackUrl = "";
    }

    public PhonePrompt(String policyId) {
        this.callbackUrl = String.format("policy/%s/phone", policyId);
    }

    public String getSchema() {
        return schema;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

}
