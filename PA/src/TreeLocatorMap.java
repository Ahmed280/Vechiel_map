
public class TreeLocatorMap<K extends Comparable<K>> implements LocatorMap<K> {
	
	private Map<K, Location> map=new BST<K, Location>();
	private Locator<K> locator=new TreeLocator<K>();
	@Override
	public Map<K, Location> getMap() {

		return map;
	}

	@Override
	public Locator<K> getLocator() {
		// TODO Auto-generated method stub
		return locator;
	}

	@Override
	public Pair<Boolean, Integer> add(K k, Location loc) {
		Pair<Boolean, Integer> x=new Pair<Boolean, Integer>(false,0);
		x=map.insert(k, loc);
		if(x.first) 
			locator.add(k, loc);
			
		return x;
	}

	@Override
	public Pair<Boolean, Integer> move(K k, Location loc) {
		Pair<Boolean, Integer> x=new Pair<Boolean, Integer>(false,0);
		Pair<Location, Integer> y=new Pair<Location, Integer>(null,0);
		y=getLoc(k);
		x=map.remove(k);
		if(x.first) {
			x.first=locator.remove(k, y.first).first;
			x=map.insert(k, loc);
			locator.add(k, loc);
		}
		return x;
	}

	@Override
	public Pair<Location, Integer> getLoc(K k) {
		Pair<Location, Integer> x=new Pair<Location, Integer>(null,0);
		Pair<Boolean, Integer> y=new Pair<Boolean, Integer>(false,0);
		y=map.find(k);
		if(y.first) {
			x.first=map.retrieve();
			x.second+=y.second;
			return x;
		}
		x.second+=y.second;
		return x;
	}

	@Override
	public Pair<Boolean, Integer> remove(K k) {
		Pair<Boolean, Integer> x=new Pair<Boolean, Integer>(false,0);
		Pair<Location, Integer> y=new Pair<Location, Integer>(null,0);
		y=getLoc(k);
		x=map.remove(k);
		if(x.first) {
			
			x.second+=locator.remove(k, y.first).second;
		}
		return x;
	}

	@Override
	public List<K> getAll() {
		List<K> l=new LinkedList<K>();
		l=map.getAll();
		return l;
	}

	@Override
	public Pair<List<K>, Integer> getInRange(Location lowerLeft, Location upperRight) {
		Pair<List<K>, Integer> x=new Pair<List<K>, Integer>(new LinkedList<K>(),0);
		Pair<List<Pair<Location, List<K>>>, Integer> y;
		y=locator.inRange(lowerLeft, upperRight);
		x.second=y.second;
		
		if(y.first.empty())
			return x;
		y.first.findFirst();
		
		while(!y.first.last()) {
			if(!y.first.retrieve().second.empty()) {
			y.first.retrieve().second.findFirst();
			while(!y.first.retrieve().second.last()) {
			x.first.insert(y.first.retrieve().second.retrieve());
			y.first.retrieve().second.findNext();
			}
			x.first.insert(y.first.retrieve().second.retrieve());
			}
			y.first.findNext();
			
		}
		if(!y.first.retrieve().second.empty()) {
		while(!y.first.retrieve().second.last()) {
			x.first.insert(y.first.retrieve().second.retrieve());
			y.first.retrieve().second.findNext();
			}
		x.first.insert(y.first.retrieve().second.retrieve());}
			
		return x;
	}

}
