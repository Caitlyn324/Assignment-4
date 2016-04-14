import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import javax.swing.BoxLayout;



public class Ballot extends JPanel {


  protected JPanel ballotPanel = new JPanel();
  protected JLabel ballotName;
  protected JButton[] optionButtons;


  String position;
  String allOptions;
  String line;
  String ballotID;
  String resultingVote;
  ArrayList<String> options = new ArrayList<String>();

  int[] votes;
  int numberOfOptions = 0;
  int passedOptionsSize;
  int votedIndex = -1;

  public Ballot(String passedID, String passedPosition, ArrayList<String> passedOptions) throws IOException {

    ballotID = passedID;

    position = passedPosition;
    ballotName = new JLabel(position, SwingConstants.CENTER);

    passedOptionsSize = passedOptions.size();
    for (int i = 0; i < passedOptionsSize; i++) {
      options.add(passedOptions.get(i));
    }

    votes = new int[passedOptionsSize];

    for (int i = 0; i < passedOptionsSize; i++) {
      votes[i] = 0;
    }

    add(ballotPanel);


    ballotPanel.setLayout(new GridLayout(options.size()+1,1));
    ballotPanel.add(ballotName);

    optionButtons = new JButton[passedOptionsSize];

    for(int i = 0; i < optionButtons.length; i++) {
			optionButtons[i] = new JButton(options.get(i));
			optionButtons[i].setName(options.get(i));
			optionButtons[i].addActionListener(new ButtonListener());
			ballotPanel.add(optionButtons[i]);
			optionButtons[i].setEnabled(false);
		}

    ballotPanel.setVisible(true);
}

  public int castVote() {
    for(int i = 0; i < passedOptionsSize; i++) {
      if(optionButtons[i].getForeground() == Color.RED) {
        votedIndex = i;
        votes[i] += 1;
      }
    }
    return votedIndex;
  }

  //Method that Enables all Buttons
  public void enableButtons() {
    for(int i = 0; i < optionButtons.length; i++) {
      optionButtons[i].setEnabled(true);
    }
  }

  public void saveBallotFile() {
    try {
      File voterFile = new File(votedIndex + ".txt");
      PrintWriter ballotWriter = new PrintWriter(voterFile);
      for (int i = 0; i < passedOptionsSize; i++) {
        ballotWriter.println(options.get(i) + ":" + votes[i]);
      }
      ballotWriter.close();
    } catch (FileNotFoundException e) {
      System.out.println("error: file totally not found :/");
    }
  }

  class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {

      JButton listenerButton = (JButton)e.getSource();
			for(int i = 0; i < optionButtons.length; i++)
			{
				optionButtons[i].setForeground(Color.BLACK);
			}
			//Changes the pressed button to have red text
			listenerButton.setForeground(Color.RED);      }
  }


}
