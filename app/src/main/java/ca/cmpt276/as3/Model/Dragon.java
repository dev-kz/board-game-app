package ca.cmpt276.as3.Model;

/**
 * Dragon class is a class that is for the
 * game model of this project. It is a Java class that
 * will allow the user to store in information and be
 * able to receive information later from this class.
 */
public class Dragon {
    private int row;
    private int col;
    private int numDragons;
    private int bestScore;
    private int numberOfGamesPlayed;
    private static Dragon instance;

    private Dragon() {}
    public static Dragon getInstance(){
        if(instance == null) instance = new Dragon();

        return instance;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public int getNumDragons() {
        return numDragons;
    }
    public void setNumDragons(int numDragons) {
        this.numDragons = numDragons;
    }
    public int getNumGamesPlayed() {
        return numberOfGamesPlayed;
    }
    public void setNumGamesPlayed(int numberOfGamesPlayed) {
        this.numberOfGamesPlayed = numberOfGamesPlayed;
    }
    public int getBestScore() {
        return bestScore;
    }
    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }
}
