package view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import model.Characters;

public class View extends JFrame implements Observer {

	private Controller controller;

	private JPanel buttons;
	private Display gameView;
	private int score;

	/**
	 * Constructor
	 * @param controller
	 */
	public View(Controller controller) {
		super("Fox, Goose and Bag of Beans");
		this.controller = controller;
		initWidgets();
		pack();
		setSize(1200, 500);
		setMinimumSize(new Dimension(750, 750));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Create the main frame and all the widgets and their functionalities.
	 */
	public void initWidgets() {
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		gameView = new Display(this);

		buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

		add(gameView);
		add(buttons);

		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(controller);
		resetButton.setActionCommand("RESET");
		buttons.add(resetButton);
		
		buttons.add(new JLabel("Boat: "));

		JButton boatLeftButton = new JButton("<");
		JButton boatRightButton = new JButton(">");

		boatLeftButton.addActionListener(controller);
		boatLeftButton.setActionCommand("BOAT LEFT");
		boatRightButton.addActionListener(controller);
		boatRightButton.setActionCommand("BOAT RIGHT");

		buttons.add(boatLeftButton);
		buttons.add(boatRightButton);

		for (Characters character : Characters.values()) {
			buttons.add(new JLabel(character.niceName() + ": "));

			JButton leftButton = new JButton("<");
			JButton rightButton = new JButton(">");

			leftButton.addActionListener(controller);
			leftButton.setActionCommand(character.name() + " LEFT");
			rightButton.addActionListener(controller);
			rightButton.setActionCommand(character.name() + " RIGHT");

			buttons.add(leftButton);
			buttons.add(rightButton);
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * The update method is called whenever the observed object is changed
	 */
	@Override
	public void update(Observable observable, Object o) {
		String command = (String) o;
		String[] commandParts = command.split(" ");
		
		//If the player won the game, run the 'Win' animation and change the title to 'You Win!'
		if (command.equalsIgnoreCase("WIN")) {
			gameView.win();
			setTitle("You win!");
			return;
		}
		
		//if the reset button is pressed, reset the game and the game title
		if (command.equalsIgnoreCase("RESET")) {
			gameView.reset();
			score = 0;
			setTitle("Fox, Goose and Bag of Beans");
			gameView.revalidate();
			gameView.repaint();
			return;
		}
		
		//if the game is over, run the 'Game Over' animation and change the title
		if (command.equalsIgnoreCase("GAME OVER")) {
			gameView.gameOver();
			setTitle("Game Over. Predator ate prey");
			return;
		}
		
		//each time the boat makes a movement, 1 is subtracted from the player's overall score
		if (commandParts[0].equalsIgnoreCase("BOAT")) {
			gameView.moveBoat();
			score--;
			setTitle("Score: " + score);
			//move the characters
		} else if (commandParts[1].equalsIgnoreCase("RIGHT"))
			gameView.moveCharacterRight(Characters.getByName(commandParts[0]));
		else if (commandParts[1].equalsIgnoreCase("LEFT"))
			gameView.moveCharacterLeft(Characters.getByName(commandParts[0]));
		else if (commandParts[1].equalsIgnoreCase("BOAT"))
			gameView.addToBoat(Characters.getByName(commandParts[0]));
	}
}