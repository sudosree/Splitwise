package com.splitwise.models.expenses;

import com.splitwise.models.splits.EqualSplit;
import com.splitwise.models.splits.PercentSplit;
import com.splitwise.models.splits.Split;
import com.splitwise.models.user.User;

import java.util.List;

public class EqualExpense extends Expense {

    public EqualExpense(User paidBy, double amountPaid, List<Split> splits) {
        super(paidBy, amountPaid, splits);
    }

    @Override
    protected boolean validate() {
        List<Split> splits = getSplits();
        for (Split split : splits) {
            if (!(split instanceof EqualSplit)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateBalances() {
        if (!validate()) {
            System.out.println("Could not update balances");
            return;
        }
        List<Split> splits = getSplits();
        int splitBy = splits.size();
        double amountPaid = getAmountPaid();
        for (Split split : splits) {
            EqualSplit equalSplit = (EqualSplit) split;
            double splitAmount = Math.round((amountPaid /splitBy) * 100.0)/100.0;
            equalSplit.setAmount(splitAmount);
        }
    }
}
