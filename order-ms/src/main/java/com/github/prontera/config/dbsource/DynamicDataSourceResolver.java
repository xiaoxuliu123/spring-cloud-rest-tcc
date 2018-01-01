package com.github.prontera.config.dbsource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import static com.github.prontera.config.dbsource.DynamicDataSourceContextHolder.*;


public class DynamicDataSourceResolver extends AbstractRoutingDataSource{

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSourceType();
    }
}
