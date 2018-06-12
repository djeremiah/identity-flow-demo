package rh.demo;

import org.apache.camel.Handler;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

@Component
public class SsnValidator {

    KieContainer kieContainer;

    public SsnValidator(){
        KieServices ks = KieServices.Factory.get();
        kieContainer = ks.getKieClasspathContainer();

    }

    @Handler
    public SsnValidation validate(SsnValidation validationRequest) {
        KieSession session = kieContainer.newKieSession();

        // assume valid, rules will mark invalid
        validationRequest.setValid(Boolean.TRUE);

        session.insert(validationRequest);
        session.fireAllRules();
        session.dispose();

        return validationRequest;
    }
}
