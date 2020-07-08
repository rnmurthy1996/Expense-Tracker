import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class ViewStatisticsGUI {

	private JFrame frame;
	private JPanel date1;
	private JPanel date2;
	private JPanel statistics;
	private JPanel buttons;

	private JLabel startdateLabel;
	private JComboBox startdateMonth;
	private JLabel slash1;
	private JComboBox startdateDay;
	private JLabel slash2;
	private JComboBox startdateYear;
	
	private JLabel enddateLabel;
	private JComboBox enddateMonth;
	private JLabel slash3;
	private JComboBox enddateDay;
	private JLabel slash4;
	private JComboBox enddateYear;
	
	
	private JLabel statisticsResults;

	private JButton runAnalysis;
	private JButton exit;

	private User a;

	public ViewStatisticsGUI(User acc) {

		a = acc;
	}

	/**
	 * The layoutManager method initializes all of the panels used by
	 * DepositGui.java.
	 */
	private void layoutManager() {

		frame = new JFrame("Expense Tracker");
		frame.setSize(300, 425);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.white);
		frame.setLayout(new FlowLayout());

		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.setSize(50, 300);
		frame.add(buttons);
		
		date1 = new JPanel();
		date1.setLayout(new FlowLayout());
		date1.setSize(100, 300);
		frame.add(date1);
		
		date2 = new JPanel();
		date2.setLayout(new FlowLayout());
		date2.setSize(100, 300);
		frame.add(date2);

		statistics = new JPanel();
		statistics.setLayout(new FlowLayout());
		statistics.setSize(100, 300);
		frame.add(statistics);
	}

	/**
	 * The createGui method is used to initialize the GUI which contains the
	 * required panels, labels, buttons, etc.
	 */
	public void createGui() {

		layoutManager();

		runAnalysis = new JButton("Analyze Spending");
		exit = new JButton("Exit");

		buttons.add(runAnalysis);
		buttons.add(exit);
		
		startdateLabel = new JLabel("Start Date:");
		slash1 = new JLabel("/");
		slash2 = new JLabel("/");
		
		String[] months = { "1", "2", "3","4", "5", "6", "7", "8", "9", "10", "11", "12" };
		startdateMonth = new JComboBox(months);
		String[] days = { "1", "2", "3","4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
					"16", "17", "18", "19", "20", "20", "21", "22", "23","24", "25", "26", "27", "28", "29", "30", "31"};
		startdateDay = new JComboBox(days);
		
		String[] years = { "2020", "2019", "2018","2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", 
					"2009", "2008", "2007", "2006","2005", "2004", "2003", "2002", "2001", "2000"};
		startdateYear = new JComboBox(years);
		
		date1.add(startdateLabel);
		date1.add(startdateMonth);
		date1.add(slash1);
		date1.add(startdateDay);
		date1.add(slash2);
		date1.add(startdateYear);
		
		
		enddateLabel = new JLabel("End Date:");
		slash3 = new JLabel("/");
		slash4 = new JLabel("/");
		
		enddateMonth = new JComboBox(months);
		enddateDay = new JComboBox(days);
		enddateYear = new JComboBox(years);
		
		date2.add(enddateLabel);
		date2.add(enddateMonth);
		date2.add(slash3);
		date2.add(enddateDay);
		date2.add(slash4);
		date2.add(enddateYear);

		statisticsResults = new JLabel("Spending Statistics:");
		statistics.add(statisticsResults);

		
		frame.setVisible(true);

		runAnalysis.addActionListener(new RunAnalysis());
		exit.addActionListener(new Exit());
	}

	/**
	 * The private class DepositMoney is called when the depositButton Jbutton is
	 * clicked and deposits the user determined amount into the account. The deposit
	 * amount is checked in this class to ensure that it is a valid amount (no
	 * spaces, letters, negative values, etc.).
	 */
	private class RunAnalysis implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			double moneySpent = 0;
			int numExpenses = 0;
			
			/*"Housing", "Transportation", "Food","Utilities", "Insurance", "Medical & Healthcare", 
			"Savings & Investing", "Personal Spending", "Recreation & Entertainment", 
			"Miscellaneous"*/
			
			double housing = 0;
			double transport = 0;
			double food = 0;
			double utilities = 0;
			double insurance = 0;
			double medical = 0;
			double savings = 0;
			double personal = 0;
			double rec = 0;
			double misc = 0;
			
			ArrayList<Expense>rangeList = new ArrayList<Expense>();
			for(int i = 0; i < a.getExpenseList().size(); i++) {
				String date = a.getExpenseList().get(i).getDate();
				String[] split = date.split("/");
				int month = Integer.valueOf(split[0]);
				int day = Integer.valueOf(split[1]);
				int year = Integer.valueOf(split[2]);
				
				int startMonth = Integer.valueOf((String) startdateMonth.getSelectedItem());
				int startDay = Integer.valueOf((String) startdateDay.getSelectedItem());
				int startYear = Integer.valueOf((String) startdateYear.getSelectedItem());
				
				int endMonth = Integer.valueOf((String) enddateMonth.getSelectedItem());
				int endDay = Integer.valueOf((String) enddateDay.getSelectedItem());
				int endYear = Integer.valueOf((String) enddateYear.getSelectedItem());
				
				boolean startCheck = false;
				if(year > startYear) {
					startCheck = true;
				}
				else if(year == startYear) {
					if(month > startMonth) {
						startCheck = true;
					}
					else if(month == startMonth) {
						if(day >= startDay) {
							startCheck = true;
						}
					}
				}
				
				boolean endCheck = false;
				if(year < startYear) {
					endCheck = true;
				}
				else if(year == startYear) {
					if(month < startMonth) {
						endCheck = true;
					}
					else if(month == startMonth) {
						if(day <= startDay) {
							endCheck = true;
						}
					}
				}
				
				if(startCheck && endCheck) {
					rangeList.add(a.getExpenseList().get(i));
				}
				
			}
			
			for(int i = 0; i < rangeList.size(); i++) {
				numExpenses++;
				moneySpent += a.getExpenseList().get(i).getAmount();
				if(a.getExpenseList().get(i).getCategory().contentEquals("Housing")) 
					housing += a.getExpenseList().get(i).getAmount();
				if(a.getExpenseList().get(i).getCategory().contentEquals("Transportation")) 
					transport += a.getExpenseList().get(i).getAmount();
				if(a.getExpenseList().get(i).getCategory().contentEquals("Food")) 
					food += a.getExpenseList().get(i).getAmount();
				if(a.getExpenseList().get(i).getCategory().contentEquals("Utilities")) 
					utilities += a.getExpenseList().get(i).getAmount();
				if(a.getExpenseList().get(i).getCategory().contentEquals("Insurance")) 
					insurance += a.getExpenseList().get(i).getAmount();
				if(a.getExpenseList().get(i).getCategory().contentEquals("Medical & Healthcare")) 
					medical += a.getExpenseList().get(i).getAmount();
				if(a.getExpenseList().get(i).getCategory().contentEquals("Savings & Investing")) 
					savings += a.getExpenseList().get(i).getAmount();
				if(a.getExpenseList().get(i).getCategory().contentEquals("Personal Spending")) 
					personal += a.getExpenseList().get(i).getAmount();
				if(a.getExpenseList().get(i).getCategory().contentEquals("Recreation & Entertainment")) 
					rec += a.getExpenseList().get(i).getAmount();
				if(a.getExpenseList().get(i).getCategory().contentEquals("Miscellaneous")) 
					misc += a.getExpenseList().get(i).getAmount();
				
			}
			
			/*"Housing", "Transportation", "Food","Utilities", "Insurance", "Medical & Healthcare", 
			"Savings & Investing", "Personal Spending", "Recreation & Entertainment", 
			"Miscellaneous"*/
			
			String s = "<html>Spending Statistics:<br/>";
			s += "Number of Expenses: " + Integer.toString(numExpenses) + "<br/>";
			
			String x = Double.toString(moneySpent);
			String[] decCheck = x.split("\\.");
			if(decCheck[1].length() == 1) {
				x = decCheck[0] + "." + decCheck[1] + "0";
			}
			else if(decCheck[1].length() > 2) {
				x = decCheck[0] + "." + decCheck[1].substring(0,2);
			}
			s += "Total Spending: $" + x + "<br/><br/>";
			
			s += "Spending By Category:" + "<br/>";
			
			x = Double.toString(housing);
			decCheck = x.split("\\.");
			if(decCheck[1].length() == 1) {
				x = decCheck[0] + "." + decCheck[1] + "0";
			}
			else if(decCheck[1].length() > 2) {
				x = decCheck[0] + "." + decCheck[1].substring(0,2);
			}
			s += "Housing: $" + x + "<br/>";
			
			x = Double.toString(transport);
			decCheck = x.split("\\.");
			if(decCheck[1].length() == 1) {
				x = decCheck[0] + "." + decCheck[1] + "0";
			}
			else if(decCheck[1].length() > 2) {
				x = decCheck[0] + "." + decCheck[1].substring(0,2);
			}
			s += "Transportation: $" + x + "<br/>";
			
			x = Double.toString(food);
			decCheck = x.split("\\.");
			if(decCheck[1].length() == 1) {
				x = decCheck[0] + "." + decCheck[1] + "0";
			}
			else if(decCheck[1].length() > 2) {
				x = decCheck[0] + "." + decCheck[1].substring(0,2);
			}
			s += "Food: $" + x + "<br/>";
			
			x = Double.toString(utilities);
			decCheck = x.split("\\.");
			if(decCheck[1].length() == 1) {
				x = decCheck[0] + "." + decCheck[1] + "0";
			}
			else if(decCheck[1].length() > 2) {
				x = decCheck[0] + "." + decCheck[1].substring(0,2);
			}
			s += "Utilities: $" + x + "<br/>";
			
			x = Double.toString(insurance);
			decCheck = x.split("\\.");
			if(decCheck[1].length() == 1) {
				x = decCheck[0] + "." + decCheck[1] + "0";
			}
			else if(decCheck[1].length() > 2) {
				x = decCheck[0] + "." + decCheck[1].substring(0,2);
			}
			s += "Insurance: $" + x + "<br/>";
			
			x = Double.toString(medical);
			decCheck = x.split("\\.");
			if(decCheck[1].length() == 1) {
				x = decCheck[0] + "." + decCheck[1] + "0";
			}
			else if(decCheck[1].length() > 2) {
				x = decCheck[0] + "." + decCheck[1].substring(0,2);
			}
			s += "Medical & Healthcare: $" + x + "<br/>";
			
			x = Double.toString(savings);
			decCheck = x.split("\\.");
			if(decCheck[1].length() == 1) {
				x = decCheck[0] + "." + decCheck[1] + "0";
			}
			else if(decCheck[1].length() > 2) {
				x = decCheck[0] + "." + decCheck[1].substring(0,2);
			}
			s += "Savings & Investing: $" + x + "<br/>";
			
			x = Double.toString(personal);
			decCheck = x.split("\\.");
			if(decCheck[1].length() == 1) {
				x = decCheck[0] + "." + decCheck[1] + "0";
			}
			else if(decCheck[1].length() > 2) {
				x = decCheck[0] + "." + decCheck[1].substring(0,2);
			}
			s += "Personal Spending: $" + x + "<br/>";
			
			x = Double.toString(rec);
			decCheck = x.split("\\.");
			if(decCheck[1].length() == 1) {
				x = decCheck[0] + "." + decCheck[1] + "0";
			}
			else if(decCheck[1].length() > 2) {
				x = decCheck[0] + "." + decCheck[1].substring(0,2);
			}
			s += "Recreation & Entertainment: $" + x + "<br/>";
			
			x = Double.toString(misc);
			decCheck = x.split("\\.");
			if(decCheck[1].length() == 1) {
				x = decCheck[0] + "." + decCheck[1] + "0";
			}
			else if(decCheck[1].length() > 2) {
				x = decCheck[0] + "." + decCheck[1].substring(0,2);
			}
			s += "Miscellaneous: $" + x + "<html>";
			
			statisticsResults.setText(s);
		}
	}

	/**
	 * The private class Exit is called when the exit Jbutton is clicked and closes
	 * the deposit money GUI. This class also initializes a new home page GUI with
	 * an updated account balance.
	 */
	private class Exit implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			frame.dispose();
		}
	}

	/**
	 * The userNameCheck method checks to see if the username entered by the user
	 * contains any spaces.
	 * 
	 * @param s the username that is being checked for spaces.
	 * @return true if the username does not contain any spaces and false otherwise.
	 */
	public boolean userNameCheck(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				return false;
			}
		}
		return true;
	}

	/**
	 * The depositCheck method checks to see if the deposit entered by the user only
	 * contains numbers.
	 * 
	 * @param s the deposit that is being checked for non-numerical values.
	 * @return true if the deposit only contains numerical values and false
	 *         otherwise.
	 */
	public boolean depositCheck(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * The depositPosCheck method checks to see if the deposit is positive.
	 * 
	 * @param s the deposit that is being checked to see if it is a positive value.
	 * @return true if the deposit is positive and false otherwise.
	 */
	public boolean depositPosCheck(String s) {
		if ((s.substring(0, 1)).equals("-")) {
			return false;
		}
		return true;
	}
}