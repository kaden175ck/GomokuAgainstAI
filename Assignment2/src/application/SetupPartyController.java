/**
  * ClassName: SetupPartyController
  * Description: This class represents the set party controller.
  * @author  Haoyang Shi 30105296
  * @date 2020 7.30
  */
package application;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Factory;

public class SetupPartyController extends PollTrackerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> SelectPartyChoiceBox;

    @FXML
    private Label PartyNameLable;

    @FXML
    private Button SetPartyButton;

    @FXML
    private Button ClearButton;

    @FXML
    private Button SubmitButton;

    @FXML
    private Label SelectPartyLable;

    @FXML
    private TextField PartyNameTextField;
    
    //Initialize the default Factory psrtyName
    private String[] value = {};
    //Initialize a  integer to get the value from listener
    private int partyToChangeIndex = 0;
    
    
    /**private String[] getDefaultpartyName() {
    	defaultValue = getFactory().getPartyNames();
		return defaultValue;
    }*/


    /**
	  * Title: ClearButtonClicked
	  * Description: This is a method to clear the data of ChoiceBox and TextField.
	  * Every time after click 'Clear' button, it will clear all information in this view and update information displayed to the most information from the current PollList and Factory objects
	  * @param event
	  */
    @FXML
    void ClearButtonClicked(ActionEvent event) {
    	PartyNameTextField.clear();
    	for(int i = 0; i < value.length; i++) {
    		value[i] = "" + i;
    	}
    	SelectPartyChoiceBox.setItems(FXCollections.observableArrayList(value));
    }
    
    /**
	  * Title: SetPartyButtonClicked
	  * Description: This is a method to set the party name which will replace a party name in the choice box.
	  * Every time after click 'Set Party Info' button, it will make the TextFiled information replace the selected party name in ChoiceBox.
	  * @param event
	  */
    @FXML
    void SetPartyButtonClicked(ActionEvent event) {
    	value[partyToChangeIndex] = PartyNameTextField.getText();
    	SelectPartyChoiceBox.setItems(FXCollections.observableArrayList(value));

    }

    /**
	  * Title: SubmitButtonClicked
	  * Description: This is a method to set the party name to the Factory.
	  * Every time after click 'Submit All Party Info, it will set the Factory a name list in current Choice Box.
	  * @param event
	  */
    @FXML
    void SubmitButtonClicked(ActionEvent event) {
    	Factory aNewFactory = getFactory();
    	aNewFactory.setPartyNames(value);
    	setFactory(aNewFactory);

    }

    @FXML
    void initialize() {
        assert SelectPartyChoiceBox != null : "fx:id=\"SelectPartyChoiceBox\" was not injected: check your FXML file 'SetupPartiesView.fxml'.";
        assert PartyNameLable != null : "fx:id=\"PartyNameLable\" was not injected: check your FXML file 'SetupPartiesView.fxml'.";
        assert SetPartyButton != null : "fx:id=\"SetPartyButton\" was not injected: check your FXML file 'SetupPartiesView.fxml'.";
        assert ClearButton != null : "fx:id=\"ClearButton\" was not injected: check your FXML file 'SetupPartiesView.fxml'.";
        assert SubmitButton != null : "fx:id=\"SubmitButton\" was not injected: check your FXML file 'SetupPartiesView.fxml'.";
        assert SelectPartyLable != null : "fx:id=\"SelectPartyLable\" was not injected: check your FXML file 'SetupPartiesView.fxml'.";
        assert PartyNameTextField != null : "fx:id=\"PartyNameTextField\" was not injected: check your FXML file 'SetupPartiesView.fxml'.";
       
       
       
        //Initialize the ChoiceBox name list
        SelectPartyChoiceBox.setItems(FXCollections.observableArrayList(value));
        //Add Listener of ChoiceBox
        SelectPartyChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
        		new ChangeListener<Number>() {
        			@Override
        			public void changed(ObservableValue observable, Number oldValue, Number newValue) {
        				int index = newValue.intValue();
            			if (index >= 0)
            				partyToChangeIndex = index;
        			}
        		}
        );
        

    }

    /**
	  * Title: refresh()
	  * Description: This is a method refresh to the default view.
	  * This is an override method of refresh() from PollTrackerController parent. Every time it works, the TextField and ChoiceBox will go back to the original view
	  */
	@Override
	public void refresh() {
		PartyNameTextField.clear();
		value = getFactory().getPartyNames();
    	SelectPartyChoiceBox.setItems(FXCollections.observableArrayList(value));
    	
	}
}
