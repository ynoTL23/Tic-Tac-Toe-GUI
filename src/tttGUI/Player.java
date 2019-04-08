package tttGUI;

public class Player implements Comparable<Player> {

	private String name, marker;
	private int numGames, numWins, numLosses;
	
	// default constructor
	public Player() {
		name = "Player";
		marker = " X ";
		numGames = 0;
		numWins = 0;
		numLosses = 0;
	}

	// overloaded constructor
	// set player name and marker
	public Player(String name, String marker) {
		this(); // call to def constructor
		this.name = name;
		this.marker = marker;
	}
	
	// no setters to configure vars individually
	
	// getWinRate, getLossRate
	
	// START SETTERS
	public void addNumWins() {
		numWins++;
		numGames++;
	}
	
	public void addNumLossses() {
		numLosses++;
		numGames++;
	}
	
	public void addDraw() {
		numGames++;
	}
	// END SETTERS
	
	// START GETTERS
	public String getName() {
		return name;
	}

	public String getMarker() {
		return marker;
	}

	public int getNumGames() {
		return numGames;
	}

	public int getNumWins() {
		return numWins;
	}

	public int getNumLosses() {
		return numLosses;
	}
	// END GETTERS
	
	// equals
	@Override
	public boolean equals(Object o) {
		if(o instanceof Player) {
			Player otherPlayer = (Player)o;
			if((this.name.equalsIgnoreCase(otherPlayer.name)) 
			&& (this.marker.equalsIgnoreCase(otherPlayer.marker)) 
			&& (this.numGames == otherPlayer.numGames)
			&& (this.numWins == otherPlayer.numWins) 
			&& (this.numLosses == otherPlayer.numLosses)) {
				return true;
			}
		}
		 return false;
	}
	
	// toString
	@Override
	public String toString() {
		String s = "Player [name=" + name + ", " + 
						   "marker=" + marker + ", " + 
						   "numGames=" + numGames+ ", " +
						   "numWins=" + numWins+ ", " +
						   "numLosses=" + numLosses + "]";
		return s;
	}
	
	@Override
	public int compareTo(Player p) {
		if(this.numWins > p.numWins) {
			return 1;
		} else if(this.numWins < p.numWins) {
			return -1;
		} else {
			return 0;
		}
	}
	
}
