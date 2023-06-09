package RMI;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.List;
import java.util.LinkedList;
import java.rmi.*;
import java.util.Scanner;
import static java.lang.System.out;

public class Client{
    private PartInterface currentPart = null;
    private ServerInterface currentRepository = null;
    private List <PartInterface> currentSubParts = new LinkedList<PartInterface>();
    private static Scanner scanner = new Scanner(System.in);

    private boolean Connect(){
      boolean returnB = false;
      out.println("Host address: ");
      String host = scanner.next();
      out.println("Port: ");
      int port = scanner.nextInt();
      System.out.println("Looking up: "+host);
      try
      {
        Registry registry = LocateRegistry.getRegistry(host, port);
        returnB = ConnectToRepo(registry);
      }
      catch(RemoteException e){
        System.out.println("Unable to connect to specified Server");
      }
      return returnB;
    }

    private boolean ConnectToRepo(Registry registry) throws RemoteException{
      boolean returnB = false;
      for(String stub : registry.list()) System.out.println(stub);
        System.out.println("Choose a repository to access");
        String name = scanner.next();
        try
        {
          currentRepository = (ServerInterface)registry.lookup(name);
          returnB = true;
        }
        catch(NotBoundException e){
          System.out.println("Unable to reach requested repository");
        }
      return returnB; 
    }

    private void printP() throws RemoteException{
      if(currentPart != null){
        System.out.println(currentPart.print());
      }
    }

    private void listParts(){

    }

    private void getRepoInfo(){

    }

    private void getPart() throws RemoteException{
      if(currentRepository != null) {
        int id = scanner.nextInt();
        currentPart = currentRepository.getp(id);
      }
    }

    private void addSubPart() throws RemoteException{
      if(currentRepository != null) {
        int id = scanner.nextInt();
        currentSubParts.add(currentRepository.getp(id));
      }
    }

    private void addPart() throws RemoteException{
      if(currentRepository != null){
        out.println("Part name: ");
        String name = scanner.next();
        out.println("Part description: ");
        String description = scanner.next();
        out.println("Part will be created using the current Sub parts list");
        currentRepository.addp(description, name, currentSubParts);
      }
    }

    private void clearSubParts(){
      currentSubParts.clear();
    }

    private static void printOptions(){
      out.println(
              "1. Connect to a different repository"+ System.lineSeparator() +
              "2. List Parts"                       + System.lineSeparator() +
              "3. Add Part"                         + System.lineSeparator() +
              "4. Get Part from id"                 + System.lineSeparator() +
              "5. Add Part to subParts list from id"
              );
    }


    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{      
          Client client = new Client();
          while(!client.Connect()){}
          boolean quit = false;

          while (!quit) {
            printOptions();
            int request = scanner.nextInt();
            switch(request){
              case 1:
                while(!client.Connect());
                break;
              case 2:
                client.listParts();
                break;
              case 3:
                client.addPart();
                break;
              case 4:
                client.getPart();
                break;
              case 5:
                client.addSubPart();
                break;
            }
          }
          scanner.close();
    }
}