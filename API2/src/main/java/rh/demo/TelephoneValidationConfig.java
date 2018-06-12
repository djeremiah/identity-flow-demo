package rh.demo;

import org.springframework.stereotype.Component;

@Component
public class TelephoneValidationConfig extends AbstractValidationConfig{

    public TelephoneValidationConfig() {
        super(TelephoneValidation.class, TelephoneValidation.class, "phone", "bean:telephoneValidator");
    }
}
