import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 * ExpenseTrackerGUI.java is used to handle the GUI for the main screen.
 * This GUI contains the buttons to access the AddExpense, RemoveExpense, and ViewStatistics GUIs.
 * It also has the JTextArea with all expenses for User a.
 */

public class ExpenseTrackerGUI {

	private JFrame frame;

	private JPanel accountInfo;
	private JPanel buttons;
	private JPanel area;

	private JLabel accountNumber;
	private JLabel accountName;
	private JLabel expDate;
	public static JLabel balance;
	private JLabel accountType;

	private JButton addExpense;
	private JButton removeExpense;
	private JButton viewStatistics;
	private JButton exit;
	public static JTextArea textArea;
	private JScrollPane scrollPane;
	private User a;

	public ExpenseTrackerGUI (User acc) {
		
		a = acc;
	}

	
	private void layoutManager() {

		frame = new JFrame("Expense Tracker");
		frame.setSize(650, 350);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.white);
		frame.setLayout(new FlowLayout());

		accountInfo = new JPanel();
		accountInfo.setLayout(new FlowLayout());
		accountInfo.setSize(100, 300);
		frame.add(accountInfo);

		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.setSize(50, 300);
		frame.add(buttons);
		textArea = new JTextArea(15, 55);
		scrollPane = new JScrollPane(textArea);

		area = new JPanel();
		area.add(scrollPane);
		area.setLayout(new FlowLayout());
		area.setSize(50, 300);
		frame.add(area);
	}

	
	public void createGui() {

		layoutManager();

		accountName = new JLabel("Username: " + a.getUsername() + "       ");

		accountInfo.add(accountName);

		addExpense = new JButton("Add Expense");
		removeExpense = new JButton("Remove Expense");
		viewStatistics = new JButton("View Statistics");
		exit = new JButton("Exit");

		buttons.add(addExpense);
		buttons.add(removeExpense);
		buttons.add(viewStatistics);
		buttons.add(exit);
		
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
		textArea.setText(s);
		textArea.setEditable(false);
		frame.setVisible(true);

		addExpense.addActionListener(new AddExpense());
		removeExpense.addActionListener(new RemoveExpense());
		viewStatistics.addActionListener(new ViewStatistics());
		exit.addActionListener(new Exit());
	}

	
	private class AddExpense implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			new AddExpenseGUI(a).createGui();
		}
	}

	
	private class RemoveExpense implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			new RemoveExpenseGUI(a).createGui();
		}
	}

	
	private class ViewStatistics implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			new ViewStatisticsGUI(a).createGui();
		}
	}

	
	private class Exit implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			System.exit(0);
		}
	}
}