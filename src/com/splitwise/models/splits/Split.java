package com.splitwise.models.splits;

import com.splitwise.models.user.User;

/**
 * This class will track of which user will get how much amount
 * after the split.
 */
public abstract class Split {

    private User user;
    protected double amount;

    public Split(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
