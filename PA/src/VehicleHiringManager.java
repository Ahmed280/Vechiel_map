public class VehicleHiringManager {
	private LocatorMap<String> t=new TreeLocatorMap<String>();
	public VehicleHiringManager() {
	}

	// Returns the locator map.
	public LocatorMap<String> getLocatorMap() {
		return t;
	}

	// Sets the locator map.
	public void setLocatorMap(LocatorMap<String> locatorMap) {
		t=locatorMap;
	}

	// Inserts the vehicle id at location loc if it does not exist and returns true.
	// If id already exists, the method returns false.
	public boolean addVehicle(String id, Location loc) {
		Boolean x=false;
		x=t.add(id, loc).first;
		return x;
	}

	// Moves the vehicle id to location loc if id exists and returns true. If id not
	// exist, the method returns false.
	public boolean moveVehicle(String id, Location loc) {
		Boolean x=false;
		x=t.move(id, loc).first;
		return x;
	}

	// Removes the vehicle id if it exists and returns true. If id does not exist,
	// the method returns false.
	public boolean removeVehicle(String id) {
		Boolean x=false;
		x=t.remove(id).first;
		return x;
	}

	// Returns the location of vehicle id if it exists, null otherwise.
	public Location getVehicleLoc(String id) {
		Location x;
		x=t.getLoc(id).first;
		return x;
	}

	// Returns all vehicles located within a square of side 2*r centered at loc
	// (inclusive of the boundaries).
	public List<String> getVehiclesInRange(Location loc, int r) {
		Location c1=new Location((loc.x-r),(loc.y-r));
		Location c2=new Location((loc.x+r),(loc.y+r));
		List<String> l;
		l=t.getInRange(c1, c2).first;
		return l;
	}
}
