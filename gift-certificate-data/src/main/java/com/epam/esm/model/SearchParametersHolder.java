package com.epam.esm.model;

public class SearchParametersHolder {
    String tagName;
    String name;
    String description;
    String sortBy;
    String sortOrder;

    public SearchParametersHolder() {
    }

    public SearchParametersHolder(String tagName, String name, String description, String sortBy, String sortOrder) {
        this.tagName = tagName;
        this.name = name;
        this.description = description;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
