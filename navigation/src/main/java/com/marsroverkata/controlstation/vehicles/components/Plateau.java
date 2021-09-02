package com.marsroverkata.controlstation.vehicles.components;

/**
 * @author Alberto Senserrich Montals
 *
 */
public class Plateau {

	private int maxXPos;
	private int maxYPos;
	private static int[][] colisions;
	private static int OCUPIED_VALUE = 99;
	private static int LOST_ROVERT_VALUE = 98;
	private static int FREE_VALUE = 0;
	private static int VISITED_VALUE = 1;

	/**
	 * @param maxXPos
	 * @param maxYPos
	 */
	public Plateau(int maxXPos, int maxYPos) {
		super();
		this.maxXPos = maxXPos;
		this.maxYPos = maxYPos;
		// plateau has original size x/y but we must keep track of exit coordinates of
		// elements, for that reason we must store a free colum and row on each limit
		Plateau.colisions = new int[maxXPos + 3][maxYPos + 3];
	}

	public void markFreeSpace(int x, int y) {
		x++;
		y++;
		if (x <= this.maxXPos && x >= 0 && y <= this.maxYPos && y >= 0) {
			Plateau.colisions[x][y] = Plateau.VISITED_VALUE;
		}
	}

	
	public void markLostRover(int x, int y) {
		x++;
		y++;
//		if (x <= this.maxXPos && x >= 0 && y <= this.maxYPos && y >= 0) {
			Plateau.colisions[x][y] = Plateau.LOST_ROVERT_VALUE;
//		}
	}
	
	public void markOcupiedSpace(int x, int y) {
		x++;
		y++;
//		if (x <= this.maxXPos && x >= 0 && y <= this.maxYPos && y >= 0) {
			Plateau.colisions[x][y] = Plateau.OCUPIED_VALUE;
//		}
	}

	public boolean isRovertLostOnCoordenates(int x, int y) {
		x++;
		y++;
		return Plateau.LOST_ROVERT_VALUE == Plateau.colisions[x][y];
//		if(x<=this.maxXPos && x>=0 && y<=this.maxYPos && y>=0){
//		}	else{
//			return false;
//		}
	}

	public boolean isCoordinateInsidePlateau(int xDestination, int yDestination) {
//		xDestination++;
//		yDestination++;
		boolean isValid = true;
		if (this.maxXPos < xDestination || 0 > xDestination) {
			isValid = false;
		} else if (this.maxYPos < yDestination || 0 > yDestination) {
			isValid = false;
		}
		return isValid;
	}
	
	public int calculateTotalAreaExplored() {
		long totalAreaExplored = 0;
		for(int x= 1; x< this.maxXPos;x++) {
			for(int y= 1; y< this.maxYPos;y++) {
				if(colisions[x][y] == VISITED_VALUE || colisions[x][y] == OCUPIED_VALUE)
				totalAreaExplored ++;
			}
		}
		return (int)totalAreaExplored;
	}
	
	

}
