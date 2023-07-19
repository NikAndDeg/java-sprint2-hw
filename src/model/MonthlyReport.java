package model;

import java.util.ArrayList;

public class MonthlyReport {
	private int index;
	private ArrayList<Item> incomes;
	private ArrayList<Item> expenses;

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

	public void setIndex(int index) {
		this.index = index;
	}

	public ArrayList<Item> getIncomes() {
		return incomes;
	}

	public void setIncomes(ArrayList<Item> incomes) {
		this.incomes = incomes;
	}

	public ArrayList<Item> getExpenses() {
		return expenses;
	}

	public void setExpenses(ArrayList<Item> expenses) {
		this.expenses = expenses;
	}
}
