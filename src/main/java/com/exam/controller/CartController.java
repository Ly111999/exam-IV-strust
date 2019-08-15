package com.exam.controller;

import com.exam.entity.Item;
import com.exam.entity.Product;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@ManagedBean(name = "cartController")
public class CartController {
    private List<Item> items;

    public CartController() {
        this.items = new ArrayList<Item>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String buy(Product product) {
        int index = this.exists(product);
        if (index == -1) {
            this.items.add(new Item(product, 1));
        } else {
            int quantity = this.items.get(index).getQuantity() + 1;
            this.items.get(index).setQuantity(quantity);
        }
        return "cart?faces-redirect=true";
    }

    public double total() {
        double totalAmount = 0;
        double totalDiscount = 0;
        for(Item item : this.items) {
            totalAmount += item.getProduct().getPrice() * item.getQuantity();
        }
        if (totalAmount >= 5000) {
            totalDiscount = totalAmount - ((totalAmount * 30)/100);
        }
        return totalAmount;
    }

    private int exists(Product product) {
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).getProduct().getId() == product.getId()) {
                return i;
            }
        }
        return -1;
    }
}
