package com.splitwise;

import com.splitwise.models.expenses.ExpenseType;
import com.splitwise.models.splits.EqualSplit;
import com.splitwise.models.splits.ExactSplit;
import com.splitwise.models.splits.PercentSplit;
import com.splitwise.models.splits.Split;
import com.splitwise.models.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {

    public static void main(String[] args) throws IOException {
        ExpenseManager expenseManager = new ExpenseManager();

        expenseManager.addUser(new User("u1", "Sreemoyee", "abc.com", "9078654321"));
        expenseManager.addUser(new User("u2", "Priyanka", "def.com", "98865707654"));
        expenseManager.addUser(new User("u3", "Rajnandini", "tre.com", "87065432115"));
        expenseManager.addUser(new User("u4", "Saloni", "poi.com", "87905432113"));

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
        String command;
        while ((command = br.readLine()) != null) {
            String[] commands = command.split(" ");
            String commandType = commands[0];
            switch (commandType) {
                case "SHOW":
                    if (commands.length == 1) {
                        expenseManager.showBalance();
                    } else {
                        String userId = commands[1];
                        expenseManager.showBalance(userId);
                    }
                    break;
                case "EXPENSE":
                    String userPaidBy = commands[1];
                    double amountPaid = Double.parseDouble(commands[2]);
                    int users = Integer.parseInt(commands[3]);
                    String expenseType = commands[4 + users];
                    List<Split> splits = new ArrayList<>();
                    switch (expenseType) {
                        case "EQUAL":
                            for (int i=0;i<users;i++) {
                                String userPaidTo = commands[4 + i];
                                User user = expenseManager.getUsers().get(userPaidTo);
                                Split split = new EqualSplit(user);
                                splits.add(split);
                            }
                            expenseManager.addExpense(ExpenseType.EQUAL, userPaidBy, amountPaid, splits);
                            break;
                        case "EXACT":
                            for (int i=0;i<users;i++) {
                                String userPaidTo = commands[4 + i];
                                double amountReceived = Double.parseDouble(commands[5 + users + i]);
                                User user = expenseManager.getUsers().get(userPaidTo);
                                Split split = new ExactSplit(user, amountReceived);
                                splits.add(split);
                            }
                            expenseManager.addExpense(ExpenseType.EXACT, userPaidBy, amountPaid, splits);
                            break;
                        case "PERCENT":
                            for (int i=0;i<users;i++) {
                                String userPaidTo = commands[4 + i];
                                double amountReceived = Double.parseDouble(commands[5 + users + i]);
                                User user = expenseManager.getUsers().get(userPaidTo);
                                Split split = new PercentSplit(user, amountReceived);
                                splits.add(split);
                            }
                            expenseManager.addExpense(ExpenseType.PERCENT, userPaidBy, amountPaid, splits);
                            break;
                    }
            }
        }
    }
}
