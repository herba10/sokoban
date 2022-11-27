package walking;

import java.util.Random;
import java.util.List;

public class Room {
	Random random;
	int width;
	int height;
	int x;
	int y;
	char type;
	int id;
	Room lastRoom;
	Room(int GAME_WIDTH, int GAME_HEIGHT, char type, int id, List<Room> rooms, int ROOMS_NUMBER){
		random = new Random();

		if (rooms.isEmpty()) {
			this.width = random.nextInt(GAME_WIDTH/ROOMS_NUMBER-3)+3;
			this.height = random.nextInt(GAME_HEIGHT/ROOMS_NUMBER-3)+3;
			this.x = 1;
			this.y = 1;
		}
		else {
			lastRoom = rooms.get(rooms.size()-1);
			this.x = lastRoom.x-1+lastRoom.width;
			this.y = lastRoom.y-1+lastRoom.height;
			this.width = GAME_WIDTH-this.x-2;
			this.height = GAME_HEIGHT-this.y-2;
		}
		this.type = type;
		this.id = id;
	}
}
