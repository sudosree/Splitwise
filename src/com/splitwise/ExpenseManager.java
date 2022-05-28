package com.splitwise;

import com.splitwise.models.expenses.Expense;
import com.splitwise.models.expenses.ExpenseType;
import com.splitwise.services.ExpenseService;
import com.splitwise.models.splits.Split;
import com.splitwise.models.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseManager {

    private Map<String, User> users;
    private List<Expense> expenses;
    private Map<String, Map<String, Double>> balanceSheet;

    public ExpenseManager() {
        this.users = new HashMap<>();
        this.expenses = new ArrayList<>();
        this.balanceSheet = new HashMap<>();
    }

    public void addUser(User user) {
        this.users.put(user.getUserId(), user);
        this.balanceSheet.put(user.getUserId(), new HashMap<>());
    }

    public void addExpense(ExpenseType type, String paidBy, double amountPaid, List<Split> splits) {
        Expense expense = ExpenseService.createExpense(type, users.get(paidBy), amountPaid, splits);
        // add it to the transaction
        expenses.add(expense);
        if (!balanceSheet.containsKey(paidBy)) {
            balanceSheet.put(paidBy, new HashMap<>());
        }
        for (Split s : splits) {
            double amountReceived = s.getAmount();
            Map<String, Double> balances = balanceSheet.get(paidBy);
            String paidTo = s.getUser().getUserId();
            balances.put(paidTo, balances.getOrDefault(paidTo, 0.0) + amountReceived);
            balances = balanceSheet.get(paidTo);
            balances.put(paidBy, balances.getOrDefault(paidBy, 0.0) - amountReceived);
        }
    }

    public void showBalance(String paidBy) {
        Map<String, Double> balances = balanceSheet.get(paidBy);
        if (balances.isEmpty()) {
            System.out.println("No balances");
            return;
        }
        for (String paidTo : balances.keySet()) {
            printBalance(paidBy, paidTo, balances.get(paidTo));
        }
    }

    public void showBalance() {
        boolean empty = true;
        for (String paidBy : balanceSheet.keySet()) {
            Map<String, Double> balances = balanceSheet.get(paidBy);
            for (String paidTo : balances.keySet()) {
                empty = false;
                printBalance(paidBy, paidTo, balances.get(paidTo));
            }
        }
        if (empty) {
            System.out.println("No balances");
        }
    }

    public void printBalance(String paidBy, String paidTo, double amount) {
        String user1 = users.get(paidBy).getName();
        String user2 = users.get(paidTo).getName();
        System.out.println(user2 + " owes " + user1 + ": " + Math.abs(amount));
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
