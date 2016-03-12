package com.v1_0.coen275ooad.nishant.www.ooadstocks.user;

/**
 * Created by nishant on 12/3/16.
 */
public class Authentication {

    private String username;
    private String password;

    public Authentication()
    {

    }
    public Authentication(String usn,String pwd)
    {
        username = usn;
        password = pwd;
    }

    public boolean setUsername(String usn)
    {
        username = usn;
        return true;
    }
    public boolean setPassword(String pwd)
    {
        password = pwd;
        return true;
    }
    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }
}
