import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.text.NumberFormat;

public class Quiz {

  public static void main(String[] args) throws IOException {
    String fileName = args[0];                                       //reads fileName from the commandline
    File fileInput = new File(fileName);                             //creates a File object based on the fileName
    Scanner fileScanner = new Scanner(fileInput);                    //creates a scanner object for input
    Scanner userInput = new Scanner(System.in);
    ArrayList<Question> questionList = new ArrayList<Question>();    //creates an ArrayList to hold the questions
    int correctAnswers = 0;
    int wrongAnswers = 0;
    int attempt = -1;
    int answer = -1;
    NumberFormat percentify = NumberFormat.getPercentInstance();
    double[] overallPercentages;
    int playerResponse = -1;

    while (fileScanner.hasNextLine()) {                 //create questions and add them to the ArrayList. Continue until the end of the text file.
      Question question = new Question(fileScanner);
      questionList.add(question);
    }
    fileScanner.close();

    int numQuestions = questionList.size();
    int[] playerAnswers = new int[numQuestions];        //creates an array to hold the player answers


    overallPercentages = new double[numQuestions];      //create an array to hold the correct percentages for all the questions
    fileScanner.close();

    for (int i = 0; i < numQuestions; i++) {            //A for loop to actually answer the questions
      questionList.get(i).askQuestion();                //for each question, ask the question!
      try {
        System.out.print("Your Answer (enter a number): ");
        playerResponse = userInput.nextInt();
      }
      catch (InputMismatchException e) {
        System.out.print("Invalid Input! Your Answer (enter a number): ");
        userInput.next();
        playerResponse = userInput.nextInt();
      }
      playerAnswers[i] = playerResponse;
      System.out.println("Player Answer Number: " + playerAnswers[i]);
      answer = questionList.get(i).getAnswer();
      questionList.get(i).increaseTimesTried();

      if (answer == playerAnswers[i]) {
        questionList.get(i).increaseTimesCorrect();
        correctAnswers++;
      }
      else {
        wrongAnswers++;
      }
      System.out.println(questionList.get(i).getAnswer());
    }

  /*****************************************************
    Grading Portion
      1. Transition
      2. Player Questions (For each question)
        a. Display Question
        b. Display Answer
        c. Display Player Answer
        d. Respond
      3. Display Overall Performance
      4. Cumulative Statistics (for each question)
        a. Display Question
        b. Display Times Tried
        c. Display Times Correct
        d. Percentage Correct
   ****************************************************/

   System.out.println("Thanks for your answers!\nHere are your results:");                                    // 1. Transition

   for (int j = 0; j < numQuestions; j++) {                                                                   // 2. Player Questions (For each question)
     System.out.println(questionList.get(j).getAnswer());
     System.out.println("Question: " + questionList.get(j).getQuestion());
     System.out.println(" Correct Answer: " + questionList.get(j).getAnswerString());
     System.out.println(" Your Answer: " + questionList.get(j).getPlayerResponseString(playerAnswers[j]));
     if (questionList.get(j).getAnswer() == playerAnswers[j]) {
       System.out.println(" Correct! Nice Job!"); }
      else {
        System.out.println("  Sorry, Better Luck Next Time!"); }
     }

    System.out.println("Your Overall Performance: ");                //3. Display Overall Performance
    System.out.println("  Correct: " + correctAnswers);
    System.out.println("  Wrong: " + wrongAnswers);

    System.out.println("Here's where you stack up against everyone else!");

    for (int k = 0; k < numQuestions; k++) {
      System.out.println("Question: " + questionList.get(k).getQuestion());
      System.out.println("  Times Tried: " + questionList.get(k).getTimesTried());
      System.out.println("  Times Correct: " + questionList.get(k).getTimesCorrect());
       double perc=  questionList.get(k).getTimesCorrect() / questionList.get(k).getTimesTried();
      overallPercentages[k] = perc;
      System.out.println("  Percent Correct: " + percentify.format(overallPercentages[k]));
    }

    double[] newOverallPercentages = selectionSort(overallPercentages);
    int easiestQuestion = newOverallPercentages.length - 1;
    int hardestQuestion = 0;

    System.out.println("Easiest Question: ");
    System.out.println("  Question: " + questionList.get(easiestQuestion).getQuestion());
    System.out.println("    Times Tried: " + questionList.get(easiestQuestion).getTimesTried());
    System.out.println("    Times Correct: " + questionList.get(easiestQuestion).getTimesCorrect());
    System.out.println("    Percent Correct: " + percentify.format(overallPercentages[easiestQuestion]));

    System.out.println("Hardest Question: ");
    System.out.println("  Question: " + questionList.get(hardestQuestion).getQuestion());
    System.out.println("    Times Tried: " + questionList.get(hardestQuestion).getTimesTried());
    System.out.println("    Times Correct: " + questionList.get(hardestQuestion).getTimesCorrect());
    System.out.println("    Percent Correct: " + percentify.format(overallPercentages[hardestQuestion]));

    PrintWriter out = new PrintWriter(fileName);

    for (int l = 0; l < numQuestions; l++) {
      questionList.get(l).writeQuestions(out);
    }
    out.close();

   }

  public static double[] selectionSort(double[] arr){

        for (int i = 0; i < arr.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[index])
                    index = j;

            double smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;
        }
        return arr;
    }

  }
