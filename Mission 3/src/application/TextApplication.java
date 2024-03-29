package application;
/**
 * @author Jungsu Kim
 * @Classname: TextApplication
 * @Description: This class gets other class "Party", "Poll", "PollList", and "Factory" and implements them together
 */
/*
 *
 * Exception handling done by: Kenneth Liu 30066722
 * 2020/08/07, TextApplication updated to handle data input error
 */
import java.util.Scanner;

import model.Factory;
import model.InvalidPartyDataException;
import model.InvalidSetupDataException;
import model.Party;
import model.Poll;
import model.PollList;
import java.lang.NumberFormatException;
public class TextApplication {
	public static final int MAX_NUMBER_OF_STARS = 25;
	private PollList polls;
	String[] parties;

	int seats = 0;
	Factory factory = new Factory(seats);
	int numPolls = 0;
	String partyInput;
	Scanner userInput = new Scanner(System.in);
	/**
	 * Title: TextApplication
	 * Description: This a default constructor of the class
	 */
	public TextApplication() {
	}

	/**
	 * Title: TextApplication
	 * Description: Another constructor which initializes the PollList object "Polls"
	 * @param polls
	 */
	public TextApplication(PollList polls) {
		this.polls = polls;
	}

	/**
	 * Title: displayPollBySeat
	 * Description: This displays each poll and the parties included, they are arranged by seats
	 * @param partyNames
	 */
	public void displayPollsBySeat(String[] partyNames) {
		for(Poll a : polls.getPolls()) {
			displayPollDataBySeat(a);
		}
	}

	/**
	 * Title: PollList getPolls
	 * Getter method
	 * Description: Gets the PollList object "Polls"
	 * @return polls
	 */
	public PollList getPolls() {
		return polls;
	}

	/**
	 * Title: displayPollDataBySeat
	 * Description: Takes in poll as an argument and displays each party and arranges them by seat
	 * @param aPoll
	 */
	public void displayPollDataBySeat(Poll aPoll) {
		int starsNeededForMajority = MAX_NUMBER_OF_STARS/2 + 1;
		System.out.println(aPoll.getPollName());
		for (Party poll : aPoll.getPartiesSortedBySeats()) {
			System.out.println(poll.textVisualizationBySeats(MAX_NUMBER_OF_STARS, starsNeededForMajority, this.polls.getNumOfSeats()/MAX_NUMBER_OF_STARS));
		}
		System.out.println("\n");
	}

	/**
	 * @author kenneth liu 30066722
	 * Title: promptSeats
	 * Description: Prompts user for seat information, will reprompt if user enters invalid data
	 * invalid data is either negative number or not a number at all
	 *
	 */
	public void promptSeats() {
		try { //try block for handling errors for seats
			System.out.println("How many seats are available in the election? (Enter an Integer value greater than 0)");
			String userSeats = userInput.nextLine();
			seats = Integer.parseInt(userSeats);
			factory = new Factory(seats);

			// if seats is not a positive integer, throw an InvalidSetUpDataException
			if(seats > 0) {
				factory = new Factory(seats);
			}else {
				throw new InvalidSetupDataException();
			}

		}catch(InvalidSetupDataException ISDE){					//catch the invalid setup data exception and reprompt user to enter the number
			System.out.println("Invalid number of seats! You must enter an integer greater than 0!");
			System.out.println();
			promptSeats();

		}
		catch(NumberFormatException e) {						//if user inputs anything other than a number, reprompt user
			System.out.println("Invalid number! You must enter a integer");
			System.out.println();
			promptSeats();

		}
	}
	/**
	 * @author kenneth liu 30066722
	 * Title: promptParties
	 * Description: Prompts user for party names separated by a comma, converts them into an array of type String split by a comma.
	 * Will reprompt user if the user does not enter anything meaningful characters
	 * Reprompts if user just hits space or enter
	 */
	public void promptParties() {
		try { 		//try block for adding party names to poll
			System.out.println("\nWhich parties are in the election (provide names, separate by comma): ");
			String partyInput = userInput.nextLine().trim();
			if(partyInput.isEmpty() == false ) {		//if user input is empty throw InvalidSetUpDataException, otherwise split party names into array
				String[] parties = partyInput.split(",");
				factory.setPartyNames(parties);
			}else {
				throw new InvalidSetupDataException();
			}

		} catch(InvalidSetupDataException ISDE) {		//reprompt user for party data
			System.out.println("Invalid party list! Nothing was entered! ");
			System.out.println();
			promptParties();
		}

	}
	/**
	 * @author kenneth liu 30066722
	 * Title: promptPolls
	 * Description: prompts user for number of polls, will reprompt if user enters
	 * negative number or a non int value.
	 */
	public void promptPolls() {
		try {		//try block for prompting user for number of polls
			System.out.print  ("\nHow many polls do you want to track with this application? (Enter an integer value greater than 0) ");
			String userNumPolls = userInput.nextLine();
			numPolls = Integer.parseInt(userNumPolls);
			if(numPolls <= 0) {		//if user input is less than 0 throw InvalidSetUpDataException
				throw new InvalidSetupDataException();
			}

		}
		catch(InvalidSetupDataException ISDE){		//catch block to reprompt user for # of polls

			System.out.println("Invalid number of polls! An integer less than 1 is entered!");
			System.out.println();
			promptPolls();


		}catch(NumberFormatException NFE) {			//catch block if user input is not an integer value, reprompt user for data

			System.out.println("You didn't enter an integer!");
			System.out.println();
			promptPolls();
		}
	}



