package model;

import java.util.HashMap;

public class YearlyReport {
	private final int index;
	private final HashMap<Integer, Double> incomes;
	private final HashMap<Integer, Double> expenses;

	public YearlyReport(int index) {
		this.index = index;
		incomes = new HashMap<>();
		expenses = new HashMap<>();
	}

	public void addIncome(int month, double income) {
		incomes.put(month, income);
	}

	public void addExpense(int month, double income) {
		expenses.put(month, income);
	}

	public int getIndex() {
		return index;
	}

	public HashMap<Integer, Double> getIncomes() {
		return incomes;
	}

	public HashMap<Integer, Double> getExpenses() {
		return expenses;
	}
}
