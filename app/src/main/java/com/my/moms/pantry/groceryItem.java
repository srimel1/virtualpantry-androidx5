/*
 * Copyright (c) 2020.. Stephanie Rimel
 */

package com.my.moms.pantry;


import java.util.Random;

public class groceryItem {
    private String name;
    private String lifecycle; //in days
    private String quantity;
    private String date;
    private String expirationDate;

    public groceryItem() {
    }


    public groceryItem(String name, String quantity, String lifecycle, String date, String expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.lifecycle = lifecycle;
        this.date = date;
        this.expirationDate = expirationDate;

    }

    public groceryItem(String name, String quantity, String date){
        this.name = name;
        this.quantity = quantity;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(String mLifecycle) {
        this.lifecycle = mLifecycle;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String mQuantity) {
        this.quantity = mQuantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    private static final Random RANDOM = new Random();

    public static int getRandFoodImage() {
        switch (RANDOM.nextInt(13)) {
            default:
            case 0:
                return R.drawable.foods_1;
            case 1:
                return R.drawable.foods_2;
            case 2:
                return R.drawable.foods_3;
            case 3:
                return R.drawable.foods_4;
            case 4:
                return R.drawable.foods_5;
            case 5:
                return R.drawable.foods_6;
            case 6:
                return R.drawable.foods_7;
            case 7:
                return R.drawable.foods_8;
            case 8:
                return R.drawable.foods_9;
            case 9:
                return R.drawable.foods_10;
            case 10:
                return R.drawable.foods_11;
            case 11:
                return R.drawable.foods_12;
            case 12:
                return R.drawable.foods_13;
            case 13:
                return R.drawable.foods_14;

        }
    }

    public static int getAvatar() {
        switch (RANDOM.nextInt(48)) {
            default:
            case 0:
                return R.drawable.aubergine;
            case 1:
                return R.drawable.beer;
            case 2:
                return R.drawable.birthday;
            case 3:
                return R.drawable.biscuit;
            case 4:
                return R.drawable.bread;
            case 5:
                return R.drawable.breakfast;
            case 6:
                return R.drawable.brochettes;
            case 7:
                return R.drawable.burger;
            case 8:
                return R.drawable.carrot;
            case 9:
                return R.drawable.cheese;
            case 10:
                return R.drawable.chicken;
            case 11:
                return R.drawable.chicken2;
            case 12:
                return R.drawable.chocolate;
            case 13:
                return R.drawable.chocolate1;
            case 14:
                return R.drawable.chocolate2;
            case 15:
                return R.drawable.cocktail;
            case 16:
                return R.drawable.coffee;
            case 17:
                return R.drawable.coke;
            case 18:
                return R.drawable.covering;
            case 19:
                return R.drawable.croissant;
            case 20:
                return R.drawable.cup;
            case 21:
                return R.drawable.cupcake;
            case 22:
                return R.drawable.donut;
            case 23:
                return R.drawable.egg;
            case 24:
                return R.drawable.fish;
            case 25:
                return R.drawable.fries;
            case 26:
                return R.drawable.honey;
            case 27:
                return R.drawable.hotdog;
            case 28:
                return R.drawable.jam;
            case 29:
                return R.drawable.jelly;
            case 30:
                return R.drawable.juice;
            case 31:
                return R.drawable.ketchup;
            case 32:
                return R.drawable.lemon;
            case 33:
                return R.drawable.lettuce;
            case 34:
                return R.drawable.loaf;
            case 35:
                return R.drawable.milk;
            case 36:
                return R.drawable.noodles;
            case 37:
                return R.drawable.pepper;
            case 38:
                return R.drawable.pickles;
            case 39:
                return R.drawable.pie;
            case 40:
                return R.drawable.pizza;
            case 41:
                return R.drawable.rice;
            case 42:
                return R.drawable.sausage;
            case 43:
                return R.drawable.spaguetti;
            case 44:
                return R.drawable.steak;
            case 45:
                return R.drawable.tea;
            case 46:
                return R.drawable.water;
            case 47:
                return R.drawable.watermelon;
            case 48:
                return R.drawable.wine;

        }
    }


}




