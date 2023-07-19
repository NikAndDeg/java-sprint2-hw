package model;

import java.util.HashMap;

public class YearlyReport {
	private int index;
	private HashMap<Integer, Double> incomes;
	private HashMap<Integer, Double> expenses;

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

	public void setIndex(int index) {
		this.index = index;
	}

	public HashMap<Integer, Double> getIncomes() {
		return incomes;
	}

	public void setIncomes(HashMap<Integer, Double> incomes) {
		this.incomes = incomes;
	}

	public HashMap<Integer, Double> getExpenses() {
		return expenses;
	}

	public void setExpenses(HashMap<Integer, Double> expenses) {
		this.expenses = expenses;
	}
}
