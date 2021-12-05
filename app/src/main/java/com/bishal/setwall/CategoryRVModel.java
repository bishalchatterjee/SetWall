package com.bishal.setwall;

public class CategoryRVModel {

    private String category;
    private String categoryIVUrl;

    public CategoryRVModel(String category, String categoryIVUrl) {
        this.category = category;
        this.categoryIVUrl = categoryIVUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryIVUrl() {
        return categoryIVUrl;
    }

    public void setCategoryIVUrl(String categoryIVUrl) {
        this.categoryIVUrl = categoryIVUrl;
    }
}
