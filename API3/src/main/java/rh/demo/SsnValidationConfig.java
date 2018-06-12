package rh.demo;

import org.springframework.stereotype.Component;

@Component
public class SsnValidationConfig extends AbstractValidationConfig{

    public SsnValidationConfig(){
        super(SsnValidation.class, SsnValidation.class, "ssn", "bean:ssnValidator");
    }
}
