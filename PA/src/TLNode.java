
public class TLNode<T> {
	
	
	public Location lo;
	public List<T> data;
	public TLNode<T> c1,c2,c3,c4;
	
	public TLNode(Location loc, T data) {
		this.lo = loc;
		this.data=new LinkedList<>();
		this.data.insert(data);
	}
	public void addData(T e) {
		this.data.insert(e);
	}
	public TLNode(Location loc, T data, TLNode<T> c1, TLNode<T> c2, TLNode<T> c3, TLNode<T> c4) {
		super();
		this.lo = loc;
		this.data.insert(data);
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
		this.c4 = c4;
	}
	
	
	

}
