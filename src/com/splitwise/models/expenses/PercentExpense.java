package com.splitwise.models.expenses;

import com.splitwise.models.splits.PercentSplit;
import com.splitwise.models.splits.Split;
import com.splitwise.models.user.User;

import java.util.List;

public class PercentExpense extends Expense {

    public PercentExpense(User paidBy, double amountPaid, List<Split> splits) {
        super(paidBy, amountPaid, splits);
    }

    @Override
    protected boolean validate() {
        List<Split> splits = getSplits();
        for (Split split : splits) {
            if (!(split instanceof PercentSplit)) {
                return false;
            }
        }
        double totalAmountPaid = getAmountPaid();
        double totalPercentageShare = 0;
        for (Split split : splits) {
            PercentSplit percentSplit = (PercentSplit) split;
            double amount = Math.round(totalAmountPaid * percentSplit.getPercent())/100.0;
            totalPercentageShare += amount;
        }
        return totalAmountPaid == totalPercentageShare;
    }

    @Override
    public void updateBalances() {
        if (!validate()) {
            System.out.println("Could not update balances");
            return;
        }
        List<Split> splits = getSplits();
        double amountPaid = getAmountPaid();
        for (Split split : splits) {
            PercentSplit percentSplit = (PercentSplit) split;
            double splitAmount = Math.round(amountPaid * percentSplit.getPercent())/100.0;
            percentSplit.setAmount(splitAmount);
        }
    }
}
