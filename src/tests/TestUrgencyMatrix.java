package tests;

public class TestUrgencyMatrix {
	/* Tasks:
	 *  Clean the dog
	 *  Feed the dog
	 *  Buy groceries
	 *  Attend Fueneral
	 *  Repay debts
	 * */
	double a = 1;
	Double[][] urgencies = new Double[][]
		{{1.0, 0.5, 0., a, 0.1},
		 {2.0, 1.0, a,   a, 0.1},
		 {a,   a, 1.0, a, 0.5},
		 {a,   a,   a, 1.0, 1.5},
		 {6.0, 6.0, 2.0, 0.6, 1.0}};
	
}
