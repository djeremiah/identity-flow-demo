package rh.demo;

import org.apache.camel.builder.RouteBuilder;

public abstract class AbstractValidationConfig extends RouteBuilder{

    private final Class<? extends Validation> inType;
    private final Class<? extends Validation> outType;
    private final String fieldName;
    private final String validatorRoute;

    protected AbstractValidationConfig(Class<? extends Validation> inType, Class<? extends Validation> outType, String fieldName, String validatorRoute) {
        this.inType = inType;
        this.outType = outType;
        this.fieldName = fieldName;
        this.validatorRoute = validatorRoute;
    }

    @Override
    public void configure() throws Exception {
        rest("validation")
            .consumes("application/json").produces("application/json")

            .post(fieldName)
                .type(inType)
                .outType(outType)
            .to(validatorRoute);
    }
}
