package model;

import java.util.ArrayList;

public class MonthlyReport {
	private final int index;
	private final ArrayList<Item> incomes;
	private final ArrayList<Item> expenses;

	public void addIncome(Item item) {
		incomes.add(item);
	}

	public void addExpense(Item item) {
		expenses.add(item);
	}

	public MonthlyReport(int index) {
		this.index = index;
		incomes = new ArrayList<>();
		expenses = new ArrayList<>();
	}

	public int getIndex() {
		return index;
	}

	public ArrayList<Item> getIncomes() {
		return incomes;
	}

	public ArrayList<Item> getExpenses() {
		return expenses;
	}
}
