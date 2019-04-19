package controller;

import user.MementoEntity;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@FacesValidator("loginValidator")

public class LoginValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o)
            throws ValidatorException {
        String userName = (String)o;
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("memento");
        EntityManager em = emf.createEntityManager();
        MementoEntity entity = em.find(MementoEntity.class, userName);
        if(entity!=null) {
            FacesMessage msg =new FacesMessage("The login already exists!");
            throw new ValidatorException(msg);
        }
    }
}
