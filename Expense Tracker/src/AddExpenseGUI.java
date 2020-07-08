import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 * AddExpenseGUI.java is used handle the GUI for adding any new expenses to User a.
 * It makes sure to check all the fields for this GUI are properly filled before adding the expense.
 */


public class AddExpenseGUI {

	private JFrame frame;
	private JPanel date;
	private JPanel description;
	private JPanel category;
	private JPanel cost;
	private JPanel buttons;
	private JPanel incorrect;

	private JLabel dateLabel;
	private JComboBox dateMonth;
	private JLabel slash1;
	private JComboBox dateDay;
	private JLabel slash2;
	private JComboBox dateYear;
	private JLabel descriptionLabel;
	private JTextField descriptionText;
	private JLabel categoryLabel;
	private JComboBox categoryType;
	private JLabel costLabel;
	private JTextField dollarsText;
	private JLabel dot;
	private JTextField centsText;

	private JButton addExpenseButton;
	private JButton exit;

	private JLabel desError;
	private JLabel addError;

	private User a;

	public AddExpenseGUI(User acc) {

		a = acc;
	}

	
	private void layoutManager() {

		frame = new JFrame("Expense Tracker");
		frame.setSize(350, 265);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.white);
		frame.setLayout(new FlowLayout());

		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.setSize(50, 300);
		frame.add(buttons);
		
		date = new JPanel();
		date.setLayout(new FlowLayout());
		date.setSize(100, 300);
		frame.add(date);

		description = new JPanel();
		description.setLayout(new FlowLayout());
		description.setSize(100, 300);
		frame.add(description);
		
		category = new JPanel();
		category.setLayout(new FlowLayout());
		category.setSize(100, 300);
		frame.add(category);
		
		cost = new JPanel();
		cost.setLayout(new FlowLayout());
		cost.setSize(100, 300);
		frame.add(cost);

