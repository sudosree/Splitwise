package com.splitwise.services;

import com.splitwise.models.expenses.*;
import com.splitwise.models.splits.Split;
import com.splitwise.models.user.User;

import java.util.List;

public class ExpenseService {

    public static Expense createExpense(ExpenseType type, User paidBy, double amountPaid, List<Split> splits) {

        Expense expense;

        switch (type) {
            case EQUAL:
                expense = new EqualExpense(paidBy, amountPaid, splits);
                expense.updateBalances();
                break;
            case EXACT:
                expense = new ExactExpense(paidBy, amountPaid, splits);
                expense.updateBalances();
                break;
            case PERCENT:
                expense = new PercentExpense(paidBy, amountPaid, splits);
                expense.updateBalances();
                break;
            default:
                return null;
        }
        return expense;
    }

}
