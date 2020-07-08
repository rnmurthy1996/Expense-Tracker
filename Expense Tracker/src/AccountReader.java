import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class AccountReader {

	public static ArrayList<User> userList;

	public AccountReader() {

	}

	/**
	 * The readAccountcsv method is used to populate userList with the current
	 * accounts from accountdata.csv.
	 */
	public static void readAccount() {

		try {
			// creating a new file object.
			File f = new File("accountdata.csv");
			Scanner fileScanner = new Scanner(f);
			userList = new ArrayList<User>();

			// looping through the file.
			while (fileScanner.hasNext()) {

				String line = fileScanner.next();

				// splitting the line to parse an account
				String[] lineArray = line.split(",");

				String username = lineArray[0];
				String password = lineArray[1];
				String expenses = lineArray[2];
				
				ArrayList<Expense>expenseList = new ArrayList<Expense>();
				
				if(!expenses.contentEquals("None")) {
					String[] expenseArr = expenses.split("_____");
					for(int i = 0; i < expenseArr.length; i++) {
						String[] nextExpense = expenseArr[i].split("---");
						String description = nextExpense[1].replace('*', ' ');
						description = description.replace('^', ',');
						String category = nextExpense[2].replace('*', ' ');
						category = category.replace('^', ',');
						expenseList.add(new Expense(nextExpense[0], description, category, Double.valueOf(nextExpense[3])));
						
					}
				}
				
				
				
				// Adding the account found in to array list
				User user = new User(username, password, expenses);
				user.setExpenseList(expenseList);
				
				userList.add(user);
			}
			fileScanner.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	/**
	 * The createAccountcsv method is used to create a new account and add it to
	 * accountdata.csv.
	 * 
	 * @param account Account the account that is being created
	 */
	public static void createUser(User u) {

		try {
			// Creating a new file object.
			File f = new File("accountdata.csv");
			Scanner sc = new Scanner(f);

			// Looping till the last line in the file.
			while (sc.hasNextLine()) {

				String line = sc.nextLine();

			}

			// Creating a print writer object.
			FileWriter fw = new FileWriter("accountdata.csv", true);
			PrintWriter p = new PrintWriter(fw);

			// Printing account details in the file.
			p.println();
			p.print(u.getUsername() + ",");
			p.print(u.getPassword() + ",");
			p.print(u.getExpenses() + ",");
			p.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * If any changes have been made to userList, the updateAccountDatabase
	 * method will go through the list and update accountdata.csv to reflect those
	 * changes.
	 */
	public static void updateAccountDatabase() {

		try {
			PrintWriter pr = new PrintWriter(new FileWriter("accountdata.csv"));
			for (int i = 0; i < userList.size(); i++) {
				// Printing the array list accounts in to the file.
				pr.println(userList.get(i).getUsername() + "," + userList.get(i).getPassword() + ","
						+ userList.get(i).getExpenses());

			}
			pr.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
