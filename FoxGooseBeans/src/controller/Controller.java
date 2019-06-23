package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Characters;
import model.Model;


public class Controller implements ActionListener {

	private Model model;
	
	/**
	 * Constructor
	 * @param model
	 */
	public Controller(Model model) {
		this.model = model;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * Give the buttons the desired functionality
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		switch(ae.getActionCommand()) {
		case "RESET":  //reset the game is the reset button is pressed
			model.reset();
			break;
		case "BOAT LEFT": //move the boat to the left side if the left button of the boat is pressed and the farmer is currently on the boat
			if (!model.isBoatLeft())
				model.moveBoat();
			break;
		case "BOAT RIGHT": //move the boat to the right if the right button of the boat is pressed and the farmer is on the boat
			if (model.isBoatLeft())
				model.moveBoat();
			break;
		default:   //move the images left or right, depending on the button pressed
			String[] command = ae.getActionCommand().split(" ");
			Characters character = Characters.getByName(command[0]);
			if (character != null) {
				if (command[1].equalsIgnoreCase("RIGHT"))
					model.moveCharacterRight(character);
				else if (command[1].equalsIgnoreCase("LEFT"))
					model.moveCharacterLeft(character);
			}
			break;
		}
	}
}

