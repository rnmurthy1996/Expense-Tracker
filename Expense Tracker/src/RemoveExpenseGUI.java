import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;


/**
 * RemoveExpenseGUI.java is used to handle the GUI for removal of logged expenses.
 * This GUI contains the dropdown menu of all current expenses which User a can select from to remove.
 */


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

	private class Exit implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			frame.dispose();
		}
	}

}