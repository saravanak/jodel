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

    public Query() {
        this.filters = new ArrayList<>();
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

}
