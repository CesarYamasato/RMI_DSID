package RMI;
import Repository.*;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.DatagramSocket;
import java.io.IOException;

class Server extends UnicastRemoteObject implements ServerInterface{
	
	private static Scanner scanner = new Scanner(System.in);
	private PartRepository Repository;
	private String name;

	protected Server() throws RemoteException, AlreadyBoundException {
		super();
		System.out.println("To create a new repository, type the new name and port to it");
		System.out.print("name: ");
		name = scanner.next();
		System.out.print(System.lineSeparator()+ "port: ");
		int port = scanner.nextInt();
		if (available(port)){
			Registry registry = LocateRegistry.createRegistry(port);
			registry.bind(name, this);
		}
		Repository = new PartRepository(name);
	}

	//Lista as funções disponíveis do server
	@Override
	public void printUse() throws RemoteException {
		System.out.println("Available functions: " 							+ System.lineSeparator() +
							"listp() Lists all the parts in the repository" + System.lineSeparator() +
							"getp() Gets the piece identified by the id" 	+ System.lineSeparator() +
							"addp() Adds a piece to the repository"
						  );
	}

	//Listar as peças que estão no repositório
	@Override
	public String lisp() {
		return Repository.listParts();
	}

	 //Obter uma peça com um determinado id
	@Override
	public PartInterface getp(int id) {
		return Repository.getPart(id);
	}

	//Adciona uma peça ao repositório rme
	@Override
	public boolean addp(String description, String name, List<PartInterface> list) throws RemoteException {
		return Repository.addPart(description, name, list);
	}

	//Retorna quantas peças tẽm no repositório
	@Override
	public int getSize(){
		return Repository.getSize();
	}

	//Retorna o nome do servidor
	@Override
	public String getName(){
		return this.name;
	}

	private static boolean available(int port) {
		if (port < 1 || port > 65535) {
			throw new IllegalArgumentException("Invalid start port: " + port);
		}
	
		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) {
				ds.close();
			}
	
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					/* should not be thrown */
				}
			}
		}
	
		return false;
	}
	
	static public void main(String[] args) throws RemoteException, AlreadyBoundException {
		System.setProperty("java.rmi.server.hostname","localhost");
		Server RepoServer = new Server();
		
		boolean quit = false;

		while(!quit){
			System.out.println("1. List Parts on repository" + System.lineSeparator() +
							   "2. Quit");
			int request = scanner.nextInt();
			switch(request){
				case 1:
				System.out.println(RepoServer.lisp());
				break;
				case 2:
				quit = true;
				break;
			}
		}
		scanner.close();
		System.exit(0);
	}
}