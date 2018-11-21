package Ex1;

//Meshi Sanker, ID:205562747

public class Game {
	private char[] hostTeam;
	private char[] guestTeam;
	private int hostScore;
	private int guestScore;

	
	public String gameToString() {
		return hostNameToString().toUpperCase() + "  " + hostScore + " - " + guestScore + "   "
				+ guestNameToString().toUpperCase();
	}

	public String hostNameToString() {
		return hostTeam[0] + "" + hostTeam[1] + "" + hostTeam[2];
	}

	public String guestNameToString() {
		return guestTeam[0] + "" + guestTeam[1] + "" + guestTeam[2];
	}

	public char[] getHostTeam() {
		return hostTeam;
	}

	public void setHostTeam(char[] hostTeam) {
		this.hostTeam = hostTeam;
	}

	public Game(char[] hostTeam, char[] guestTeam, int hostScore, int guestScore) {
		super();
		this.hostTeam = hostTeam;
		this.guestTeam = guestTeam;
		this.hostScore = hostScore;
		this.guestScore = guestScore;
	}

	public char[] getGuestTeam() {
		return guestTeam;
	}

	public void setGuestTeam(char[] guestTeam) {
		this.guestTeam = guestTeam;
	}

	public int getHostScore() {
		return hostScore;
	}

	public void setHostScore(int hostScore) {
		this.hostScore = hostScore;
	}

	public int getGuestScore() {
		return guestScore;
	}

	public void setGuestScore(int guestScore) {
		this.guestScore = guestScore;
	}

}
