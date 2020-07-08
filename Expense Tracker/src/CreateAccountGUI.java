import javax.swing.JLabel;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 * CreateAccountGUI.java is used handle the creation of new Users.
 * It makes sure to check all the fields for this GUI are properly filled before adding the User.
 */


public class CreateAccountGUI {

	private JFrame frame;
	private JPanel un;
	private JPanel pw;
	private JPanel buttons;
	private JPanel incorrect;
	private JPanel accType;
	private JPanel id;

	private JLabel username;
	private JTextField usernameText;
	private JLabel password;
	private JTextField passwordText;

	private JButton login;
	private JButton createAccount;
	private JButton exit;

	private JLabel usernameErr;
	private JLabel passwordErr;
	private JLabel depositErr;

	
	private void layoutManager() {

		frame = new JFrame("Bank Application");
		frame.setSize(300, 190);
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
		incorrect.setLayout(new BoxLayout(incorrect, BoxLayout.Y_AXIS));
		incorrect.setSize(50, 200);
		frame.add(incorrect);
	}

	
	public void createGui() {

		layoutManager();

		username = new JLabel("Create Username:");
		password = new JLabel("Create Password:");
		usernameText = new JTextField();
		passwordText = new JPasswordField();
		usernameText.setColumns(10);
		passwordText.setColumns(10);

		un.add(username);
		un.add(usernameText);
		pw.add(password);
		pw.add(passwordText);

		createAccount = new JButton("Create Account");
		exit = new JButton("Exit");

		buttons.add(createAccount);
		buttons.add(exit);

		usernameErr = new JLabel("                                  ");
		usernameErr.setAlignmentX(incorrect.CENTER_ALIGNMENT);
		incorrect.add(usernameErr);
		passwordErr = new JLabel("                                  ");
		passwordErr.setAlignmentX(incorrect.CENTER_ALIGNMENT);
		incorrect.add(passwordErr);
		depositErr = new JLabel("                                  ");
		depositErr.setAlignmentX(incorrect.CENTER_ALIGNMENT);
		incorrect.add(depositErr);

		frame.setVisible(true);

		createAccount.addActionListener(new CreateAccount());
		exit.addActionListener(new Exit());
	}

	
	private class CreateAccount implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			String name = "";
			String pw = "";
			double balance = 0;

			boolean taken = false;
			AccountReader check = new AccountReader();
			check.readAccount();
			for (int i = 0; i < check.userList.size(); i++) {
				if (usernameText.getText().equals(check.userList.get(i).getUsername())) {

					usernameErr.setText("Username Is Already Taken");
					usernameErr.setForeground(Color.red);
					taken = true;
				}
			}
			if (usernameText.getText().isEmpty() == true) {
				usernameErr.setText("Username Field Cannot Be Empty");
				usernameErr.setForeground(Color.red);
			} else if (userNameCheck(usernameText.getText()) == false) {
				usernameErr.setText("Username Cannot Contain Spaces");
				usernameErr.setForeground(Color.red);
			} else if (taken == false) {
				name = usernameText.getText();
				usernameErr.setText("                                  ");
			}

			if (passwordText.getText().isEmpty() == true) {
				passwordErr.setText("Password Field Cannot Be Empty");
				passwordErr.setForeground(Color.red);
			} else {
				pw = passwordText.getText();
				passwordErr.setText("                                  ");
			}

			if (!(name.equals("")) && !(pw.equals("")) ) {
				
				User u = new User(name, pw, "None");
				AccountReader ar = new AccountReader();
				ar.createUser(u);
				frame.dispose();
				new LoginGUI().createGui();
			}
		}
	}

	
	private class Exit implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			frame.dispose();
			new LoginGUI().createGui();
		}
	}

	
	public boolean userNameCheck(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				return false;
			}
		}
		return true;
	}

}
