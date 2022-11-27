package walking;

public  class Tile{
	char type;
	int x, y;
	boolean target;
	Tile(char type, int x, int y, boolean target){
		this.target = target;
		this.type = type;
		this.x = x;
		this.y = y;
	}
}