	/**
	 * Title: Run
	 * Description: This body of code is to initializes all the methods made above and creates an application allowing user input and output
	 * @throws InvalidSetupDataException 
	 */
	public void run() throws InvalidSetupDataException  {


		System.out.println("Welcome to the poll tracker!");

		promptSeats();


		promptParties();


		promptPolls();

		System.out.print  ("\nWould you like to create a random set of polls? yes or no ");
		String randomPoll = userInput.nextLine();

		/*
		 * user will be reprompted if choice is not yes or no
		 * done by Kenneth Liu
		 */
		if (randomPoll.equalsIgnoreCase("yes")) {
			try {
				polls = new PollList(numPolls, seats);
			}catch(InvalidSetupDataException e){
				System.out.println(e.getMessage());
			}
			
			int counter = 0;
			while (counter < numPolls) {
				try {
					polls.addPoll(factory.createRandomPoll("Poll " + counter));
				} catch (InvalidPartyDataException e) {
					System.out.println(e.getMessage());
				}
				counter ++;
			}
			System.out.println("A random set of polls will be created");
			System.out.println();
		}
		else if (randomPoll.equalsIgnoreCase("no")) {
			
				try {
					polls = factory.promptForPollList(numPolls);
				} catch (Throwable e) {
					System.out.println(e.getMessage());
				}
					//prompt for poll list was not done in factory
			System.out.println("A random set of polls will not be created");
			System.out.println();
		}
		else {
			do {
				System.out.println("Invalid Selection!");
				System.out.print  ("\nWould you like to create a random set of polls? yes or no ");
				randomPoll = userInput.nextLine();
				if (randomPoll.equalsIgnoreCase("yes")) {
					polls = new PollList(numPolls, seats);
					int counter = 0;
					while (counter < numPolls) {
						try {
							polls.addPoll(factory.createRandomPoll("Poll " + counter));
						} catch (InvalidPartyDataException e) {
							System.out.println(e.getMessage());
						}
						counter ++;
					}
					System.out.println("A random set of polls will be created");
					System.out.println();
				}
				else if (randomPoll.equalsIgnoreCase("no")) {
					try {
						polls = factory.promptForPollList(numPolls);
					} catch (Throwable e) {
						System.out.println(e.getMessage());
					}
					System.out.println("A random set of polls will not be created");
					System.out.println();
				}
			}while(randomPoll.equalsIgnoreCase("yes") == false && randomPoll.equalsIgnoreCase("no") == false);
		}

		String option = "";
		int counter = 0;
		while(counter == 0) {
			System.out.println("Options: all (show result of all polls), aggregate (show aggregate result), quit (end application)");
			System.out.println("Choose an option: ");
			option = userInput.nextLine();
			if (option.equalsIgnoreCase("all")) {
				displayPollsBySeat(parties);
			}
			if (option.equalsIgnoreCase("aggregate")) {
				System.out.println("Aggregate Poll:");
				System.out.println();
				displayPollDataBySeat(polls.getAggregatePoll(factory.getPartyNames()));
			}
			if (option.equalsIgnoreCase("quit")) {
				counter ++;
			}
		}
		userInput.close();
	}

	/**
	 * Title: main
	 * Description: runs the java code and initializes the TextApplication class 
	 * @param args
	 * @throws InvalidSetupDataException 
	 * @throws InvalidSetUpDataException
	 */
	public static void main(String[] args) throws InvalidSetupDataException {
		TextApplication app = new TextApplication(null);
		try {
			app.run();
		}catch(InvalidSetupDataException e) {
			System.out.println(e.getMessage());
		}
		

	}
}