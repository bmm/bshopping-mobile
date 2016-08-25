package org.lupum.bshopping;

import android.support.annotation.NonNull;

import java.text.Collator;

public class Product implements Comparable<Product> {
    private Long id;
    private String name;
    private boolean selected;

    public Product() {
        this.id = null;
        this.name = "";
        this.selected = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NonNull Product product) {
        return Collator.getInstance().compare(name, product.getName());
    }
}
