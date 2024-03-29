/**
  * @ClassName: PollList
  * @Description:  represents a list of polls
  * @author Angus Milne 30071887
  * @date July 16, 2020
  */
public class PollList {
	private Poll[] polls;
	private int numOfSeats;

	/**
	  * Title: PollList
	  * @Description: PollList constructor taking number of polls needed and number of total seats as arguments
	  * @param numOfPolls
	  * @param numOfSeats
	  */
	public PollList(int numOfPolls, int numOfSeats) {
		if (numOfPolls >= 1) {
		polls = new Poll[numOfPolls];
		}
		else {
			polls = new Poll[5];

		}
		if (numOfSeats >=1) {
		this.numOfSeats = numOfSeats;
		}

		else {
			this.numOfSeats = 10;
		}

	}
	/**
	  * Title: getNumOfSeats
	  * @Description: returns the number of seats available in the election
	  */
	public int getNumOfSeats() {
		return numOfSeats;
	}
	/**
	  * Title: getPolls
	  * @Description: returns the poll list
	  */
	public Poll[] getPolls() {
		return polls;
	}

	/**
	  * Title: addPoll
	  * @Description: adds a poll to the poll list taking the poll to be added as an argument
	  * @param aPoll
	  */
	public void addPoll(Poll aPoll) {
		System.out.println("aPoll   "+aPoll);
		if (aPoll != null) {
		boolean already = false;
		int full = 0;
		// testing to see if the poll already exists in the list and if the list is full
		for (int index = 0; index < polls.length && polls[index] != null && already != true; index++) {
			String pollName = polls[index].getPollName();
			System.out.println("poll name    "+pollName);
			if (pollName.equalsIgnoreCase(aPoll.getPollName())) {
				already = true;
			}
			full++;
		}
		System.out.println("already     " + already);
		//if the poll doesn't exist and the list is not full, add the poll to the end of the list
		if (already == true) {
			polls[full-1] = aPoll;
		}
		else {

		if (full < polls.length) {
			// if the list is not full add the new poll to the end of the list
			polls[full] = aPoll;

		}
		// if the list is full, print error message saying the list is full
		else if (full == polls.length) {
			//if the list is full and no duplicate it present, print an error message
			System.out.println("List is full, no further polls can be added.");

		}

		}
	}
		// if aPoll is empty, print error message and do not change Poll list
		else
			System.out.println("Poll empty, no changes will be made.");

		}


	/**
	  * Title: getAggregatePoll
	  * @Description: makes a poll named Aggregate where it's elements are the adjusted totals of all the parties
	  * @param partyNames
	  */
	public Poll getAggregatePoll(String[] partyNames){
		float totalSeats = 0;
		float totalPercent = 0;
		//make a new aggregate poll named "Aggregate"
		Poll Aggregate = new Poll("Aggregate", partyNames.length);
		for (int index = 0; index < partyNames.length; index++) {
			Aggregate.addParty(getAveragePartyData(partyNames[index]));
		}
		System.out.println("Hello");

		for (int index = 0; index < Aggregate.getNumberOfParties(); index++) {
			float seats = Aggregate.getParty(partyNames[index]).getProjectedNumberOfSeats();
			float percent = Aggregate.getParty(partyNames[index]).getProjectedPercentageOfVotes();
			System.out.println(seats + "     seats");
			totalSeats = totalSeats + seats;
			totalPercent = totalPercent + percent;
			System.out.println(totalSeats + "    total number of seats");
		}
		if (totalSeats > this.numOfSeats) {
			float proportion = this.numOfSeats/totalSeats;
			System.out.println(proportion + "     proportion");
			for (int index = 0; index < Aggregate.getNumberOfParties(); index++) {
			Aggregate.getParty(partyNames[index]).setProjectedNumberOfSeats(Aggregate.getParty(partyNames[index]).getProjectedNumberOfSeats()*proportion);
			System.out.println(Aggregate.getParty(partyNames[index]).getProjectedNumberOfSeats());
		}
		}
		if (totalPercent > 1) {
			float proportionPercent = (float)1.0/totalPercent;
			for (int index =0; index <Aggregate.getNumberOfParties(); index++) {
				Aggregate.getParty(partyNames[index]).setProjectedPercentageOfVotes(Aggregate.getParty(partyNames[index]).getProjectedPercentageOfVotes()*proportionPercent);
			}
		}
		return Aggregate;
	}
	/**
	  * Title: getAveragePartyData
	  * @Description: takes a party name as an argument and adds the total number of seats and total percentage of votes of all polls in PollList
	  * @param partyName
	  */
	public Party getAveragePartyData(String partyName) {
		Party party = new Party(partyName);
		//expected number of seats

		float numberSeats = 0;
		float percentageOfVotesTotal = 0;
		float length = 0;
		for (int index = 0;index < polls.length ;index++) {
			//inside polls getParty from party class --> argument party name
			// how to get the projected number of seats to the party created
			Party partyObject = polls[index].getParty(partyName);
			if (partyObject != null) {
			float projectedNumberSeats = partyObject.getProjectedNumberOfSeats();
			numberSeats = numberSeats + projectedNumberSeats;
			float percentageOfVotes = partyObject.getProjectedPercentageOfVotes();
			percentageOfVotesTotal = percentageOfVotesTotal + percentageOfVotes;
			length++;

		}
		}

		float avgNumberSeats = numberSeats/length;
		float avgPercentageOfVotes = percentageOfVotesTotal/length;


		// create new party object
		party.setProjectedNumberOfSeats(avgNumberSeats);
		party.setProjectedPercentageOfVotes(avgPercentageOfVotes);
		return party;
	}


	@Override
	public String toString() {
		return "Number of seats: " + numOfSeats;
	}
}
