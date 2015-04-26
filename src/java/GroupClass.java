/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jimisanchez and sdlauer
 */
public class GroupClass {
    
    private Integer groupId = null;
    private String groupName = null;
    private Integer active = 0;
    
    public void setGroupId(Integer id) {
        this.groupId = id;
    }
    
    public Integer getGroupId() {
        return this.groupId;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public String getGroupName() {
        return this.groupName;
    }
    
    public void setActive(Integer active) {
        this.active = active;
    }
    
    public Integer getActive() {
        return this.active;
    }
}
