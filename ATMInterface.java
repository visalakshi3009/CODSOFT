/* ATM Interface, Note: Works in Terminal only since Console class is used for PIN. 
PIN won't be displayed while entering, but will be saved. */
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.Console;
class InvalidPINException extends Exception
{
	InvalidPINException()
	{
		super("PIN should be exactly four digits only!");
	}
} //Exception thrown when PIN contains more than 4 digits, or contains non- numeric characters
class InsufficientBalanceException extends Exception
{
	InsufficientBalanceException(double x)
	{
		super("Account has minimum balance. Cannot withdraw Rs. " + x);
	}
} //Exception thrown when amt to be withdrawn exceeds minimum balance(500)
class InvalidACException extends Exception
{
	InvalidACException()
	{
		super("Account number should contain 11 digits");
	}
} //Exception thrown when number contains more than 11 digits, or contains non- numeric characters
class BankAccount
{
	private long acno;
	private double balance;
	private int pin;
	Console console;
	Scanner sc;
	BankAccount(long ac, double bal)
	{
		acno = ac;
		balance = bal;
		console = System.console();
		sc = new Scanner(System.in);
		boolean valid = false; 
		while(!valid)
		{
			try{
				setPin();
				valid = true;
			}
			catch(InvalidPINException e)
			{
				System.out.println(e.getMessage());
			}
		} //while loop continues until user enters valid PIN
	}
	void setPin() throws InvalidPINException
	{
		System.out.println("\nEnter new PIN");
		char[] pinstring = console.readPassword();
		for(int i = 0; i < 4; i++)
			System.out.print("*");
		System.out.println();
		String str = new String(pinstring);
		if(pinstring.length != 4 || !(str.matches("\\d{4}")))
			throw new InvalidPINException();
		pin = Integer.parseInt(str);
		System.out.println("Pin set successfully");
	} //method to set PIN
	boolean checkPin() throws InvalidPINException
	{
		int wattempts = 3;
		int entered;
		boolean correct;
		for(int j = 1; j <= wattempts; j++){
			correct = false;
			System.out.println("\nEnter current PIN");
			char[] pinstring = console.readPassword();
			for(int i = 0; i<4; i++)
				System.out.print("*");
			System.out.println();
			String str = new String(pinstring);			
			if(pinstring.length != 4 || !(str.matches("\\d{4}"))){
				System.out.println("Incorrect PIN: " + (wattempts - j) + " attempts remaining!");
				continue;
			}
			entered = Integer.parseInt(str);
			if(entered == pin)
				return true;
			else
				System.out.println("Incorrect PIN: " + (wattempts - j) + " attempts remaining!");
		}
		return false;
	} //method to check if PIN is valid
	void changePin()
	{
		boolean check;
		boolean valid = false;
		while(!valid)
		{
			try{
				check = checkPin();
				if(check == false){
					System.out.println("Maximum limit for wrong attempts exceeded, Process terminated!");
					System.exit(0);
				}
				setPin();
				valid = true;
			}
			catch(InvalidPINException e)
			{
				System.out.println(e.getMessage());
			}
		} //while loop continues until user enters valid PIN
	}
	// get and set methods for private members
	double getBalance()
	{
		return balance;
	}
	void setBalance(double b)
	{
		balance = b;
	}
	long getAC()
	{
		return acno;
	}
}
class ATM
{
	void withdraw(BankAccount b, double a) throws InsufficientBalanceException
	{
		boolean check;
		boolean valid = false;
		while(!valid)
		{
			try{
				check = b.checkPin();
				if(check == false){
					System.out.println("Maximum limit for wrong attempts exceeded, Process terminated!");
					System.exit(0);
				}
				valid = true;
			}
			catch(InvalidPINException e)
			{
				System.out.println(e.getMessage());
			}
		}
		if(b.getBalance() - a < 500)
			throw new InsufficientBalanceException(a);
		b.setBalance(b.getBalance() - a);
		System.out.println("Rs. " + a + " withdrawn successfully. Remaining balance: Rs. " + b.getBalance());
	} //method to withdraw amount
	void deposit(BankAccount b, double a)
	{
		boolean check;
		boolean valid = false;
		while(!valid)
		{
			try{
				check = b.checkPin();
				if(check == false){
					System.out.println("Maximum limit for wrong attempts exceeded, Process terminated!");
					System.exit(0);
				}
				valid = true;
			}
			catch(InvalidPINException e)
			{
				System.out.println(e.getMessage());
			}
		}
		b.setBalance(b.getBalance() + a);
		System.out.println("Rs. " + a + " deposited successfully. Current balance: Rs. " + b.getBalance());
	} //method to deposit amount
	void checkBalance(BankAccount b)
	{
		boolean check;
		boolean valid = false;
		while(!valid)
		{
			try{
				check = b.checkPin();
				if(check == false){
					System.out.println("Maximum limit for wrong attempts exceeded, Process terminated!");
					System.exit(0);
				}
				System.out.println("Current balance is Rs. " + b.getBalance());
				valid = true;
			}
			catch(InvalidPINException e)
			{
				System.out.println(e.getMessage());
			}
		}
	} //method to check balance
}
class ATMInterface
{
	public static void main(String args[]) throws InvalidACException
	{
		Scanner sc = new Scanner(System.in);
		ATM atm = new ATM();
		int flag = 1;
		int choice;
		long acno;
		String ac = new String();
		double amt;
		BankAccount b;
		boolean valid = false;
		System.out.println("----- ATM Interface -----");
		while(!valid)
		{
			try{
				System.out.println("\nEnter account number");
				ac = sc.nextLine();
				if(ac.length() != 11 || !(ac.matches("\\d{11}")))
					throw new InvalidACException();
				valid = true;
			}
			catch(InvalidACException e)
			{
				System.out.println(e.getMessage());
			}
		} //while loop continues until user enters valid account number
		acno = Long.parseLong(ac);
		System.out.println("Enter current balance");
		amt = sc.nextDouble();
		b = new BankAccount(acno, amt);
		//Menu- driven interface
		do
		{
			System.out.println("\n1.Deposit amount\n2.Withdraw amount\n3.Display account details\n4.Change PIN\n5.Exit\n");
			System.out.println("Enter your choice");
			choice = sc.nextInt();
			switch(choice)
			{
				case 1:
					System.out.println("Enter amount to be deposited");
					amt = sc.nextDouble();
					atm.deposit(b, amt);
					break;
				case 2:
					System.out.println("Enter amount to be withdrawn");
					amt = sc.nextDouble();
					try{
						atm.withdraw(b, amt);
					}
					catch(InsufficientBalanceException e)
					{
						System.out.println(e);
					}
					break;
				case 3:
					System.out.println("Account Number: " + b.getAC());
					atm.checkBalance(b);
					break;
				case 4:
					b.changePin();
					break;
				case 5:
					flag = 0;
					break;
				default:
					System.out.println("Enter a valid choice");
			}
		}while(flag == 1);
		System.out.println("Thank you!");
	}
}
