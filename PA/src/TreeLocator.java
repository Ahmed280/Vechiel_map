
public class TreeLocator<T> implements Locator<T> {
	private TLNode<T> root, current;

	public TreeLocator() {
		root = current = null;
	}

	@Override
	public int add(T e, Location loc) {
		Pair<Boolean, Integer> x = findI(loc);
		TLNode<T> p = current, q = current;
		if (x.first) {
			p.addData(e);
			return x.second;
		}
		q = new TLNode<T>(loc, e);
		if (root == null) {
			root = current = q;
			return x.second;
		}
		if (loc.x < current.lo.x && loc.y <= current.lo.y) {
			current.c1 = q;
		} else if (loc.x <= current.lo.x && loc.y > current.lo.y) {
			current.c2 = q;
		} else if (loc.x > current.lo.x && loc.y >= current.lo.y) {
			current.c3 = q;
		} else {
			current.c4 = q;
		}

		return x.second;
	}

	@Override
	public Pair<List<T>, Integer> get(Location loc) {
		Pair<Boolean, Integer> x = find(loc);
		Pair<List<T>, Integer> y;
		if(!x.first) {
			y=new Pair<List<T>, Integer>(new LinkedList<T>(),x.second);
			return y;
			}
		TLNode<T> p=current;
		if(p.data.empty()){
			y=new Pair<List<T>, Integer>(new LinkedList<T>(),x.second);
			return y;
			}
		y=new Pair<List<T>, Integer>(p.data,x.second);
		
		return y;
	}

	@Override
	public Pair<Boolean, Integer> remove(T e, Location loc) {
		Pair<Boolean, Integer> x = find(loc);
		if(!x.first) {
			return x;
		}
		TLNode<T> p = current;
		if(!search(p.data,e)) {
			x.first=false;
			return x;
		}
		while(search(p.data,e)) {
			p.data.remove();
		}
		x.first=true;
		return x;
	}
	private boolean search(List<T> l,T data) {
		if(l.empty())
			return false;
		
		l.findFirst();
		while(!l.last()) {
			if(l.retrieve()==data)
				return true;
			l.findNext();
		}
			if(l.retrieve()==data)
				return true;
			return false;
	}

	@Override
	public List<Pair<Location, List<T>>> getAll() {
		List<Pair<Location, List<T>>> l=new LinkedList<Pair<Location, List<T>>>();
		TLNode<T> p=root;
		getAll(p,l);
		return l;
	}
	private void getAll(TLNode<T> p,List<Pair<Location, List<T>>> l) {
		if(p==null)
			return;
		Pair<Location, List<T>> x=new Pair<Location, List<T>>(p.lo,p.data);
		l.insert(x);
		getAll(p.c1,l);
		getAll(p.c2,l);
		getAll(p.c3,l);
		getAll(p.c4,l);
		
		
	}

