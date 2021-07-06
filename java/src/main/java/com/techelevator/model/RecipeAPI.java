package com.techelevator.model;

public class RecipeAPI {
    private long id;
    private String title;

    public RecipeAPI(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "RecipeAPI{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
