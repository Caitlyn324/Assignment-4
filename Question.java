import java.io.*;
import java.util.*;
import java.lang.*;

public class Question {

  String question;              //hold the question
  String[] options;             //hold the options for possible Answers
  int optionsSize;              //determine the size of the options ArrayList
  int answer;                   //designates the answer
  double timesTried;               //holds the number of times a given question was tried
  double timesCorrect;             //holds the number of times a given question was tried
  String stringOptionsSize;     //String version of optionsSize
  String stringAnswer;          //String version of answer
  String stringTimesTried;      //String version of timesTried
  String stringTimesCorrect;    //String version of timesCorrect


  public Question(Scanner scannerObject) throws IOException {
    question = scannerObject.nextLine();      //read the next line from the text file and store it in question
    stringOptionsSize = scannerObject.nextLine();    //reads the next int from the text file and stores it in the size of the options array
    optionsSize = Integer.parseInt(stringOptionsSize);
    options = new String[optionsSize];        //makes options the size of possible answers
    for (int i = 0; i < optionsSize; i++) {   //stores the same number of lines as optionSize in the options array
      options[i] = scannerObject.nextLine();
    }
    stringAnswer = scannerObject.nextLine();              //reads the next line from the text file and sets the answer to it.
    answer = Integer.parseInt(stringAnswer);              //converts into int
    stringTimesTried = scannerObject.nextLine();          //reads the next int from the text file and sets the times tried to it
    timesTried = Double.parseDouble(stringTimesTried);      //converts into int
    stringTimesCorrect = scannerObject.nextLine();        //reads the next int from the text file and sets the times correctly answered to it
    timesCorrect = Double.parseDouble(stringTimesCorrect);  //converts into int
  }

  public void printQuestionData() {
    System.out.println("Question: " + question);
    System.out.println("Answer: " + answer);
    System.out.println("Times Tried: " + timesTried);
    System.out.println("Times : " + timesCorrect);
  }

  public void askQuestion() {               //a method to print the question
    System.out.print("Question: ");
    System.out.println(question);
    System.out.println("  Answers:");
    for (int i = 0; i < optionsSize; i++) {
      int optionNumber = i + 1;
      System.out.println("    " + optionNumber + ": " + options[i]);
    }
  }

  public void writeQuestions(PrintWriter fileName) {
    fileName.println(question);
    fileName.println(stringOptionsSize);
    for (int i = 0; i < options.length; i++) {
      fileName.println(options[i]);
    }
    fileName.println(answer);
    fileName.println(timesTried);
    fileName.println(timesCorrect);
  }

  public int getAnswer() {                              //answer getter
    return answer;
  }

  public String getQuestion() {                              //answer getter
    return question;
  }

  public String getAnswerString() {
      String answerString = options[answer-1];
      return answerString;
  }

  public String getPlayerResponseString(int playerResponse) {
    return options[playerResponse-1];
  }

  public double getTimesTried() {                          //timesTried getter
    return timesTried;
  }

  public double getTimesCorrect() {                        //timesCorrect getter
    return timesCorrect;
  }

  public void increaseTimesTried() {        //timesTried setter
    timesTried++;
  }

  public void increaseTimesCorrect() {   //timesCorrect setter
    timesCorrect++;
  }

}
