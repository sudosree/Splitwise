package com.splitwise.models.expenses;

import com.splitwise.models.splits.Split;
import com.splitwise.models.user.User;

import java.util.List;

public abstract class Expense {

    private User paidBy;
    private double amountPaid;
    private List<Split> splits;

    public Expense(User paidBy, double amountPaid, List<Split> splits) {
        this.paidBy = paidBy;
        this.amountPaid = amountPaid;
        this.splits = splits;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public void setSplits(List<Split> splits) {
        this.splits = splits;
    }

    protected abstract boolean validate();

    public abstract void updateBalances();
}
