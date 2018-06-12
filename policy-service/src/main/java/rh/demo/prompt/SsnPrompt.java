package rh.demo.prompt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsnPrompt {

    @JsonRawValue
    private final String schema = "{\"$schema\": \"http://json-schema.org/draft-04/schema#\",\"description\": \"\",\"type\": \"object\",\"properties\": {\"ssn\": {\"type\": \"string\"}},\"required\": [\"ssn\"]}\n";
    private final String callbackUrl;


    public SsnPrompt(){
        this.callbackUrl = "";
    }

    public SsnPrompt(String policyId){
        this.callbackUrl = String.format("policy/%s/ssn", policyId);
    }

    public String getSchema() {
        return schema;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }
}
