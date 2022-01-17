package com.bealean.scrummy.model;

public class GroceryListItem {

    private String name;
    private boolean done;

    public GroceryListItem(String name, boolean done) {
        this.name = name;
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
