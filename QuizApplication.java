import java.util.Scanner;
import java.time.LocalTime;
class QuizTimer extends Thread //Separate thread to accept answers, terminates after specified time ends.
{
    Scanner sc;
    public static char entered;
    volatile boolean running;
    QuizTimer()
    {
        sc = new Scanner(System.in);
        running = true;
        entered = ' ';
        start();
    }
    public void run()
    {
        entered = ' ';
        try
        {
            while(running){
                if(System.in.available() > 0){
                    entered = sc.nextLine().charAt(0);
                    end();
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Method couldn't execute!");
        }
    }
    public void end() //Stops the thread when user doesn't enter anything within the specified time limit.
    {
        running = false;
    }
}
class QuizApplication
{
    public static int evaluate(char x) //Function to accept answer within specified time and check if it's correct. Returns 1 if the correct option is entered, else 0.
    {
        int score = 0;
        System.out.println("Enter your option");
        LocalTime current = LocalTime.now();
        QuizTimer timer = new QuizTimer();
        while(LocalTime.now().isBefore(current.plusSeconds(10)) && QuizTimer.entered == ' ')
            continue;
        timer.end();
        if(QuizTimer.entered == ' ')
            System.out.println("Time\'s up!!!");
        if(Character.toUpperCase(QuizTimer.entered) == x)
            score++;
        return score;
    }
    public static void main(String args[])
    {
        int total = 10, score = 0;
        System.out.println("********** General Knowledge Trivia Quiz **********");
        System.out.println("There will be a total of " + total + " questions, of MCQ type. You have 10 seconds to answer each question. Enter the correct option only(A, B, C or D). Good Luck !!!");
        System.out.println("\n1. The tallest status in the world \"The Statue of Unity\" is located near which dam?\nA) Bhakra Nangal Dam\nB) Sardar Sarovar Dam\nC) Tehri Dam\nD) Hirakud Dam");
        score += evaluate('B');
        System.out.println("\n2. Which Article of the Indian Constitution gives the President the power of pardoning?\nA) Article 72\nB) Article 73\nC) Article 74\nD) Article 75");
        score += evaluate('A');
        System.out.println("\n3. Which is the largest producer of copper in India?\nA) Jharkhand\nB) Bihar\nC) Madhya Pradesh\nD) Rajasthan");
        score += evaluate('C');
        System.out.println("\n4. Which one of the following gases is considered as atmospheric pollutant?\nA) Oxygen\nB) Ozone\nC) Sulphur dioxide\nD) Nitrogen");
        score += evaluate('C');
        System.out.println("\n5. Which of the following rivers of India crosses the Tropic of Cancer twice?\nA) Mahi\nB) Chambal\nC) Narmada\nD) Yamuna");
        score += evaluate('A');
        System.out.println("\n6. In which Indian state is the Flamingo festival celebrated?\nA) Rajasthan\nB) Assam\nC) Manipur\nD) Andhra Pradesh");
        score += evaluate('D');
        System.out.println("\n7. Who among the following performing artists bagged the best actress award for Meena Gurjari in 1975?\nA) Sonal Masingh\nB) Sitara Devi\nC) Shovana Narayana\nD) Mallika Sarabhai");
        score += evaluate('D');
        System.out.println("\n8. The author of world famous Harry Potter series is\nA) Arundhati Roy\nB) J K Rowling\nC) Taslima Nasrin\nD) Salman Rushdie");
        score += evaluate('B');
        System.out.println("\n9. Fatehpur Sikri was founded as the capital of the Mughal Empire by\nA) Babur\nB) Humayun\nC) Jahangir\nD) Akbar");
        score += evaluate('D');
        System.out.println("\n10. Who was the court poet of Samudragupta?\nA) Banabhatta\nB) Harishen\nC) Chand Bardai\nD) Bhavabhuti");
        score += evaluate('B');
        System.out.println("\nYour score: " + score + " / " + total + "\nCorrect attempts: " + score + "\nIncorrect attempts (or unattempted): " + (total - score));
        System.out.println("\nThanks for trying out!!!");
    }
}