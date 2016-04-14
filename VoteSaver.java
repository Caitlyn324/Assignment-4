import java.util.*;
import java.io.*;
import java.util.Scanner;


public class VoteSaver {
  static int[] votes;
  static ArrayList<String> votedOptions = new ArrayList<String>();
  static ArrayList<String> votesStrings = new ArrayList<String>();
  static File oldVotesFile;
  static int index;
  static PrintWriter voteWriter;
  static File tempVotesFile;
  static PrintWriter newVotesWriter;
  static Scanner tempVotesScanner;
  static int voteStringsSize;
  static Scanner votesScanner;

  public VoteSaver() {
    try {
      oldVotesFile = new File("votes.txt");
      if (oldVotesFile.exists()) {
        votesScanner = new Scanner(oldVotesFile);
        while(votesScanner.hasNextLine()) {
          String votesLine = votesScanner.nextLine();
          String[] votesSplit = votesLine.split(":");
          votedOptions.add(votesSplit[0]);
          votesStrings.add(votesSplit[1]);
        }
        voteStringsSize = voteStringsSize;
        for (int i = 0; i < voteStringsSize; i++) {
          String tempVoteString = votesStrings.get(i);
          votes[i] = Integer.parseInt(tempVoteString);
        }

      }
      tempVotesFile = new File("tempVotesFile.txt");
      voteWriter = new PrintWriter(tempVotesFile);
    } catch (FileNotFoundException e) {
      System.out.println("error: file not found"); }
    }

  public static void save(String passedOption) {
    String option = passedOption;
    int saveIndex = -1;
    System.out.println(voteStringsSize);
    if (votedOptions.contains(passedOption)) {
      for (int i = 0; i < votedOptions.size(); i++) {
        String currentOption = votedOptions.get(i);
        if ((currentOption).equals(passedOption)) {
          saveIndex = i;
          votes[saveIndex] += 1;
        }
      }
    }

  for (int i = 0; i < votedOptions.size(); i++)
    voteWriter.println(votedOptions.get(i) + ":" + votes[i]);

  }

  public static void castVoteFile() {
    try {
      voteWriter.close();
      if (oldVotesFile.exists()) {
        oldVotesFile.delete();
      }
      File newVotesFile = new File("votes.txt");
      newVotesWriter = new PrintWriter(newVotesFile);
      tempVotesScanner = new Scanner(tempVotesFile);
      while (tempVotesScanner.hasNextLine()) {
        newVotesWriter.print(tempVotesScanner.nextLine());
      }
      tempVotesFile.delete();
      newVotesWriter.close();
      tempVotesScanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("error: file REALLY not found");
    }

  }
}
