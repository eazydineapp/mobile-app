package com.eazydineapp.backend.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Category implements Serializable {
    private Long id;

    private String name;
    private String description;
    private int rank;

    private Set<Item> items = new HashSet<>();

    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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

    public Set<Item> getItems() {
		return items;
    }
	public void setItems(Set<Item> items) {
		this.items = items;
	}

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
