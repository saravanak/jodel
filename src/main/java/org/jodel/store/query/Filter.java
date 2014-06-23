/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.store.query;

/**
 *
 * @author sathish_ku
 */
public class Filter<T> {

    public enum Operator {

        EQUALS,
        GREATER,
        LESSER
    }

    private final String name;
    private final Operator operator;
    private final T value;

    public Filter(String name, Operator operator, T value) {
        this.name = name;
        this.operator = operator;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Operator getOperator() {
        return operator;
    }

    public T getValue() {
        return value;
    }

}
