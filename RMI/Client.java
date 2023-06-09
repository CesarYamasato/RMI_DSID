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
        out.print("HELLO2");
        System.out.println(currentPart.print());
      }
    }

    private void listParts(){

    }

    private void getRepoInfo(){

    }

    private void getPart(int id) throws RemoteException{
      if(currentRepository != null) currentPart = currentRepository.getp(id);
    }

    private void addSubPart(int id, int n) throws RemoteException{
      if(currentRepository != null) currentSubParts.add(currentRepository.getp(id));
    }

    private void addPart(String name, String description) throws RemoteException{
        currentRepository.addp(description, name, currentSubParts);
    }


    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{
        String host = "";
        int port = 1099;

       if (args.length > 1)
         {
           host = args[0];
           if(args.length == 2){
            port = Integer.parseInt(args[1]);
           }
         }
      else
         {
           System.out.println("Usage: RMI.Client server");
           System.exit(1);
         }        

        // // Request a reference to the server object
        // //String name = "//"+host;
        // System.out.println("Looking up: "+host);

        // ServerInterface server = null;
      
        //   // In reality, Naming.lookup() will return an instance of
        //   // examples.rmi.RMIServer_stub.
        //   // This is typecast into the ServerInterface, which is what
        //   // specifies the available server methods.
        //   //System.setProperty("java.rmi.server.hostname","192.168.15.200");
        //   Registry registry = LocateRegistry.getRegistry(host, port);
        //   for(String stub : registry.list()){
        //     System.out.println(stub);
        //   }
        //   Scanner scanner = new Scanner(System.in);
        //   System.out.println("Choose a repository to access");
        //   String name = scanner.next();
        //   server = (ServerInterface)registry.lookup(name);
        //   scanner.close();

          Client client = new Client();
          while(!client.Connect()){}
          boolean quit = false;
          // client.addPart("cachorro", "com pulga");
          // client.getPart(0);
          // client.printP();
          // client.addSubPart(0,  1);
          // client.addPart("cachorro²", "com mais pulgas");
          // client.getPart(1);
          while (!quit) {
            client.printP();
            int request= scanner.nextInt();
            switch(request){

            }
            //server.addp("dog");
            //System.out.print(server.getp(0).print());
            //server.lisp();
            // for(PartInterface part : client.currentSubParts){
            //   client.Out.println(part.print());
            // }
          }
          scanner.close();
          // server = (ServerInterface)Naming.lookup(name);
    }
}