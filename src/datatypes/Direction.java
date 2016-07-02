package datatypes;

public enum Direction {
	UP		(0),
	RIGHT	(1),
	DOWN	(2),
	LEFT	(3),
	UNKNOWN (4);
	
	int value;
	Direction(int d){
		this.value = d;
	}
}
