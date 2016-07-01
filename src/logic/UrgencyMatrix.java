package logic;

import io.Reader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.Vector;

import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

public class UrgencyMatrix {
	private String name;
	
	//size of matrix
	private int n;
	
	// Urgency Matrix
	private double[][] U;
	
	private List<Integer> task_ids;
	
	public UrgencyMatrix(int n){
		this.n = n;
		this.U = new double[n][n];
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n ; j++)
				U[i][j] = 1.0;
	}
	
	public UrgencyMatrix(String name, double[][] U){
		this.name = name;
		this.n = U.length;
		this.U = U;
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
			U[i][j] = val;
			U[j][i] = 1.0/val;
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
		Matrix A = A();
		Vector b = Vector.zero(n+1);
		b.set(n, -1);
		
		Vector x = A.withSolver(LinearAlgebra.GAUSSIAN).solve(b);
		System.out.println(x);
		return null;
	}
	
	/**
	 * Calculates a matrix of values to be used in the rank tasks methods.
	 * Read task_ordering.tex for more info.
	 * */
	private Matrix A(){
		Matrix A = Matrix.zero(n+1, n+1);
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++){
				A.set(i, j, -2*(U[i][j] + U[j][i]));
			}
		
		for(int j = 0; j < n; j++){
			double temp = A.get(j, j);
			for(int l = 0; l < n; l++)
				temp += 2*U[l][j]*U[l][j];
			temp += n;
			A.set(j, j, temp);
		}
		
		for(int k = 0; k < n; k++){
			A.set(k, n, -1.0);
			A.set(n, k, -1.0);
		}
		
		return A;
	}
	
	public static void main(String[] args){
		/**
		 * task 0: clean the car
		 * task 1: feed the dog
		 * task 2: perform surgery
		 * 
		 * t1 is 2 times more important than t0
		 * t2 is 10 times more important than t0
		 * t2 is 7 times more important than t1
		 * */
		double[][] U = new double[][]{
			{1,	 0.5, 0.1},
			{2,	 1,	  0.142},
			{10, 7,	  1}
		};
		UrgencyMatrix m = new UrgencyMatrix("name", U);
		m.rank_tasks(null);
	}
}
