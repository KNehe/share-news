package nehe.sharenews.utils;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class PasswordReset {

    public String generateResetToken(String email){

        return UUID.randomUUID().toString() + email;
    }

    public Date generateExpirationDate(){

        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_WEEK, 1);

        return calendar.getTime();
    }
}
