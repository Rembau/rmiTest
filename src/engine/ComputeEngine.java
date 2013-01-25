package engine;
import java.rmi.*; import java.rmi.registry.LocateRegistry;
import java.rmi.server.*; import compute.*;
public class ComputeEngine extends UnicastRemoteObject  implements Compute{ 
	private static final long serialVersionUID = 1L;
	public ComputeEngine() throws RemoteException {     
		super();  
	}
	public Object executeTask(Task t) {      
	   return t.execute();  
	} 
	public static void main(String[] args) { 
	   if (System.getSecurityManager() == null){  
		   System.setSecurityManager(new RMISecurityManager()); 
	   }
	   try {
		LocateRegistry.createRegistry(2222);
	} catch (RemoteException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
	   String name = "//localhost:2222/helloObj";
	   try { 
		   Compute engine = new ComputeEngine();
           Naming.rebind(name, engine);
           System.out.println("ComputeEngine bound");
	   } catch (Exception e) { 
		   System.err.println("ComputeEngine exception: " +  e.getMessage()); 
		   e.printStackTrace();   
		   }
	   }
}
