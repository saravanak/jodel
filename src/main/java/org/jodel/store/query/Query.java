/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.store.query;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sathish_ku
 */
public class Query {

    private final List<Filter> filters;

    /**
     * Intialize with default values
     */
    public Query() {
        this.filters = new ArrayList<>();
    }

    /**
     * gets filters from the query
     * @return 
     */
    public List<Filter> getFilters() {
        return filters;
    }

    /**
     * Adds a Filter to the query
     * @param filter 
     */
    public void addFilter(Filter filter) {
        filters.add(filter);
    }

}
