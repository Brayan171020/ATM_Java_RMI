import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

class User {
    Account[] accounts;
}

class Account implements ATM_Account{
    int number;
    float current_balance;
    Transaction[] transactions;

    public Account(int numbe, float current_balanc){
        number = numbe;
        current_balance = current_balanc;
    }

    @Override
    public void createAccount(){

    }
}

class Transaction {
    int id;
    float amount;
    LocalDateTime date;
    String description;

    public Transaction(int id, float amount, LocalDateTime date, String description){

    }
}

class ImplInterface implements Interface{
    	/*
				Sobrescribir opcionalmente los métodos que escribimos en la interfaz
        	*/
            @Override
            public float sumar(float numero1, float numero2) throws RemoteException {
                return numero1 + numero2;
            };

            @Override
            public float restar(float numero1, float numero2) throws RemoteException {
                return numero1 - numero2;
            };

            @Override
            public float multiplicar(float numero1, float numero2) throws RemoteException {
                return numero1 * numero2;
            };

            @Override
            public float dividir(float numero1, float numero2) throws RemoteException {
                return numero1 / numero2;
            };
}

public class Server {
	private static final int PUERTO = 1100; //Si cambias aquí el puerto, recuerda cambiarlo en el cliente
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Remote remote = UnicastRemoteObject.exportObject(new ImplInterface(), 0);
        Registry registry = LocateRegistry.createRegistry(PUERTO);
       	System.out.println("Servidor escuchando en el puerto " + String.valueOf(PUERTO));
        registry.bind("Calculadora", remote); // Registrar calculadora
    }
}



