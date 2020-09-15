package android.fit.ba.rentacar.data;

/**
 * Created by Ibrahim on 25.7.2018..
 */

import java.io.Serializable;

public class AuthenticationLoginPostVM implements Serializable
{
    public String username ;
    public String password ;

    public AuthenticationLoginPostVM(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}