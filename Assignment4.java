import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;



public class Assignment4 {

  static ArrayList<Ballot> ballots = new ArrayList<Ballot>();
  static JButton login = new JButton("Login");
  static JButton castVotes = new JButton("Confirm Votes");
  static ArrayList<String> options = new ArrayList<String>();
  static ArrayList<String> buttonOptions = new ArrayList<String>();
  static VoteSaver voteSaver = new VoteSaver();
  static int votesIndex;
  static boolean[] status;

  //votes stuff
  static ArrayList<String> votedOptions = new ArrayList<String>();
  static ArrayList<String> votesStrings = new ArrayList<String>();
  static int[] votes;

  //voterIDWriter Stuff
  static ArrayList<String> voterIDArray = new ArrayList<String>();


  public static void main(String[] args) throws IOException {

		String filename = args[0];

		//Opens files for scanners
		File ballotFile = new File(filename);
		File voters = new File("voters.txt");
    File votesFile = new File("votes.txt");

    Scanner votesScanner = new Scanner(votesFile);
    while (votesScanner.hasNextLine()) {
      int counter = 0;
      String votedLine = votesScanner.nextLine();
      String[] lineSplitter = votedLine.split(":");
      votedOptions.add(lineSplitter[0]);
      votesStrings.add(lineSplitter[1]);
    }
    int voteSize = votesStrings.size();
    votes = new int[voteSize];
      for (int i = 0; i < votesStrings.size(); i++) {
        votes[i] = Integer.parseInt(votesStrings.get(i));
      }

      int numberOfBallots = 0;
      String numberOfBallotsString;

      String category;
      String ballotID;
      String optionsLine;


      Scanner ballotFileScanner = new Scanner(ballotFile);
      Scanner voterScanner = new Scanner(voters);

      numberOfBallotsString = ballotFileScanner.nextLine();
      numberOfBallots = Integer.parseInt(numberOfBallotsString);

      for (int i = 0; i < numberOfBallots; i++) {
        String line = ballotFileScanner.nextLine();                     //change to split
        Scanner lineScanner = new Scanner(line).useDelimiter(":");
        String ballotIDString = lineScanner.next();
        String position = lineScanner.next();
        String allOptions = lineScanner.next();

        Scanner optionsScanner = new Scanner(allOptions).useDelimiter(",");
        while (optionsScanner.hasNext()) {
          options.add(optionsScanner.next());
        }
        Ballot currentBallot = new Ballot(ballotIDString, position, options);
        ballots.add(currentBallot);
        if (i < numberOfBallots-1) {
        options.clear(); }

      }
      JFrame window = new JFrame("Vote");
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setLayout(new GridLayout(1,2));

      JPanel top = new JPanel();
      top.setLayout(new FlowLayout(FlowLayout.CENTER));

      JPanel votersWindow = new JPanel();
      votersWindow.setLayout(new BoxLayout(votersWindow, BoxLayout.Y_AXIS));


      ArrayList<String> voterLines = new ArrayList<String>();
      int z = 0;
      while (voterScanner.hasNextLine()) {
        String curentVoterLine = voterScanner.nextLine();
        voterLines.add(curentVoterLine);
        System.out.println(voterLines.get(z));
        z++;
      }

      ArrayList<String> voterIDs = new ArrayList<String>();
      ArrayList<String> voterNames = new ArrayList<String>();
      status = new boolean[voterLines.size()];



      for (int i = 0; i < voterLines.size(); i++) {
        Scanner voterLineScanner = new Scanner(voterLines.get(i));

        String line = voterLineScanner.nextLine();
        String[] splitLine = line.split(":");

        voterIDs.add(splitLine[0]);
        voterNames.add(splitLine[1]);
        String loopStatus = splitLine[2];
        System.out.println(loopStatus);
        }

        System.out.println(voterNames.size());

        for (int q = 0; q < voterIDs.size(); q++) {
          System.out.println(voterIDs.get(q) + "     " + voterNames.get(q) + "     " + status[q]);
        }

        for (int i = 0; i < options.size(); i++) {
          System.out.println(options.get(i));
        }

      login.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Brings up input dialog that asks for user ID
					String voterIDActionListener = JOptionPane.showInputDialog("Enter Your Voter ID: ");
            String userInput = voterIDActionListener;
            voterIDArray.add(userInput);
            System.out.println(voterIDActionListener);
            int voterIndex = -1;

            for (int x = 0; x < voterIDs.size(); x++) {
              if (userInput.equals(voterIDs.get(x)))
                voterIndex = x;
            }

            System.out.println(voterIndex);

            if (voterIDs.contains(voterIDActionListener) && status[voterIndex]==false) {
							//Enables all Buttons
							for(int j=0; j<ballots.size();j++) {
								ballots.get(j).enableButtons();
							}
							//Greets the Voter
							JOptionPane.showMessageDialog(window, "Hello " + voterNames.get(voterIndex) + "\nPlease Make Your Choices");
							login.setEnabled(false); //Temporariliy disables the login button
							castVotes.setEnabled(true); //Enables the Cast Vote Button
						}
            else if (voterIDs.contains(voterIDActionListener) && status[voterIndex]==true) {
              JOptionPane.showMessageDialog(window, "Sorry, " + voterNames.get(voterIndex) + ", You've Already Voted!");
            }

            else {
              JOptionPane.showMessageDialog(window, "Sorry, You Haven't Registered To Vote!");
            }
            status[voterIndex] = true;

				}
			}
		); //End of AddActionListener;

		//Adds ActionListener for Cast Vote Button
		castVotes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(window, "Thank You, Your Vote Has Been Recorded");
					for(int i=0;i<ballots.size();i++)	{
						//Gets String from castVote method and writes it to votes file
						votesIndex = ballots.get(i).castVote();
            String passSave = options.get(votesIndex);
            voteSaver.save(passSave);
            status[votesIndex] = true;
					}
          voteSaver.castVoteFile();
					castVotes.setEnabled(false);
					login.setEnabled(true);
				}
			});//End of addActionListener



      for(int i = 0; i < ballots.size(); i++) {
  			window.add(ballots.get(i));
  		}

      window.add(votersWindow);
      window.add(top);


  		top.add(login, BorderLayout.WEST);
  		castVotes.setEnabled(false);
  		top.add(castVotes, BorderLayout.EAST);

  		window.pack();
  		window.setLayout(new GridLayout(2,(ballots.size())));
  		window.setVisible(true);

  }
}
