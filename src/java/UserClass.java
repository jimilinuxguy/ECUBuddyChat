/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;

/**
 *
 * @author jimisanchez
 */
public class UserClass implements Serializable {
    
    private Integer userId = null;
    private String userName = null;
    private String lastName = null;
    private String firstName = null;
    
    public UserClass() {
       //Todo
    }
    
    public void setUserId(Integer id) {
        this.userId = id;
    }
    
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
}
