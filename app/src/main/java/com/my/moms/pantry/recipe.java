package com.my.moms.pantry;

public class recipe {


    private String name;
    private String description; //in days
    private String steps;
    private String ingredients;
    private String serving;


    public recipe() {
    }



    public recipe(String name, String description, String steps, String ingredients, String serving) {
        this.name = name;
        this.description = description;
        this.steps = steps;
        this.ingredients = ingredients;
        this.serving = serving;
    }

    public String getName() {
        return name;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }


}
