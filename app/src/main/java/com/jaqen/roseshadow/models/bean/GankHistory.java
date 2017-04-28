package com.jaqen.roseshadow.models.bean;

import java.util.List;

/**
 * @author chenp
 * @version 2017-04-19 14:43
 */

public class GankHistory {
    private boolean error;
    private List<String> results;

    public List<String> getResults() {
        return results;
    }

    public boolean isError() {
        return error;
    }
}
