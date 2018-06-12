package rh.demo.prompt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImagePrompt {

    @JsonRawValue
    private final String schema = "{\"$schema\": \"http://json-schema.org/draft-04/schema#\",\"description\": \"\",\"type\": \"object\",\"properties\": {\"url\": {\"type\": \"string\"}},\"required\": [\"url\"]}\n";
    private final String callbackUrl;


    public ImagePrompt(){
        this.callbackUrl = "";
    }

    public ImagePrompt(String policyId){
        this.callbackUrl = String.format("policy/%s/image", policyId);
    }

    public String getSchema() {
        return schema;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }
}
