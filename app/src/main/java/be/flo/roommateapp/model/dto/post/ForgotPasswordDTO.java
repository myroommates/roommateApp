package be.flo.roommateapp.model.dto.post;

import be.flo.roommateapp.model.dto.technical.PostDTO;
import be.flo.roommateapp.model.util.annotation.Pattern;

/**
 * Created by florian on 4/03/15.
 */
public class ForgotPasswordDTO extends PostDTO{

    @Pattern(regex = Pattern.EMAIL, message = "Email conforme attendu")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ForgotPasswordDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}
