import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;


public class RemoveExpenseGUI {

	private JFrame frame;
	private JPanel removals;
	private JPanel buttons;
	private JPanel incorrect;

	private JComboBox removeList;

	private JButton removeExpenseButton;
	private JButton exit;
	
	private JLabel Error;

	private User a;
	
	private HashMap<String, String> stringMatch = new HashMap<String, String>();

	public RemoveExpenseGUI(User acc) {

		a = acc;
	}

	/**
	 * The layoutManager method initializes all of the panels used by
	 * DepositGui.java.
	 */
	private void layoutManager() {

		frame = new JFrame("Expense Tracker");
		frame.setSize(650, 275);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.white);
		frame.setLayout(new FlowLayout());

		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.setSize(50, 300);
		frame.add(buttons);
		
		removals = new JPanel();
		removals.setLayout(new FlowLayout());
		removals.setSize(100, 300);
		frame.add(removals);
		
		incorrect = new JPanel();
		incorrect.setBackground(Color.white);
		incorrect.setLayout(new BoxLayout(incorrect, BoxLayout.Y_AXIS));
		incorrect.setSize(50, 200);
		frame.add(incorrect);

	}

	/**
	 * The createGui method is used to initialize the GUI which contains the
	 * required panels, labels, buttons, etc.
	 */
	public void createGui() {

		layoutManager();
		
		
		
		ArrayList<Expense>expenses = a.getExpenseList();
		String[] expenseArr = new String[expenses.size()];
		for(int i = 0; i < a.getExpenseList().size(); i++) {
			String s = "";
			s += "Date: " + a.getExpenseList().get(i).getDate() + "     " 
					+ "Description: " + a.getExpenseList().get(i).getDescription() + "     "
					+ "Category: " + a.getExpenseList().get(i).getCategory() + "     " 
					+ "Cost: $" + a.getExpenseList().get(i).getAmount();
			
			String x = Double.toString(a.getExpenseList().get(i).getAmount());
			String[] decCheck = x.split("\\.");
			if(decCheck[1].length() == 1) {
				s += "0";
			}
			expenseArr[i] = s;
			stringMatch.put(s, expenses.get(i).getDate() + "---" + expenses.get(i).getDescription() + "---" +
							expenses.get(i).getCategory() + "---" + Double.toString(expenses.get(i).getAmount()));
		}
		
		removeExpenseButton = new JButton("Remove Expense");
		exit = new JButton("Exit");

		buttons.add(removeExpenseButton);
		buttons.add(exit);
		
		removeList = new JComboBox(expenseArr);
		removals.add(removeList);
		
		Error = new JLabel("                                                                            ");
		incorrect.add(Error);
		Error.setAlignmentX(incorrect.CENTER_ALIGNMENT);

		frame.setVisible(true);

		removeExpenseButton.addActionListener(new RemoveExpense());
		exit.addActionListener(new Exit());
	}

	/**
	 * The private class DepositMoney is called when the depositButton Jbutton is
	 * clicked and deposits the user determined amount into the account. The deposit
	 * amount is checked in this class to ensure that it is a valid amount (no
	 * spaces, letters, negative values, etc.).
	 */
	private class RemoveExpense implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			if(removeList.getItemCount() > 0) {
				String choice = (String) removeList.getSelectedItem();
				String match = stringMatch.get(choice);
				String [] arr= match.split("---");
				for(int i = 0; i < a.getExpenseList().size(); i++) {
					if(a.getExpenseList().get(i).getDate().contentEquals(arr[0]) && a.getExpenseList().get(i).getDescription().contentEquals(arr[1]) &&
						a.getExpenseList().get(i).getCategory().contentEquals(arr[2]) && Double.toString(a.getExpenseList().get(i).getAmount()).equals(arr[3])) {
						a.getExpenseList().remove(i);
						break;
					}
				}
				if(a.getExpenseList().size() == 0) {
					a.setExpenses("None");
				}
				else {
					a.setExpenses();
				}
					
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
			
			else {
				Error.setText("There Are No Expenses Left To Remove.");
				Error.setForeground(Color.red);
			}
			
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