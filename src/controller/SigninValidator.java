package controller;

import user.MementoEntity;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@FacesValidator
public class SigninValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        UIInput input = (UIInput) uiComponent.findComponent("login");
        String login = (String)input.getLocalValue();
        String psw = (String)o;
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("memento");
        EntityManager em = emf.createEntityManager();
        MementoEntity entity = em.find(MementoEntity.class, login);
        if(entity==null || !psw.equals(entity.getPassword())) {
            FacesMessage msg = new FacesMessage("Wrong login or password");
            throw new ValidatorException(msg);
        }
    }
}
