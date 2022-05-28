package com.splitwise.models.splits;

import com.splitwise.models.user.User;

public class ExactSplit extends Split {

    public ExactSplit(User user, double amount) {
        super(user);
        this.amount = amount;
    }
}
