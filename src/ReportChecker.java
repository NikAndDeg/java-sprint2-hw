import model.Item;
import model.MonthlyReport;
import model.YearlyReport;

import static model.MonthsName.MONTHS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class ReportChecker {

	public static void startReportChecker() {
		Scanner sc = new Scanner(System.in);
		FileReader fileReader = new FileReader();
		ArrayList<MonthlyReport> monthlyReports = null;
		YearlyReport yearlyReport = null;
		while (true) {
			printMenu();
			switch (sc.next()) {
				case "1": // 1 — Считать месячные отчеты.
					System.out.println("За какой год считать месячные отчеты?");
					monthlyReports = readAllMonthsReports(fileReader, sc.nextInt());
					break;
				case "2": // 2 — Считать годовой отчет.
					System.out.println("За какой год считать отчет?");
					yearlyReport = readYearlyReport(fileReader, sc.nextInt());
					break;
				case "3": // 3 — Вывести информацию о месячных отчетах.
					printMonthsReportsInfo(monthlyReports);
					break;
				case "4": // 4 — Вывести информацию о годовом отчете.
					printYearlyReportsInfo(yearlyReport);
					break;
				case "5": // 5 — Сверить отчеты.
					checkReports(yearlyReport, monthlyReports);
					break;
				case "0": // 0 — Выход из программы.
					System.out.println();
					return;
				default:
					System.out.println("Такой команды нет.");
			}
		}
	}

	private static void printYearlyReportsInfo(YearlyReport yearlyReport) {
		if (yearlyReport == null) {
			System.out.println("Сначала надо считать годовой отчет.");
			return;
		}
		HashMap<Integer, Double> incomes = yearlyReport.getIncomes();
		HashMap<Integer, Double> expenses = yearlyReport.getExpenses();
		if (incomes.size() != expenses.size()) {
			System.out.println("Проверьте, что если за один и тот же месяц указаны как расходы, так и доходы.");
			return;
		}
		double averageIncomes = 0;
		double averageExpenses = 0;
		System.out.println(yearlyReport.getIndex());
		for (int i = 1; i <= incomes.size(); i++) {
			averageExpenses += expenses.get(i);
			averageIncomes += incomes.get(i);
			double profit = incomes.get(i) - expenses.get(i);
			System.out.println("Прибыль за " + MONTHS.get(i) + " составила: " + profit);
		}
		System.out.println("Средний расход за все имеющиеся операции в году: " + averageExpenses / expenses.size());
		System.out.println("Средний доход за все имеющиеся операции в году: " + averageIncomes / incomes.size());
	}

	private static void printMonthsReportsInfo(ArrayList<MonthlyReport> monthlyReports) {
		if (monthlyReports == null) {
			System.out.println("Сначала надо считать месячные отчеты.");
			return;
		}
		for (MonthlyReport monthlyReport : monthlyReports) {
			System.out.println(MONTHS.get(monthlyReport.getIndex()));
			Item item = findMostExpensiveItem(monthlyReport.getIncomes());
			if (item == null)
				System.out.println("В этом месяце не было прибыльных товаров.");
			else
				System.out.println("Самый прибыльный товар " + item.getName()+ ". Его сумма "
				+ item.getPrice() * item.getQuantity() + ".");
			item = findMostExpensiveItem(monthlyReport.getExpenses());
			if (item == null)
				System.out.println("В этом месяце не было трат.");
			else
				System.out.println("Самая большая трата " + item.getName()+ ". Ее сумма "
						+ item.getPrice() * item.getQuantity() + ".");

		}
	}

	private static Item findMostExpensiveItem(ArrayList<Item> items) {
		Item mostProfItem = null;
		double highestProf = 0;
		for (Item item : items) {
			double prof = item.getQuantity() * item.getPrice();
			if (prof > highestProf) {
				mostProfItem = item;
				highestProf = prof;
			}
		}
		return mostProfItem;
	}

	private static ArrayList<MonthlyReport> readAllMonthsReports(FileReader fileReader, int year) {
        /*
        Метод считывает все месячные отчеты из папки resources и добавляет их в список
        с месячными отчетами.
         */
		ArrayList<MonthlyReport> monthlyReports = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			String fileName = "";
			if (i < 10)
				fileName = "m." + year + "0" + i + ".csv";
			else
				fileName = "m." + year + i + ".csv";
			ArrayList<String> file = fileReader.readFileContents(fileName);
			if (file.isEmpty())
				System.out.println("Отчет за " + MONTHS.get(i) + " не считан.");
			else {
				System.out.println("Отчет за " + MONTHS.get(i) + " считан.");
				MonthlyReport monthlyReport = createMonthlyReport(file, i);
				monthlyReports.add(monthlyReport);
			}
		}
		return monthlyReports;
	}

	private static MonthlyReport createMonthlyReport(ArrayList<String> file, int index) {
        /*
        Метод создает экземпляр MonthlyReport из списка строк CSV и номера месяца.
         */
		MonthlyReport monthlyReport = new MonthlyReport(index);
		for (int i = 1; i < file.size(); i++) {
			String line = file.get(i);
			String[] splitLine = line.split(",");
			boolean isExpense = Boolean.parseBoolean(splitLine[1]);
			if (isExpense)
				monthlyReport.addExpense(createItem(splitLine));
			else
				monthlyReport.addIncome(createItem(splitLine));
		}
		return monthlyReport;
	}

	private static Item createItem(String[] splitLine) {
        /*
        Метод создает экземпляр Item из splitLine -- разбитой по запятой строки из файла CSV.
        с месячным отчетом.
         */
		String name = splitLine[0];
		double price = Double.parseDouble(splitLine[3]);
		int quantity = Integer.parseInt(splitLine[2]);
		return new Item(name, price, quantity);
	}

	private static YearlyReport readYearlyReport(FileReader fileReader, int index) {
        /*
        Метод считывает годовой отчет из папки resources.
         */
		String fileName = "y." + index + ".csv";
		ArrayList<String> file = fileReader.readFileContents(fileName);
		if (file.isEmpty())
			System.out.println("Отчет за " + index + " не считан.");
		else {
			System.out.println("Отчет за " + index + " считан.");
			return createYearlyReport(file, index);
		}
		return null;
	}

	public static YearlyReport createYearlyReport(ArrayList<String> file, int index) {
		/*
        Метод создает экземпляр YearlyReport из списка строк CSV и номера года.
         */
		YearlyReport yearlyReport = new YearlyReport(index);
		for (int i = 1; i < file.size(); i++) {
			String[] splitLine = file.get(i).split(",");
			int month = Integer.parseInt(splitLine[0]);
			double amount = Double.parseDouble(splitLine[1]);
			boolean isExpense = Boolean.parseBoolean(splitLine[2]);
			if (isExpense)
				yearlyReport.addExpense(month, amount);
			else
				yearlyReport.addIncome(month, amount);
		}
		return yearlyReport;
	}

	private static void checkReports(YearlyReport yearlyReport, ArrayList<MonthlyReport> monthlyReports) {
		if (yearlyReport == null) {
			System.out.println("Сначала надо считать годовой отчет.");
			return;
		}
		if (monthlyReports == null) {
			System.out.println("Сначала надо считать месячные отчеты.");
			return;
		}
		System.out.println("Сверка годового и месячных отчетов.");
		boolean isCheckSuccessful = true;
		HashMap<Integer, Double> incomesFromMR = new HashMap<>();
		HashMap<Integer, Double> expensesFromMR = new HashMap<>();
		for (MonthlyReport monthlyReport : monthlyReports) {
			double income = 0;
			double expense = 0;
			for (Item item : monthlyReport.getIncomes()) {
				income += item.getPrice() * item.getQuantity();
			}
			for (Item item : monthlyReport.getExpenses()) {
				expense += item.getPrice() * item.getQuantity();
			}
			incomesFromMR.put(monthlyReport.getIndex(), income);
			expensesFromMR.put(monthlyReport.getIndex(), expense);
		}
		HashMap<Integer, Double> incomesFromYR = yearlyReport.getIncomes();
		HashMap<Integer, Double> expensesFromYR = yearlyReport.getExpenses();
		for (Integer monthIndex : incomesFromYR.keySet()) {
			if (!Objects.equals(incomesFromYR.get(monthIndex), incomesFromMR.get(monthIndex))) {
				System.out.print("Несоответствие суммы доходов в " + MONTHS.get(monthIndex) + ": ");
				System.out.println("Доходы из годового отчета: " + incomesFromYR.get(monthIndex));
				System.out.println("Доходы из месячного отчета: " + incomesFromMR.get(monthIndex));
				isCheckSuccessful = false;
			}
		}
		for (Integer monthIndex : expensesFromYR.keySet()) {
			if (!Objects.equals(expensesFromYR.get(monthIndex), expensesFromMR.get(monthIndex))) {
				System.out.print("Несоответствие суммы расходов в " + MONTHS.get(monthIndex) + ": ");
				System.out.println("Расходы из годового отчета: " + expensesFromYR.get(monthIndex));
				System.out.println("Расходы из месячного отчета: " + expensesFromMR.get(monthIndex));
				isCheckSuccessful = false;
			}
		}
		if (isCheckSuccessful)
			System.out.println("Сверка успешно завершена!");
	}

	private static void printMenu() {
		System.out.println("Выберите команду: ");
		System.out.println("1 — Считать месячные отчеты.");
		System.out.println("2 — Считать годовой отчет.");
		System.out.println("3 — Вывести информацию о месячных отчетах.");
		System.out.println("4 — Вывести информацию о годовом отчете.");
		System.out.println("5 — Сверить отчеты.");
		System.out.println("0 — Выход из программы.");
	}
}