		incorrect = new JPanel();
		incorrect.setBackground(Color.white);
		incorrect.setLayout(new BoxLayout(incorrect, BoxLayout.Y_AXIS));
		incorrect.setSize(50, 200);
		frame.add(incorrect);
	}

	
	public void createGui() {

		layoutManager();

		dateLabel = new JLabel("Date:");
		slash1 = new JLabel("/");
		slash2 = new JLabel("/");
		
		String[] months = { "1", "2", "3","4", "5", "6", "7", "8", "9", "10", "11", "12" };
		dateMonth = new JComboBox(months);
		String[] days = { "1", "2", "3","4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
					"16", "17", "18", "19", "20", "20", "21", "22", "23","24", "25", "26", "27", "28", "29", "30", "31"};
		dateDay = new JComboBox(days);
		
		String[] years = { "2020", "2019", "2018","2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", 
					"2009", "2008", "2007", "2006","2005", "2004", "2003", "2002", "2001", "2000"};
		dateYear = new JComboBox(years);
		
		date.add(dateLabel);
		date.add(dateMonth);
		date.add(slash1);
		date.add(dateDay);
		date.add(slash2);
		date.add(dateYear);

		descriptionLabel = new JLabel("Description:");
		descriptionText = new JTextField();
		descriptionText.setColumns(20);
		description.add(descriptionLabel);
		description.add(descriptionText);
		
		categoryLabel = new JLabel("Category:");
		String[] categories = { "Housing", "Transportation", "Food","Utilities", "Insurance", "Medical & Healthcare", 
							"Savings & Investing", "Personal Spending", "Recreation & Entertainment", 
							"Miscellaneous"};
		categoryType = new JComboBox(categories);
		category.add(categoryLabel);
		category.add(categoryType);
		
		
		costLabel = new JLabel("Cost: $");
		dollarsText = new JTextField();
		dollarsText.setColumns(7);
		dot = new JLabel(".");
		centsText = new JTextField();
		centsText.setColumns(3);
		centsText.setDocument(new JTextFieldLimit(2));

		cost.add(costLabel);
		cost.add(dollarsText);
		cost.add(dot);
		cost.add(centsText);

		addExpenseButton = new JButton("Add Expense");
		exit = new JButton("Exit");

		buttons.add(addExpenseButton);
		buttons.add(exit);

		desError = new JLabel("                                                                            ");
		addError = new JLabel("                                                                            ");
		incorrect.add(desError);
		incorrect.add(addError);
		addError.setAlignmentX(incorrect.CENTER_ALIGNMENT);
		desError.setAlignmentX(incorrect.CENTER_ALIGNMENT);

		frame.setVisible(true);

		addExpenseButton.addActionListener(new AddExpense());
		exit.addActionListener(new Exit());
	}

	
	private class AddExpense implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			double cost = 0;
			String expenseDescription = "";

			if (dollarsText.getText().isEmpty() == true) {
				addError.setText("Deposit Amount Field Cannot Be Empty");
				addError.setForeground(Color.red);
			} else if (depositPosCheck(dollarsText.getText()) == false) {
				addError.setText("Deposit Amount Must Be Positive");
				addError.setForeground(Color.red);
			} else if (depositCheck(dollarsText.getText()) == false) {
				addError.setText("Deposit Amount Must Be Numerical");
				addError.setForeground(Color.red);
			} else if (centsText.getText().isEmpty() == true) {
				addError.setText("Deposit Amount Field Cannot Be Emptyy");
				addError.setForeground(Color.red);
			} else if (depositPosCheck(centsText.getText()) == false) {
				addError.setText("Deposit Amount Must Be Positive");
				addError.setForeground(Color.red);
			} else if (depositCheck(centsText.getText()) == false) {
				addError.setText("Deposit Amount Must Be Numerical");
				addError.setForeground(Color.red);
			} else {
				if (centsText.getText().length() == 1) {
					cost = Double.parseDouble(dollarsText.getText())
							+ Double.parseDouble(centsText.getText()) / 10.00;
					addError.setText("                                                                            ");
				} else {
					cost = Double.parseDouble(dollarsText.getText())
							+ Double.parseDouble(centsText.getText()) / 100.00;
					addError.setText("                                                                            ");
				}
			}

			String removeWS = descriptionText.getText().replaceAll("\\s+","");
			if(removeWS.contentEquals("")) {
				desError.setText("Description Field Cannot Be Empty");
				desError.setForeground(Color.red);
			}
			else {
				expenseDescription = descriptionText.getText();
				desError.setText("                                                                            ");
			}
				
				
			if (cost > 0 && !(expenseDescription.equals(""))) {
				String d = (String) dateMonth.getSelectedItem() + "/" + (String) dateDay.getSelectedItem()
							+ "/" + (String) dateYear.getSelectedItem();
				String de = descriptionText.getText();
				String c = (String) categoryType.getSelectedItem();
				a.addExpense(d, de, c, cost);
				
				a.setExpenses();
				AccountReader.updateAccountDatabase();
				frame.dispose();
				String s = "";
				for(int i = 0; i < a.getExpenseList().size(); i++) {
					s += "Date: " + a.getExpenseList().get(i).getDate() + "     " 
							+ "Description: " + a.getExpenseList().get(i).getDescription() + "     "
							+ "Category: " + a.getExpenseList().get(i).getCategory() + "     " 
							+ "Cost: $" + a.getExpenseList().get(i).getAmount();
					
					String x = Double.toString(a.getExpenseList().get(i).getAmount());
					String[] decCheck = x.split("\\.");
					if(decCheck[1].length() == 1) {
						s += "0";
					}
					s+="\n";
					
				}
				new ExpenseTrackerGUI(a).textArea.setText(s);
				
			}
			
		}
	}

	
	private class Exit implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			frame.dispose();
		}
	}

	public boolean depositCheck(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public boolean depositPosCheck(String s) {
		if ((s.substring(0, 1)).equals("-")) {
			return false;
		}
		return true;
	}
}