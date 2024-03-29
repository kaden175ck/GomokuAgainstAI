/**
  * ClassName: SetupPollTrackerController
  * Description:  This class create the content of Set up Poll Tracker.
  * @author Yuan Liu 30087153
  * @since 2020 7.29
  */
package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.Factory;
import model.InvalidSetupDataException;
import model.Poll;
import model.PollList;

public class SetupPollTrackerController extends PollTrackerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label NumOfPollsLable;

    @FXML
    private TextField NumOfPollsTyped;

    @FXML
    private Label NumOfSeatsLable;

    @FXML
    private Label NumOfPartiesLable;

    @FXML
    private TextField NumOfSeatsTyped;

    @FXML
    private TextField NumOfPartiesTyped;

    @FXML
    private Button ClearButton;

    @FXML
    private Button SubmitButton;
    
    //error label
    @FXML
    private Label errorLabel;
    
    //Create three integer value to get the input information from the three TextField.
    //NumOfParties get the input from NumOfPollsTyped
    //NumOfSeats get the input from NumOfSeatsTyped
    //NumOfPolls get the input from NumOfPartiesTyped
    private int NumOfParties = 0;
    private int NumOfSeats = 0;
    private int NumOfPolls = 0;
    

	/**
	  * Title: ClearButtonClicked
	  * Description: This is a method to handle the event of 'Clear' button.
	  * Every time after click 'Clear' button, the three TextField value will clear all information in current view and bring it back to a default view.
	  * @param event
	  */
    @FXML
    void ClearButtonClicked(ActionEvent event) {
    	errorLabel.setText("");
    	NumOfPollsTyped.clear();
    	NumOfSeatsTyped.clear();
    	NumOfPartiesTyped.clear();
    }

    /**
	  * Title: SubmitButtonClicked
	  * Description: This is a method to handle the event of 'Submit' button.
	  * Every time after click 'Submit' button, all information in the polls and factory objects should be updated to reflect the new information.
	  * @param event
	  */
    @FXML
    void SubmitButtonClicked(ActionEvent event) throws NumberFormatException, InvalidSetupDataException{
    	/*
    	 * InvalidSetupDataException
    	 */
    	try {
    	//Give input value to the three variables(NumOfPolls, NumOfSeats, NumOfParties)
    	NumOfPolls = Integer.valueOf(NumOfPollsTyped.getText());
    	NumOfSeats = Integer.valueOf(NumOfSeatsTyped.getText());
    	NumOfParties = Integer.valueOf(NumOfPartiesTyped.getText());
    	
    	//if it is not a integer or is less than 1 catch exception
    	if (NumOfPolls <= 0 || NumOfSeats <= 0 || NumOfParties <=0) {
    		throw new InvalidSetupDataException("The input value should be a non negative integer.");
    	}
    	
    	//if the error is not there return errorLabel to nothing
    	errorLabel.setText("");
    	
    	//Create a new Factory object with certain number of seats and certain number of name.
    	Factory aNewFactory = new Factory(NumOfSeats);
    	String[] partiesName = new String[NumOfParties];
    	for(int i = 0; i < NumOfParties; i++) {
    		String partyName = Integer.toString(i);
    		partiesName[i] = partyName;
    	}
    	aNewFactory.setPartyNames(partiesName);
    	//Using setFactory() from PollTrackerController to set the new Factory to app.factory
    	setFactory(aNewFactory);
    	
    	//Create a new PollList object with certain number of seats and certain number of Poll objects.
    	PollList aNewPollList = new PollList(NumOfPolls, NumOfSeats);
    	for(int i = 0; i < NumOfPolls; i++ ) {
    		//Add random Poll to the PollList as a test
    		//aNewPollList.addPoll(getFactory().createRandomPoll("Poll"+i));
    		//Add empty Poll to the PollList named aNewPollList
    		aNewPollList.addPoll(new Poll("Poll"+i, NumOfParties));
    		}
 
    	////Using setFactory() from PollTrackerController to set the new Polllist to app.polls
    	setPollList(aNewPollList);
    	
    	//catch the exception and print out a message
    	}catch(NumberFormatException e) {
    		errorLabel.setText("A integer greater than 0 needs to be entered.");
    		System.out.println(e.getMessage());
    	}catch (InvalidSetupDataException e) {
    		errorLabel.setText("A integer greater than 0 needs to be entered.");
    		System.out.println(e.getMessage());
    	}

    }

    @FXML
    void initialize() {
        assert NumOfPollsLable != null : "fx:id=\"NumOfPollsLable\" was not injected: check your FXML file 'SetupPollTrackerView.fxml'.";
        assert NumOfPollsTyped != null : "fx:id=\"NumOfPollsTyped\" was not injected: check your FXML file 'SetupPollTrackerView.fxml'.";
        assert NumOfSeatsLable != null : "fx:id=\"NumOfSeatsLable\" was not injected: check your FXML file 'SetupPollTrackerView.fxml'.";
        assert NumOfPartiesLable != null : "fx:id=\"NumOfPartiesLable\" was not injected: check your FXML file 'SetupPollTrackerView.fxml'.";
        assert NumOfSeatsTyped != null : "fx:id=\"NumOfSeatsTyped\" was not injected: check your FXML file 'SetupPollTrackerView.fxml'.";
        assert NumOfPartiesTyped != null : "fx:id=\"NumOfPartiesTyped\" was not injected: check your FXML file 'SetupPollTrackerView.fxml'.";
        assert ClearButton != null : "fx:id=\"ClearButton\" was not injected: check your FXML file 'SetupPollTrackerView.fxml'.";
        assert SubmitButton != null : "fx:id=\"SubmitButton\" was not injected: check your FXML file 'SetupPollTrackerView.fxml'.";
        

    }

    /**
	  * Title: refresh
	  * Description: This is a method to refresh current view to the original views.
	  * This is an override method of refresh() from PollTrackerController parent.It will clear all information in current view and bring it back to a default view  
	  */
	@Override
	public void refresh() {
		NumOfPollsTyped.clear();
    	NumOfSeatsTyped.clear();
    	NumOfPartiesTyped.clear();
		
	}
}