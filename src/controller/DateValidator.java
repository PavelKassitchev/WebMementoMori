package controller;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@FacesValidator
public class DateValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o)
            throws ValidatorException {
        String birthdate = ((String)o).trim();
        String message;
        Date now = new Date();
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = simpleDateFormat.parse(birthdate);
        } catch (ParseException e) {
            message = "The date of birth should be input in DD//MM//YYYY format";
            FacesMessage msg =new FacesMessage(message);
            throw new ValidatorException(msg);
        }
        long currentAgeInMillis = now.getTime() - date.getTime();
        if(currentAgeInMillis < 0) {
            message = "You should better be born first";
            FacesMessage msg = new FacesMessage(message);
            throw new ValidatorException(msg);
        }
        else if (currentAgeInMillis > 85 * Cuckoo.MILLIS_IN_YEAR) {
            message = "Over 85 years old? You are immortal!";
            FacesMessage msg = new FacesMessage(message);
            throw new ValidatorException(msg);
        }
    }
}
