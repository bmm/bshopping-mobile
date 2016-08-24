package org.lupum.bshopping;

/**
 * Created by bmm on 24/08/16.
 */
public class Product {
    private Long id;
    private String name;
    private boolean selected;

    public Product() {
        this.id = null;
        this.name = "";
        this.selected = false;
    }

    public Product(String name) {
        this.id = null;
        this.name = name;
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
}
