package logic;

import java.util.LinkedList;
import java.util.Queue;

public class TaskComparator {
	private static Queue<Pair<Integer>> to_be_compared = new LinkedList<>();
	
	/**
	 * 
	 * */
	public static boolean push(Pair<Integer> pair){
		if(to_be_compared.contains(pair) || to_be_compared.contains(pair.swap()))
			return false;
		else{
			to_be_compared.add(pair);
			return true;
		}
	}
	
	public static boolean push(Integer id1, Integer id2){
		return push(new Pair<Integer>(id1, id2));
	}
	
	public static void finished_comparison(){
		if(to_be_compared.isEmpty())
			return;
		
	}
	
	
	
}
