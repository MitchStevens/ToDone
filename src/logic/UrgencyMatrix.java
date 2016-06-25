package logic;

import io.Reader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Jama.Matrix;

import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

public class UrgencyMatrix {
	private String name;
	
	//size of matrix
	private int n;
	
	// Urgency Matrix
	private Matrix U;
	
	private List<Integer> task_ids;
	
	public UrgencyMatrix(int n){
		this.n = n;
		this.U = new Matrix(n, n, 1.0);
	}
	
	public UrgencyMatrix(String name, int n, double[][] U){
		this.name = name;
		this.n = n;
		this.U = new Matrix(U);
	}
	
	/**
	 * Sets {code U[a][b] = val} in the matrix. Since {@code U[i][j] = 1/U[j][i]},
	 * this method also sets the corresponding location to 1/val.
	 * @param i Column index
	 * @param j Row index
	 * @param val the value to be set.
	 * */
	public void set(int i, int j, double val){
		if(i>=0 && i<n &&
		   j>=0 && j<n && val > 0){
			U.set(i, j, val);
			U.set(j, i, 1/val);
		}
	}
	
	public void set(Task a, Task b, double val){
		int i = task_ids.indexOf(a);
		int j = task_ids.indexOf(b);
		if(i == -1) throw new Error("Task "+ a +" could not be found in this UM.");
		if(j == -1) throw new Error("Task "+ b +" could not be found in this UM.");
		
		set(i, j, val);
	}
	
	public List<Integer> rank_tasks(List<Integer> task_ids){
		double alpha = 1.0/n;
		int num_iter = 100;
		Matrix x = new Matrix(n, 1, 1.0/n);
		Matrix M = Matrix.identity(n, n).times(1 - n*alpha).plus(U.times(alpha));
		while(num_iter-- > 0){
			x = M.times(x);
			x = x.times(1/x.norm2());
			x.print(n, 1);
		}
		
		Map<Integer, Double> map = new TreeMap<>();
		
		for(int i = 0; i < n; i++)
			map.put(task_ids.get(i), x.get(i, 0));
		
		Ordering.natural().onResultOf(Functions.forMap(map));
		
		return new ArrayList<Integer>(map.keySet());
	}
	
//	public static void main(String[] args){
//		UrgencyMatrix m = new UrgencyMatrix(3, new Long[]{0L, 1L, 2L});
//		/**
//		 * task 0: clean the car
//		 * task 1: feed the dog
//		 * task 2: perform surgery
//		 * 
//		 * t1 is 2 times more important than t0
//		 * t2 is 10 times more important than t0
//		 * t2 is 7 times more important than t1
//		 * */
//		
//		m.set(1, 0, 2);
//		m.set(2, 0, 10);
//		m.set(2, 1, 7);
//		m.rank_tasks();
//	}
}
