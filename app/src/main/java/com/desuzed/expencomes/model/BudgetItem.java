package com.desuzed.expencomes.model;

public class BudgetItem {
    private String type;
    private String category;
    private long value;
    private String comment;

    public BudgetItem() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BudgetItem(String category) {
        this.category = category;
    }

    public BudgetItem(String category, long value, String comment, String type) {
        this.category = category;
        this.value = value;
        this.comment = comment;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }


}
