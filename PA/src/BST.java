
public class BST<K extends Comparable<K>, T> implements Map<K, T> {
	private BSTNode<K,T> root,current;
	
	public BST() {
		root = current = null;
	}
	@Override
	public boolean empty() {
		return root == null;
	}

	@Override
	public boolean full() {
		return false;
	}

	@Override
	public T retrieve() {
		return current.data;
	}

	@Override
	public void update(T e) {
		current.data=e;

	}

	@Override
	public Pair<Boolean, Integer> find(K key) {
		BSTNode<K,T> p = root;
		Pair<Boolean,Integer> x=new Pair<Boolean,Integer>(false,0) ;
		Integer count=0;
		if (empty())
			return x;
		while (p != null) {
			count++;
			if (p.key.compareTo(key)==0) {
				current = p;
				x.first=true;
				x.second=count;
				return x;
			} else if (key.compareTo(p.key)<0) {
				p = p.left;
				
			}
			else {
				
				p = p.right;
				
				
			}
			
		}
		
		x.second=count;
		
		return x;
	}

	@Override
	public Pair<Boolean, Integer> insert(K key, T data) {
		Pair<Boolean,Integer> x=find(key) ;
		BSTNode<K,T> p= current;

		if (x.first) {
			x.first=false;
			return x;
		}
		Pair<Boolean,Integer> y=findI(key) ;
		p = new BSTNode<K,T>(key,data);
		if (empty()) {
			root = current = p;
			y.first=true;
			return y;
		} else {
			
			if (key.compareTo(current.key)<0)
				current.left = p;
			else
				current.right = p;
			current = p;
			y.first=true;
			return y;
		}
	}

	@Override
	public Pair<Boolean, Integer> remove(K key) {
		Pair<Boolean, Integer> removed =find(key);
		BSTNode<K,T> p;
		p = remove_aux(key, root, removed);
		current = root = p;
		return removed;
	}

	private BSTNode<K,T> remove_aux(K key, BSTNode<K,T> p, Pair<Boolean, Integer>flag) {
		BSTNode<K,T> q, child = null;
		if (p == null)
			return null;
		if (key.compareTo(p.key)<0)
			p.left = remove_aux(key, p.left, flag); // go left
		else if (key.compareTo(p.key)>0)
			p.right = remove_aux(key, p.right, flag); // go right
		else {
			flag.first = true;
			if (p.left != null && p.right != null) { // two children
				q = find_min(p.right);
				p.key = q.key;
				p.data = q.data;
				p.right = remove_aux(q.key, p.right, flag);
			} else {
				if (p.right == null) // one child
					child = p.left;
				else if (p.left == null) // one child
					child = p.right;
				return child;
			}
		}
		return p;
	}

	private BSTNode<K,T> find_min(BSTNode<K,T> p) {
		if (p == null)
			return null;
		while (p.left != null) {
			p = p.left;
		}
		return p;
	}
	

	@Override
	public List<K> getAll() {
		List<K> l=new LinkedList<K>();
		BSTNode<K,T> p=root;
		getAll(p,l);
				
		return l;
	}
	private void getAll(BSTNode<K,T> t,List<K> l){
		
		if(t==null)
			return;
		else {
			
			getAll(t.left,l);
			l.insert(t.key);
			getAll(t.right,l);
			
		}
		
	}
	private Pair<Boolean, Integer> findI(K key) {
		BSTNode<K,T> p=root,q = root;
		Pair<Boolean,Integer> x=new Pair<Boolean,Integer>(false,0) ;
		Integer count=0;
		if (empty())
			return x;
		while (p != null) {
			q=p;
			if (p.key.compareTo(key)==0) {
				current = p;
				x.first=true;
				count++;
				x.second=count;
				return x;
			} else if (key.compareTo(p.key)<0) {
				p = p.left;
				count++;
				
			}
			else 	{	
				p = p.right;
				count++;
			}
		}
		current=q;
		x.second=count;
		
		return x;
	}
}
