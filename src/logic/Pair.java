package logic;

public class Pair<T> {

	private T a, b;
	
	public Pair(T a, T b){
		this.a = a;
		this.b = b;
	}
	
	public Pair<T> swap(){
		return new Pair<T>(b, a);
	}
	
	public T first(){
		return a;
	}
	
	public T last(){
		return b;
	}
	
	public int hashCode(){
		return a.hashCode() * b.hashCode();
	}
	
	public boolean equals(Object o){
		if(!(o instanceof Pair<?>)) return false;
		Pair<?> p = (Pair<?>)o;
		
		return (a.equals(p.first())) && (b.equals(p.last()));
	}
}
