package org.acme.DTO;

import java.math.BigDecimal;
import java.sql.Date;

public class DeveloperUpdateDTO {
    public String name;
    public Long age;
    public Date endDate;
    public Boolean fullStack;
    BigDecimal lat;
    BigDecimal lng;
    public DeveloperUpdateDTO(String name, Long age, Date endDate, Boolean fullStack, BigDecimal lat, BigDecimal lng){
        this.name = name;
        this.age = age;
        this.endDate = endDate;
        this.fullStack = fullStack;
        this.lat = lat;
        this.lng = lng;
    }
    public DeveloperUpdateDTO(){

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
    public BigDecimal getLat() {
        return lat;
    }
    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }
    public BigDecimal getLng() {
        return lng;
    }
    public void setLng(BigDecimal lng) {
        this.lng = lng;
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
