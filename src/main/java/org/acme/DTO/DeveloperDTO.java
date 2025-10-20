package org.acme.DTO;

import java.sql.Date;

public class DeveloperDTO {
    public String name;
    public Long age;
    public Date endDate;
    public Boolean fullStack;
    public DeveloperDTO(String name, Long age, Date endDate, Boolean fullStack){
        this.name = name;
        this.age = age;
        this.endDate = endDate;
        this.fullStack = fullStack;
    }
    public DeveloperDTO(){

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getAge() {
        return age;
    }
    public void setAge(Long age) {
        this.age = age;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Boolean getFullStack() {
        return fullStack;
    }
    public void setFullStack(Boolean fullStack) {
        this.fullStack = fullStack;
    }
    @Override
    public String toString() {
        return "DeveloperDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", endDate='" + endDate + '\'' +
                ", fullStack=" + fullStack +
                '}';
    }
}
