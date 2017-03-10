package com.jaqen.roseshadow.models.bean;

import java.util.List;

/**
 * @author chenp
 * @version 2017-02-08 16:53
 */

public class GankData {
    private boolean error;
    private List<GankContent> results;

    public List<GankContent> getResults() {
        return results;
    }

    public boolean isError() {
        return error;
    }
}