package controller;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@FacesValidator("pswValidator")
public class PswValidator implements Validator {

    /*private String password;

    public PswValidator() {
        password = new String();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o)
            throws ValidatorException {

        String confirm = (String)o;
        UIInput psw = (UIInput)uiComponent.findComponent("psw");
        String password = (String)psw.getLocalValue();
        if (confirm==null || password==null || !confirm.equals(password)) {
            FacesMessage msg = new FacesMessage("The input passwords don't match");
            throw new ValidatorException(msg);
        }
        /*if(((String)o).equals("ha")) {
            FacesMessage msg = new FacesMessage("Not valid");
            throw new ValidatorException(msg);
        }*/


    }
}
