package model;

import java.util.HashSet;

public enum Characters {
	FOX,
	GOOSE,
	BEANS,
	FARMER;
	
	private HashSet<Characters> enemies;
	
	/**
	 * Adds the selected Character object as an enemy.
	 * @param enemy
	 */
	void addEnemy(Characters enemy) {
		enemies.add(enemy);
	}
	
	/**
	 * Empty constructor.
	 */
	Characters() {
		enemies = new HashSet<Characters>();  //initialise the 'enemies' HashSet
	}
	
	/**
	 * Getter method for the HashSet
	 * @return enemies
	 */
	public HashSet<Characters> getEnemies() {
		return enemies;
	}
	
	/**
	 * The first letter of the constants is Capital and all other letters are lower case
	 * @return character
	 */
	public String niceName() {
		return name().substring(0, 1) + name().substring(1).toLowerCase();
	}
	
	public static Characters getByName(String name) {
		Characters character = null;
		for (Characters chara : Characters.values()) {
			if (chara.name().equalsIgnoreCase(name)) {
				character = chara;
				break;
			}
		}
		return character;
	}
}
