package RMI;
import java.rmi.*;
import java.util.List;

public interface ServerInterface extends Remote{
	public void printUse() throws RemoteException; //Listar as funçõs disponíveis 
	public String lisp() throws RemoteException; //Listar as peças que estão no repositório
	public PartInterface getp(int id) throws RemoteException; //Obter uma peça com um determinado id
	public boolean addp(String description, String name, List<PartInterface> list) throws RemoteException; //Adciona uma peça ao repositório rmeoto
	

	public int getSize() throws RemoteException;
	public String getName() throws RemoteException;
	//Os demais métodos exigidos são implementados pela classe client
}