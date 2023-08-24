/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.test.command.tgbot.demobot.configuration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author User
 */
@Configuration
@ConfigurationProperties(prefix = "userroles")
public class UserRolesConfig {
    
    private Map<Long, String> userAndStatus = new HashMap<>();
    
    public UserRolesConfig(Map<Long, String> userAndStatus) {
        this.userAndStatus = userAndStatus;
    }

    public void setUserAndStatus(Map<Long, String> userAndStatus) {
        this.userAndStatus = userAndStatus;
    }

    public Map<Long, String> getUserAndStatus() {
        return userAndStatus;
    }
    public void addNewUser(Long newId, String status){
        userAndStatus.put(newId, status);
    }

    

    

}