	@Override
	public Pair<List<Pair<Location, List<T>>>, Integer> inRange(Location lowerLeft, Location upperRight) {
		Pair<List<Pair<Location, List<T>>>, Integer> x=new Pair<List<Pair<Location, List<T>>>, Integer>(new LinkedList<Pair<Location, List<T>>>(),0);
		TLNode<T> p=root;
		inRange(x,lowerLeft,upperRight,p);
		
		return x;
	}
	private void inRange(Pair<List<Pair<Location, List<T>>>, Integer> x,Location lowerLeft, Location upperRight,TLNode<T> p) {
		if(p==null)
			return;
		
		if(p.lo.x>upperRight.x && p.lo.y>upperRight.y) {
			x.second++;
			inRange(x,lowerLeft,upperRight,p.c1);
			
		}
		else if(p.lo.x>=lowerLeft.x && p.lo.y>=lowerLeft.y && p.lo.x<=upperRight.x && p.lo.y<=upperRight.y) {
			x.second++;
			x.first.insert(new Pair<Location, List<T>>(p.lo,get(p.lo).first));
			inRange(x,lowerLeft,upperRight,p.c1);
			inRange(x,lowerLeft,upperRight,p.c2);
			inRange(x,lowerLeft,upperRight,p.c3);
			inRange(x,lowerLeft,upperRight,p.c4);
		}
			
		
		else if(p.lo.x>upperRight.x && p.lo.y<lowerLeft.y) {
			x.second++;
			inRange(x,lowerLeft,upperRight,p.c2);
		}
		else if(p.lo.x<lowerLeft.x && p.lo.y<lowerLeft.y) {
			x.second++;
			inRange(x,lowerLeft,upperRight,p.c3);
		}
		else if(p.lo.x<lowerLeft.x && p.lo.y>upperRight.y) {
			x.second++;
			inRange(x,lowerLeft,upperRight,p.c4);
		}
		else if(p.lo.x>=lowerLeft.x && p.lo.y>upperRight.y) {
			x.second++;
			if(p.c1!=null && p.c1.lo.x>=lowerLeft.x || p.c1!=null&&(p.c1.c3!=null|| p.c1.c4!=null))
				inRange(x,lowerLeft,upperRight,p.c1);
			if(p.c4!=null && p.c4.lo.x<=upperRight.x || p.c4!=null&&(p.c4.c1!=null || p.c4.c2!=null))
				inRange(x,lowerLeft,upperRight,p.c4);
		}
		else if(p.lo.x>=lowerLeft.x && p.lo.y<lowerLeft.y) {
			x.second++;
			if(p.c2!=null && p.c2.lo.x>=lowerLeft.x || p.c2!=null&&(p.c2.c3!=null|| p.c2.c4!=null))
				inRange(x,lowerLeft,upperRight,p.c2);
			if(p.c3!=null && p.c3.lo.x<=upperRight.x || p.c3!=null&&(p.c3.c1!=null || p.c3.c2!=null))
				inRange(x,lowerLeft,upperRight,p.c3);
		}
		else if(p.lo.x>upperRight.x && p.lo.y>=lowerLeft.y) {
			x.second++;
			if(p.c1!=null && p.c1.lo.y>=lowerLeft.y || p.c1!=null&&(p.c1.c2!=null || p.c1.c3!=null))
				inRange(x,lowerLeft,upperRight,p.c1);
			if(p.c2!=null && p.c2.lo.y<=upperRight.y ||p.c2!=null&&(p.c2.c1!=null ||  p.c2.c4!=null))
				inRange(x,lowerLeft,upperRight,p.c2);
		}
		else if(p.lo.x<lowerLeft.x && p.lo.y>=lowerLeft.y) {
			x.second++;
			if(p.c3!=null && p.c3.lo.y>=lowerLeft.y || p.c3!=null&&(p.c3.c2!=null || p.c3.c3!=null))
				inRange(x,lowerLeft,upperRight,p.c3);
			if(p.c4!=null && p.c4.lo.y<=upperRight.y || p.c4!=null&&(p.c4.c1!=null || p.c4.c4!=null))
				inRange(x,lowerLeft,upperRight,p.c4);
		}
		else {
			inRange(x,lowerLeft,upperRight,p.c1);
			inRange(x,lowerLeft,upperRight,p.c2);
			inRange(x,lowerLeft,upperRight,p.c3);
			inRange(x,lowerLeft,upperRight,p.c4);
		}
		
	}

	private Pair<Boolean, Integer> findI(Location loc) {
		Integer count = 0;
		Pair<Boolean, Integer> x = new Pair<Boolean, Integer>(false, 0);
		TLNode<T> p = root, q = root;
		while (p != null) {
			count++;
			q = p;
			if (loc.x == p.lo.x && loc.y == p.lo.y) {
				
				current = p;
				x.first = true;
				x.second = count;
				return x;
			} else {
				if (loc.x < p.lo.x && loc.y <= p.lo.y) {
					p = p.c1;
				} else if (loc.x <= p.lo.x && loc.y > p.lo.y) {
					p = p.c2;
				} else if (loc.x > p.lo.x && loc.y >= p.lo.y) {
					p = p.c3;
				} else if(loc.x>=p.lo.x && loc.y<p.lo.y){
					p = p.c4;
				}
				else
					break;
			}
		}
		current = q;
		x.first = false;
		x.second = count;
		return x;

	}
	private Pair<Boolean, Integer> find(Location loc) {
		Integer count = 0;
		Pair<Boolean, Integer> x = new Pair<Boolean, Integer>(false, 0);
		TLNode<T> p = root;
		while (p != null) {
			count++;
			if (loc.x == p.lo.x && loc.y == p.lo.y) {
				current = p;
				x.first = true;
				x.second = count;
				return x;
			} else {
				if (loc.x < p.lo.x && loc.y <= p.lo.y) {
					p = p.c1;
				} else if (loc.x <= p.lo.x && loc.y > p.lo.y) {
					p = p.c2;
				} else if (loc.x > p.lo.x && loc.y >= p.lo.y) {
					p = p.c3;
				} else if(loc.x>=p.lo.x && loc.y<p.lo.y){
					p = p.c4;
				}
				else
					break;
			}
		}
		x.first = false;
		x.second = count;
		return x;

	}

}
