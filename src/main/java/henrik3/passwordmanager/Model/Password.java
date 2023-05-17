/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package henrik3.passwordmanager.Model;

import java.io.Serializable;

/**
 *
 * @author Henrique
 */
public class Password implements Serializable {
    private String user, password;
    
    public Password(String user, String password)
    {
        this.user = user;
        this.password = password;
    }
    
    public String getUser()
    {
        return this.user;
    }
    
    public String getPassword()    
    {
        return this.password;
    }
    
}
