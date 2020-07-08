import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * AccountReader.java is used to populate userList with current users, 
 * add new users to accountdata.csv, and update accountdata.csv with any changes to current users.
 */


public class AccountReader {

	public static ArrayList<User> userList;

	public AccountReader() {

	}

	public static void readAccount() {

		try {
			File f = new File("accountdata.csv");
			Scanner fileScanner = new Scanner(f);
			userList = new ArrayList<User>();

			while (fileScanner.hasNext()) {

				String line = fileScanner.next();

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
				
				
				
				User user = new User(username, password, expenses);
				user.setExpenseList(expenseList);
				
				userList.add(user);
			}
			fileScanner.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	
	public static void createUser(User u) {

		try {
			File f = new File("accountdata.csv");
			Scanner sc = new Scanner(f);

			while (sc.hasNextLine()) {

				String line = sc.nextLine();

			}

			FileWriter fw = new FileWriter("accountdata.csv", true);
			PrintWriter p = new PrintWriter(fw);

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

	
	public static void updateAccountDatabase() {

		try {
			PrintWriter pr = new PrintWriter(new FileWriter("accountdata.csv"));
			for (int i = 0; i < userList.size(); i++) {
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
