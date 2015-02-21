/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jimisanchez
 */
public class GroupClass {
    
    private Integer groupId = null;
    private String groupName = null;
    private Boolean active = null;
    
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
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public Boolean getActive() {
        return this.active;
    }
}
