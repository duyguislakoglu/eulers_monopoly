package gui;

import domain.board.tiles.TilePosition;

public class TileLocations {
	static int[][][] tileLocation;
	static TileLocations instance;
	
	private TileLocations() {
		initializeTileLocations();
	}
	
	private static void initializeTileLocations() {
		int width=40;
		tileLocation = new int[2][56][3]; // (x,y), number, layer
		
		// 0th layer (inside)
		tileLocation[0][0][0] = 487;
		tileLocation[1][0][0] = 487;
		tileLocation[0][1][0] = 425;
		tileLocation[1][1][0] = 486;
		
		//bottom
		for (int i=2; i<7; i++){
			tileLocation[0][i][0] = 425 - (i-1)*width;
			tileLocation[1][i][0] = 486;
		}
		
		//left
		for (int i=7; i<13; i++){
			tileLocation[0][i][0] = 205;
			tileLocation[1][i][0] = 422 - (i-7)*width;
		}
		
		//top
		for (int i=13; i<19; i++){
			tileLocation[0][i][0] = 270 + (i-13)*width;
			tileLocation[1][i][0] = 208;
		}
		
		//right
		for (int i=19; i<24; i++){
			tileLocation[0][i][0] = 490;
			tileLocation[1][i][0] = 272 + (i-19)*width;
		}

		// 1st layer
		// down
		tileLocation[0][0][1] = 567;
		tileLocation[1][0][1] = 567;
		tileLocation[0][1][1] = 506;
		tileLocation[1][1][1] = 567;
		for (int i = 2; i < 10; i++) {
			tileLocation[0][i][1] = 506 - (i-1)*width;
			tileLocation[1][i][1] = 567;
		}
		tileLocation[0][10][1] = 101;
		tileLocation[1][10][1] = 598;

		//left
		tileLocation[0][11][1] = 125;
		tileLocation[1][11][1] = 508;
		for (int i = 12; i < 20; i++) {
			tileLocation[1][i][1] = 508 - (i-11)*width;
			tileLocation[0][i][1] = 125;
		}
		
		//free parking
		tileLocation[0][20][1] = 130;
		tileLocation[1][20][1] = 130;

		//up
		tileLocation[0][21][1] = 191;
		tileLocation[1][21][1] = 125;
		for (int i = 22; i < 30; i++) {
			tileLocation[0][i][1] = 191 + (i-21)*width;
			tileLocation[1][i][1] = 125;
		}
		
		//
		tileLocation[0][30][1] = 575;
		tileLocation[1][30][1] = 124;

		// right

		tileLocation[0][31][1] = 575;
		tileLocation[1][31][1] = 191;
		for (int i = 32; i < 40; i++) {
			tileLocation[1][i][1] = 191 + (i-31)*width;
			tileLocation[0][i][1] = 575;
		}
		
		//bottom layer(2)
		int y = 653;
		tileLocation[0][0][2] = y;
		tileLocation[1][0][2] = y;		
		for (int i = 1; i<14; i++){
			tileLocation[0][i][2] = 588-(i-1)*width;
			tileLocation[1][i][2] = y;
		}
		
		// left
		int x = 48;
		tileLocation[0][14][2] = x;
		tileLocation[1][14][2] = y;	
		for (int i = 15; i<28; i++){
			tileLocation[0][i][2] = x;
			tileLocation[1][i][2] = 589-(i-15)*width;
		}
		
		//top
		y = x;
		tileLocation[0][28][2] = x;
		tileLocation[1][28][2] = y;	
		for (int i=29; i<42; i++){
			tileLocation[0][i][2] = 107+(i-29)*width;
			tileLocation[1][i][2] = y;
		}
		
		//right
		x = 650;
		tileLocation[0][42][2] = x;
		tileLocation[1][42][2] = 47;	
		for (int i=43; i<56; i++){
			tileLocation[0][i][2] = x;
			tileLocation[1][i][2] = 107+(i-43)*width;
		}
	}	
	
	public static int[][][] getTileArray(){
		if (tileLocation == null) initializeTileLocations();
		return tileLocation;
	}
	
	public static TileLocations getInstance(){
		if (instance==null) instance=new TileLocations();
		return instance;
	}
	
	public Position getPosition(TilePosition tilePos){
		int lane = tilePos.getLane();
		int tileno = tilePos.getTileIndex();
		return new Position(tileLocation[0][tileno][lane], tileLocation[1][tileno][lane]);
	}
	
	public int getX(int no, int lane){
		return tileLocation[0][no][lane];
	}
	
	public int getY(int no, int lane){
		return tileLocation[1][no][lane];
	}
}
