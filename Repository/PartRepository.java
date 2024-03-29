package Repository;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import static java.lang.System.out;

import RMI.PartInterface;

public class PartRepository{
	private Map<Integer, Part> List;
	private String Repository;
	private int size;
	
	//Constructor
	public PartRepository(String Repository) {
		List = new HashMap<Integer, Part>();
		this.Repository = Repository;
		size = 0;
	}
	
	//Method for adding parts to the repository
	public boolean addPart(String description, String name, List<PartInterface> list) throws RemoteException {
		Integer size = this.size;
		Part part = new Part(size, Repository, description, name, list);
		Part temp = List.put(size, part);
		if(temp == null) {
			this.size++;
			out.println("added part");
			return true;
		}
		List.remove(size);
		return false;
	}
	
	//Remove part from Repository
	public boolean removePart(int id) {
		List.remove(id);
		return false;
	}
	
	public String listParts() {
		String returnString = "";
		for(Map.Entry<Integer, Part> part: List.entrySet()) {
			int id = part.getKey();
			returnString += List.get(id).print();
		}
		return returnString;
	}
	
	public Part getPart(int id) {
		return List.get(id);
	}

	public int getSize(){
		return this.List.size();
	}

}