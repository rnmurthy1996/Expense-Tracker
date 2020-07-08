import java.util.ArrayList;

public class User {
	
	private String username;
	private String password;
	private ArrayList<Expense> expenseList;
	private String expenses;
	
	public User(String u, String p, String e) {
		username = u;
		password = p;
		expenses = e;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getExpenses() {
		return expenses;
	}
	
	public ArrayList<Expense> getExpenseList() {
		return expenseList;
	}
	
	public void setExpenseList(ArrayList<Expense> list) {
		expenseList = list;
	}
	
	public void addExpense(String d, String de, String c, double a) {
		Expense e = new Expense(d, de, c, a);
		expenseList.add(e);
	}
	
	public void setExpenses() {
		String s = "";
		for(int i = 0; i < expenseList.size(); i++) {
			s += expenseList.get(i).getDate() + "---" + expenseList.get(i).getDescription() + "---" + 
					expenseList.get(i).getCategory() + "---" + Double.toString(expenseList.get(i).getAmount()) + "_____";
		}
		s = s.replace(' ', '*');
		s = s.replace(',', '^');
		expenses = s;
	}
	
	public void setExpenses(String s) {
		expenses = s;
	}
	
	public boolean loginCheck(String u, String p) {
		if(username.contentEquals(u) && password.contentEquals(p)) {
			return true;
		}
		return false;
	}
}
