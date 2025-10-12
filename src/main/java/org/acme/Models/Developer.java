package org.acme.Models;

import java.sql.Date;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Developer extends PanacheEntity{
    private String name;
    private Long age;
    private Date endDate;
    private Boolean fullStack;
    public Developer(){

    }
    public Developer(String name, Long age, Date endDate, Boolean fullStack) {
        this.name = name;
        this.age = age;
        this.endDate = endDate;
        this.fullStack = fullStack;
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
}
