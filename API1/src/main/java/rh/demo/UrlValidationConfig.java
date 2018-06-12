package rh.demo;

import org.springframework.stereotype.Component;

@Component
public class UrlValidationConfig extends AbstractValidationConfig{

    public UrlValidationConfig(){
        super(UrlValidation.class, UrlValidation.class, "image", "bean:urlValidator");
    }
}
