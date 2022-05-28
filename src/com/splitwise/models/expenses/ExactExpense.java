package com.splitwise.models.expenses;

import com.splitwise.models.splits.ExactSplit;
import com.splitwise.models.splits.Split;
import com.splitwise.models.user.User;

import java.util.List;

public class ExactExpense extends Expense {

    public ExactExpense(User paidBy, double amountPaid, List<Split> splits) {
        super(paidBy, amountPaid, splits);
    }

    @Override
    protected boolean validate() {
        List<Split> splits = getSplits();
        for (Split split : splits) {
            if (!(split instanceof ExactSplit)) {
                return false;
            }
        }
        double totalAmountPaid = getAmountPaid();
        double totalSplitAmount = 0;
        for (Split split : splits) {
            ExactSplit exactSplit = (ExactSplit) split;
            totalSplitAmount += exactSplit.getAmount();
        }
        return totalAmountPaid == totalSplitAmount;
    }

    @Override
    public void updateBalances() {
        if (!validate()) {
            System.out.println("Could not update balances");
        }
    }
}
