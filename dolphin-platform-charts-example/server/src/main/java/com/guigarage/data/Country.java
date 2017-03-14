package com.guigarage.data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Country {

    private final List<String> towns;

    public Country() {
        this.towns = new CopyOnWriteArrayList<>();
    }

    public List<String> getTowns() {
        return towns;
    }
}
