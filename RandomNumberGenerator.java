import java.util.Scanner;
import java.util.Random;
class RandomNumberGenerator
{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		Random rand = new Random();
		int guess, randNo, flag, range, check;
		int rounds = 0, correct = 0;
		int attempts = 5;
		do
		{
			check = 0;
			rounds++; //To keep track of the number of rounds
			System.out.println("------ Welcome to the Number Game ------");
			System.out.println("You have " + attempts + " attempts to guess the correct number");
			System.out.println("Hint: Number will be from 1 to 100");
			randNo = 1 + rand.nextInt(100); //Random number will be generated from 1 to 100
			for(int i = 1; i <= attempts; i++){
				System.out.println("\nAttempt " + i + ": Enter your guess");
				guess = sc.nextInt();
				if(guess == randNo){
					System.out.println("Hooray! You guessed it right!!!");
					correct++; //To keep track of the number of successful attempts
					check = 1; //Flag variable for successful attempts
					break;
				}
				else{
					System.out.println("Oops! Wrong guess!");
					range = randNo - guess;
					if(Math.abs(range) <= 5)
						System.out.println("You are close");
					else if(range < 0)
						System.out.println("Number entered is too high");
					else
						System.out.println("Number entered is too low");
				}
			}
			if(check == 0)
				System.out.println("\nSorry! You lose!\nThe number was " + randNo); //Random number will be displayed at the end
			System.out.println("\nEnter 1 to continue, 0 to stop");	
			flag = sc.nextInt(); //Flag variable to check if the user wants to continue the game
			if(flag != 1 && flag != 0){
				while(flag != 1 && flag != 0){
					System.out.println("\nPlease enter a valid number.\nEnter 1 to continue, 0 to stop");
					flag = sc.nextInt();
				}
			} //To ensure the user enters only 1 or 0
			System.out.println();
		}while(flag == 1);
		//Score displayed at the end
		System.out.println("Total attempts: " + rounds);
		System.out.println("No of rounds won: " + correct);
		System.out.println("\nThanks for playing!");
	}
}
