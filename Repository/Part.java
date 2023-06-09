package Repository;
import java.util.Map;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import RMI.PartInterface;

public class Part extends UnicastRemoteObject implements PartInterface{
	private String name;
	private String description;
	private String Repository;
	private Map<PartInterface, Integer> subParts = new HashMap<PartInterface, Integer>();
	private int id;
	
	//Constructor
	public Part(int id, String repo, String description, String name, List<PartInterface> list) throws RemoteException{
		super();
		this.id = id;
		this.Repository = repo;
		this.name = name;
		this.description = description;
		for(PartInterface part : list ){
			System.out.print("HELLO FROM PART");
			int quant = 0;
			if(subParts.get(part) != null) {quant = subParts.get(part);}
			subParts.put(part, quant+1);
		}
	}
	
	// //Method for adding sub parts
	// public void addSubPart(List<Part> subPartsList) {
	// 	for(Part subPart : subPartsList){
	// 		int quant = 0;
	// 		if(subParts.get(subPart) != null) {quant = subParts.get(subPart);}
	// 		subParts.put(subPart, quant+1);
	// 	}
	// }
	
	//Method for printing all sub parts of current part
	public String listSubParts() throws RemoteException {
		String returnStr = "";

		//System.out.println("SubParts of " + description + ":");
		//System.out.print("	"); //Sub parts are printed with spaces for readability
		for(Map.Entry<PartInterface ,Integer> partEntry : subParts.entrySet()) {
			PartInterface part = partEntry.getKey();
			int quant = partEntry.getValue();
			
			String temp = "(Primitiva)";

			returnStr += "name:     " + part.getName();
			if(part.getSubPartSize() > 0) {temp = "(Composta)";}
			returnStr += temp + System.lineSeparator();
			returnStr += "quantity: " + quant + System.lineSeparator();
		}
		return returnStr;
	}
	
	//Method for printing parts
	@Override
	public String print() {
		String temp = "(Primitiva)";
		if(subParts.size() > 0){temp = "(Composta)";}
		String returnStr = "repository:   " + Repository  + 	System.lineSeparator() +
						   "name:         " + name 		  + temp +  System.lineSeparator() +
						   "id:           " + id          +     System.lineSeparator() +
						   "description:  " + description +     System.lineSeparator() +
						   "Num.SubParts: " + subParts.size() + System.lineSeparator();		
		return returnStr;
	}

	@Override
	public String getName() throws RemoteException {
		return this.name;
	}

	@Override
	public int getSubPartSize() throws RemoteException {
		return this.subParts.size();
	}
	
	//Equals method used for the hash map
}