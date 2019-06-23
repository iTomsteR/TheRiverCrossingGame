package model;

import java.util.Observable;

public class Model extends Observable {

	private Position[] positions;
	private boolean boatLeft, gameOver, win;

	/**
	 * Empty constructor
	 */
	public Model() {
		reset();
		setEnemies();
	}

	/**
	 * Define the enemies.
	 */
	public void setEnemies() {
		Characters.FOX.addEnemy(Characters.GOOSE);
		Characters.GOOSE.addEnemy(Characters.FOX);
		Characters.BEANS.addEnemy(Characters.GOOSE);
		Characters.GOOSE.addEnemy(Characters.BEANS);
	}
	
	/**
	 * Check if the boat is at the left side.
	 * @return boatLeft
	 */
	public boolean isBoatLeft() {
		return boatLeft;
	}

	/**
	 * Reset the game
	 */
	public void reset() {
		boatLeft = false;
		gameOver = false;
		win = false;
		positions = new Position[Characters.values().length];
		for (int i = 0; i < positions.length; i++) {
			positions[i] = Position.RIGHT;
		}
		setChanged();
		notifyObservers("RESET");
	}

	/**
	 * Check if the player won
	 */
	public void checkWin() {
		win = true;
		for (int i = 0; i < Characters.values().length; i++) {
			if (Characters.values()[i] != Characters.FARMER && positions[i] != Position.LEFT)
				win = false;
		}
		if (win) {
			setChanged();
			notifyObservers("WIN");
		}
	}
	
	/**
	 * Check if the game is over
	 */
	public void checkGameOver() {
		if ((boatLeft && positions[Characters.FARMER.ordinal()] != Position.RIGHT) || (!boatLeft && positions[Characters.FARMER.ordinal()] != Position.LEFT)) {
			for (Characters character : Characters.values()) {
				Position unsafeSide = Position.RIGHT;
				if (!boatLeft && positions[Characters.FARMER.ordinal()] != Position.LEFT) {
					unsafeSide = Position.LEFT;
				}
				if (positions[character.ordinal()] == unsafeSide) {
					if (character.getEnemies().size() > 0) {
						for (Characters enemy : character.getEnemies()) {
							if (positions[enemy.ordinal()] == positions[character.ordinal()]) {
								gameOver = true;
								setChanged();
								notifyObservers("GAME OVER");
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Move the boat to the desired location
	 */
	public void moveBoat() {
		if (gameOver || win)
			return;
		if (positions[Characters.FARMER.ordinal()] == Position.BOAT) {
			boatLeft = !boatLeft;
			setChanged();
			notifyObservers(boatLeft ? "BOAT LEFT" : "BOAT RIGHT");
		}
		checkWin();
		checkGameOver();
	}

	/**
	 * Count how many times the boat changes its position
	 * @return count
	 */
	public int getBoatCount() {
		int count = 0;

		for (Position position : positions) {
			if (position == Position.BOAT)
				count++;
		}
		return count;
	}

	/**
	 * Move the character to the left side, using the boat
	 * @param character
	 */
	public void moveCharacterLeft(Characters character) {
		if (gameOver || win)
			return;
		if (boatLeft && positions[character.ordinal()] == Position.BOAT) {
			positions[character.ordinal()] = Position.LEFT;
			setChanged();
			notifyObservers(character.name() + " LEFT");
		} else if (!boatLeft && positions[character.ordinal()] == Position.RIGHT) {
			if ((positions[Characters.FARMER.ordinal()] == Position.BOAT && getBoatCount() == 1) || (positions[Characters.FARMER.ordinal()] != Position.BOAT && getBoatCount() == 0) || (character == Characters.FARMER)) {
				positions[character.ordinal()] = Position.BOAT;
				setChanged();
				notifyObservers(character.name() + " BOAT");
			}
		}
		checkWin();
	}

	/**
	 * Move the character to the right side using the boat
	 * @param character
	 */
	public void moveCharacterRight(Characters character) {
		if (gameOver || win)
			return;
		setChanged();
		if (!boatLeft && positions[character.ordinal()] == Position.BOAT) {
			positions[character.ordinal()] = Position.RIGHT;
			notifyObservers(character.name() + " RIGHT");
		} else if (boatLeft && positions[character.ordinal()] == Position.LEFT) {
			positions[character.ordinal()] = Position.BOAT;
			notifyObservers(character.name() + " BOAT");
		}
		checkWin();
	}
}

