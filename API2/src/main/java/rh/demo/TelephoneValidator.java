package rh.demo;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL;
import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL;

@Component
public class TelephoneValidator {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Handler
    public TelephoneValidation validate(TelephoneValidation validationRequest) {
        TelephoneValidation validationResponse = new TelephoneValidation();

        String tel = validationRequest.getTel();
        String region = validationRequest.getRegion() != null ? validationRequest.getRegion() : "US";
        validationResponse.setRegion(region);

        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(tel, region);
            if (phoneNumberUtil.isValidNumber(number)) {
                validationResponse.setTel(phoneNumberUtil.format(number, "US".equals(region) ? NATIONAL : INTERNATIONAL));
                validationResponse.setValid(Boolean.TRUE);
            } else {
                validationResponse.setTel(tel);
                validationResponse.setValid(Boolean.FALSE);
                validationResponse.setError(String.format("%s is not a valid US telephone number", tel));
            }

        } catch (NumberParseException e) {
            validationResponse.setTel(tel);
            validationResponse.setValid(Boolean.FALSE);
            validationResponse.setError(e.getMessage());
        }

        return validationResponse;
    }
}
