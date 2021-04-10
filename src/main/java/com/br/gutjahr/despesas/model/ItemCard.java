package com.br.gutjahr.despesas.model;

import java.io.Serializable;

public class ItemCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private Double value;

    public ItemCard(){}

    public ItemCard(String title, Double value){
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ItemCard{" +
                "title='" + title + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
