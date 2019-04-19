package controller;

import com.google.gson.Gson;
import user.MementoEntity;


import javax.ejb.Remove;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Named
@SessionScoped
public class Main implements Serializable {

    @Inject
    private MementoEntity mementoEntity;

    public MementoEntity getMementoEntity() {
        return mementoEntity;
    }

    public void setMementoEntity(MementoEntity mementoEntity) {
        this.mementoEntity = mementoEntity;
    }


    EntityManagerFactory emf = Persistence
            .createEntityManagerFactory("memento");
    EntityManager em = emf.createEntityManager();

    private Map<String, String> answerMap;

    public Map<String, String> getAnswerMap() {
        return answerMap;
    }

    public void setAnswerMap(Map<String, String> answerMap) {
        this.answerMap = answerMap;
    }

    public String signup(){


        /*EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("memento");
        EntityManager em = emf.createEntityManager();*/
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        em.persist(mementoEntity);
        etx.commit();

        return "introduction";
    }

    public String signin() {
        /*EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("memento");
        EntityManager em = emf.createEntityManager();*/

        mementoEntity = em.find(MementoEntity.class, mementoEntity.getLogin());
        String userdata = mementoEntity.getUserdata();
        String[] userAnnswers = new Gson().fromJson(userdata, String[].class);
        mementoEntity.setAnswers(userAnnswers);
        cuckoo = new CubicCuckoo(mementoEntity);
        return "result";
    }

    public String introduce() {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(mementoEntity);
        etx.commit();
        answerMap = new LinkedHashMap<>();
        if (mementoEntity.getAnswers()== null) {
            String[] userAnswers = new String[Questions.getLength()];
            for (int i = 0; i<Questions.getLength();i++) {
                userAnswers[i] = "0";
            }
            mementoEntity.setAnswers(userAnswers);
        }
        for (int i = 0; i<Questions.getLength(); i++) {
            answerMap.put((Questions.values()[i]).getQuestion(), mementoEntity.getAnswers()[i]);
        }
        return "questionnaire";
    }

    public String result() {
        String[] replies = answerMap.values().toArray(new String[Questions.getLength()]);
        mementoEntity.setAnswers(replies);
        cuckoo = new CubicCuckoo(mementoEntity);
        String userdata = new Gson().toJson(mementoEntity.getAnswers());
        mementoEntity.setUserdata(userdata);

        System.out.println(userdata);

        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(mementoEntity);
        etx.commit();
        return "result";
    }

    private CubicCuckoo cuckoo;

    public CubicCuckoo getCuckoo() {
        return cuckoo;
    }

    public void setCuckoo(CubicCuckoo cuckoo) {
        this.cuckoo = cuckoo;
    }


    public String exit() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        em.close();
        emf.close();
        return "index?face-redirect=true";
    }
}
