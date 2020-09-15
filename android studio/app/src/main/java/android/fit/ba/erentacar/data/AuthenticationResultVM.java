package android.fit.ba.erentacar.data;


import java.io.Serializable;

public class AuthenticationResultVM implements Serializable {
    public int UserId;
    public String FirstName;
    public String LastName;
    public String UserName;
    public String PasswordSalt;
    public String Phone;
    public String Email;
    public String Token;
}