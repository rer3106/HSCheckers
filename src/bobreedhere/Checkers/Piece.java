package bobreedhere.Checkers;

public class Piece {

	
	boolean isKinged;
	int color;

	public Piece(int colour){

		color = colour;
	}
	

	public boolean isKinged() {
		return isKinged;
	}
	public void setKinged() {
		this.isKinged = true;
	}
	public int getColor() {
		return color;
	}
	public String toString(){
		return color==0?"B ":"W ";
	}
	
	
}
