/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jodel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jodel.store.stereotype.Id;

/**
 *
 * @author sathish_ku
 */
public class SampleBean {
    
    @JsonProperty(required = true)
    @Id
    private String name;
    
    private Integer age;
    
    private Long longSalary;
    
    private Double salary;
    
    private Boolean isMale;

    public Long getLongSalary() {
        return longSalary;
    }

    public void setLongSalary(Long longSalary) {
        this.longSalary = longSalary;
    }    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Boolean isIsMale() {
        return isMale;
    }

    public void setIsMale(Boolean isMale) {
        this.isMale = isMale;
    }
    
    
    
}
