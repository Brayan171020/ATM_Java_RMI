import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.*;
import java.io.Serializable;

class Bank  implements ATM_Bank {
    private ArrayList<User> users = new ArrayList<User>();
    public boolean userExist (int document)throws RemoteException{
        for (ATM_User usuario : users) {
        if (usuario.getDocument_id() == document) {
            return true;
        }
    }
        return false;
    }

    public ATM_User getUser (int document)throws RemoteException{
        for (ATM_User usuario : users) {
            if (usuario.getDocument_id() == document) {
                return usuario;
            }
        }
        return null;        
    }


    public void addUser(int document_id, String name, String username, String password)throws RemoteException{
        User user = new User();
        user.createUser(document_id, name, username, password);
        //this.users = user;
        this.users.add(user);        
    }

    public boolean authenticateUser(int document_id,String username, String password)throws RemoteException{
        if (this.getUser(document_id).getUserName().equals(username)&& 
        this.getUser(document_id).getPassword().equals(password)){
            return true;

        }
        return false;
    }

    public boolean isMaxAccounts(int document_id)throws RemoteException{
        return this.getUser(document_id).isMaxAccounts();
    }


    public void createAccount(int document_id, int numeroDeCuenta,float amount)throws RemoteException{
        this.getUser(document_id).addAccount(numeroDeCuenta, amount);
    }
}

class User implements ATM_User, Serializable {
    int document_id;
    String name;
    String username;
    String password;
    ArrayList<Account> accounts=new ArrayList<Account>(3);

    public void createUser (int document_id, String name, String username, String password)
    throws RemoteException{
        this.document_id = document_id;
        this.name = name;
        this.username = username;
        this.password = password;   
    }

    public void addAccount(int number, float current_balance)throws RemoteException{
        Account account = new Account(number, current_balance);
        this.accounts.add(account);
    }

    public boolean isMaxAccounts()throws RemoteException{
        if (this.accounts.size()==3){
            return true;
        }else{
            return false;
        }
    }

    public String getName() {
        return this.name;
    }

    public String getUserName() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }

    public int getDocument_id(){
        return this.document_id;
    }
}

class Account implements ATM_Account{
    int number;
    float current_balance;
    ArrayList<Transaction> transactions = new ArrayList<Transaction>();

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
        this.id = id;
        this.amount = amount;   
        this.description = description;
        this.date = date;
    }
}
public class Server {
	private static final int PUERTO = 1100; //Si cambias aquí el puerto, recuerda cambiarlo en el cliente
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Remote remote = UnicastRemoteObject.exportObject(new Bank(),0);
        Registry registry = LocateRegistry.createRegistry(PUERTO);
       	System.out.println("Servidor escuchando en el puerto " + String.valueOf(PUERTO));
        registry.bind("Bank", remote); // Registrar calculadora
    }
}



