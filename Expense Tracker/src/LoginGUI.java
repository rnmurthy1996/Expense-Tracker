import javax.swing.JLabel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 * LoginGUI.java is used to handle the GUI for the login screen.
 * This GUI contains the options to either login or create a new account.
 * It also authenticates the username and password fields before loggin in.
 */


public class LoginGUI {

	private JFrame frame;

	private JPanel un;
	private JPanel pw;
	private JPanel buttons;
	private JPanel incorrect;

	private JLabel username;
	private JTextField usernameText;
	private JLabel password;
	private JTextField passwordText;

	private JButton login;
	private JButton createAccount;
	private JButton exit;

	private JLabel incorrectMessage;

	public static User workingAccount;
	
	private void layoutManager() {

		frame = new JFrame("Bank Application");
		frame.setSize(300, 175);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.white);
		frame.setLayout(new FlowLayout());

		un = new JPanel();
		un.setLayout(new FlowLayout());
		un.setSize(50, 200);
		frame.add(un);

		pw = new JPanel();
		pw.setLayout(new FlowLayout());
		pw.setSize(50, 200);
		frame.add(pw);

		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.setSize(50, 200);
		frame.add(buttons);

		incorrect = new JPanel();
		incorrect.setBackground(Color.white);
		incorrect.setLayout(new FlowLayout());
		incorrect.setSize(50, 200);
		frame.add(incorrect);
	}

	public void createGui() {

		layoutManager();

		username = new JLabel("Username:");
		password = new JLabel("Password:");
		usernameText = new JTextField();
		passwordText = new JPasswordField();
		usernameText.setColumns(10);
		passwordText.setColumns(10);

		un.add(username);
		un.add(usernameText);
		pw.add(password);
		pw.add(passwordText);

		login = new JButton("Login");
		createAccount = new JButton("Create Account");
		exit = new JButton("Exit");

		buttons.add(login);
		buttons.add(createAccount);
		buttons.add(exit);

		incorrectMessage = new JLabel("                                  ");
		incorrectMessage.setAlignmentX(incorrect.CENTER_ALIGNMENT);
		incorrect.add(incorrectMessage);

		frame.setVisible(true);

		login.addActionListener(new Login());
		createAccount.addActionListener(new CreateAccount());
		exit.addActionListener(new Exit());
	}

	private class Login implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			String user = usernameText.getText();
			String pass = passwordText.getText();
			
			
			AccountReader.readAccount();

			for (int i = 0; i < AccountReader.userList.size(); i++) {
				if (AccountReader.userList.get(i).loginCheck(user, pass) == true) {
					workingAccount = AccountReader.userList.get(i);

					new ExpenseTrackerGUI(AccountReader.userList.get(i)).createGui();

					frame.dispose();
				}
			}

			incorrectMessage.setText("Incorrect Username or Password");
			incorrectMessage.setForeground(Color.red);
		}
	}

	
	private class CreateAccount implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			new CreateAccountGUI().createGui();
			frame.dispose();
		}
	}

	
	private class Exit implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			frame.dispose();
		}
	}
	
}