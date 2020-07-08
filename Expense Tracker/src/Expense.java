/**
 * Expense.java is used to define an Expense and the variables required for an Expense.
 */


public class Expense {

	private String date;
	private String description;
	private String category;
	private double amount;
	
	public Expense(String d, String de, String c, double a) {
		date = d;
		description = de;
		category = c;
		amount = a;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getCategory() {
		return category;
	}
	
	public double getAmount() {
		return amount;
	}
}
