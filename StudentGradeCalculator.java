import java.util.Scanner;
class InvalidMarksException extends Exception
{
	InvalidMarksException(int x)
	{
		super(x + " is not a valid score. Marks should be from 0 to 100");
	}
}
class StudentGradeCalculator
{
	public static int sum(int[] arr)
	{
		int sum = 0;
		for(int x: arr)
			sum += x;
		return sum;
	}
	public static void calculateGrade(double avg)
	{
		if(avg >= 90)
			System.out.println("Grade obtained: A+");
		else if(avg >= 80)
			System.out.println("Grade obtained: A");
		else if(avg >= 70)
			System.out.println("Grade obtained: B");
		else if(avg >= 60)
			System.out.println("Grade obtained: C");
		else if(avg >= 50)
			System.out.println("Grade obtained: D");
		else if(avg >= 40)
			System.out.println("Grade obtained: E");
		else
			System.out.println("Grade obtained: F");
	}
	public static void main(String args[]) throws InvalidMarksException
	{
		Scanner sc = new Scanner(System.in);
		int n, total;
		double avg;
		System.out.println("-----Student Grade Calculator-----");
		System.out.println("\nEnter the number of subjects");
		n = sc.nextInt();
		int[] marks = new int[n];
		for(int i = 0; i<n; i++){
			System.out.println("Enter the marks in subject " + (i + 1));
			try{
				marks[i] = sc.nextInt();
				if(marks[i] < 0 || marks[i] > 100)
					throw new InvalidMarksException(marks[i]);
			}
			catch(InvalidMarksException e){
				System.out.println(e);
				i--;
			}
		}
		total = sum(marks);
		avg = (double)(total)/marks.length;
		System.out.println("\nTotal marks: " + total);
		System.out.println("Average percentage: " + String.format("%.2f", avg) + "%");
		calculateGrade(avg);
	}
}		
