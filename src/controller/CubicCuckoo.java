package controller;

import user.MementoEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//life span calculator for User user
public class CubicCuckoo implements Cuckoo {

    private MementoEntity user;

    public CubicCuckoo(MementoEntity user) {
        this.user = user;
    }
    @Override
    //returns the current user's age in years
    public double getCurrentAge() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = null;
        try {
            birthDate = simpleDateFormat.parse(user.getBirthDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date today = new Date();
        return (today.getTime() - birthDate.getTime()) / MILLIS_IN_YEAR;
    }

    @Override
    //returns personal correction on INITIAL life span depending on the user's reply to questions
    public double getCorrection() {
        double correction = 0;
        for (int i=0; i<Questions.getLength(); i++) {
            switch (user.getAnswers()[i]) {
                case "-1":
                    correction += Questions.getNegatives()[i];
                    break;
                case "1":
                    correction += Questions.getPositives()[i];
                    break;
                case "0":

            }
        }
        return correction;
    }



    @Override
    //returns the expected REMAINING life span in years, depending on user's geneder and current age
    public double getLifeSpan() {
        String gender = user.getGender();
        double ageInYears = getCurrentAge();
        double defaultMaleLifeSpan = 0.00007 * Math.pow(ageInYears, 3)
                - 0.0037 * Math.pow(ageInYears, 2) + 0.1131 * ageInYears + AVG_MALE;
        double defaultFemaleLifeSpan = 0.0000624 * Math.pow(ageInYears, 3)
                - 0.00532 * Math.pow(ageInYears, 2) + 0.157 * ageInYears + AVG_FEMALE;

        switch (gender) {
            case "MALE": return defaultMaleLifeSpan;
            case "FEMALE": return defaultFemaleLifeSpan;
            default: return (defaultFemaleLifeSpan + defaultMaleLifeSpan) / 2;
        }
    }

    private String deadline;

    public String getDeadline() {

        double ratio = (getLifeSpan() - getCurrentAge())/getLifeSpan();
        double remaining = getLifeSpan()+ ratio * getCorrection();

        String birthdate = user.getBirthDate();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date born = null;
        try {
            born = format.parse(birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long spanInMillis = born.getTime() + (long)(remaining * MILLIS_IN_YEAR);
        Date last = new Date(spanInMillis);
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM, yyyy");
        deadline = formatter.format(last);
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
