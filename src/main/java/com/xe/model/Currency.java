package com.xe.model;

public class Currency {
    private String symbol;

    private String description;
    private String descriptionPlural;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionPlural() {
        return descriptionPlural;
    }

    public void setDescriptionPlural(String descriptionPlural) {
        this.descriptionPlural = descriptionPlural;
    }
}
