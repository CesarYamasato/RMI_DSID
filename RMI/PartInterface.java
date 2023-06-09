package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PartInterface extends Remote{
    public String print() throws RemoteException; //Prints the Part description, id and subparts
    public String listSubParts() throws RemoteException; //Lists all the subparts
    public String getName() throws RemoteException;
    public int getSubPartSize() throws RemoteException;
}
