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

    public  ArrayList<ATM_Account> getAccount(int document_id) throws RemoteException{
        return this.getUser(document_id).getAccounts();
    }

    public void addTransaction(int document_id,int account, float amount,LocalDateTime date, String description)
    throws RemoteException{
        ATM_User user = getUser(document_id);
        user.getAccounts().get(account).addBalance(amount);
        user.getAccounts().get(account).addTransaction(amount, date, description);
    }
    public void addDeposit(int to,int from,int account, int account2,float amount,LocalDateTime date, 
    String description)throws RemoteException{
        ATM_User user = getUser(to);
        ATM_User user2 = getUser(from);
        user.getAccounts().get(account).addBalance(amount);
        user.getAccounts().get(account).addTransaction(amount, date, description);
        user2.getAccounts().get(account2).restBalance(amount);
        user2.getAccounts().get(account2).addTransaction(-amount, date, description);
    }

    public String confirmUser(int document_id, int number)throws RemoteException{
        ATM_User user = getUser(document_id);
        String name = null;
        if (user!=null && user.getAccount(number)!=null){
            name=user.getName();
        }
        return name;
    }

    public float getBalance(int document_id,int account) throws RemoteException{
        ATM_User user = getUser(document_id);
        return user.getAccounts().get(account).getBalance();
    }
}

class User implements ATM_User, Serializable {
    int document_id;
    String name;
    String username;
    String password;
    ArrayList<ATM_Account> accounts= new ArrayList<ATM_Account>(3);

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
    public ArrayList<ATM_Account> getAccounts() throws RemoteException{
        return this.accounts;
    }

    public ATM_Account getAccount(int number) throws RemoteException{
        for (ATM_Account account : this.accounts){
            if (account.getId()==number){
                return account;
            }
        }
        return null;
    }
}

class Account implements ATM_Account, Serializable{
    int number;
    float current_balance;
    ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    public int ultimoIdGenerado = 0;

    public Account(int numbe, float current_balanc){
        number = numbe;
        current_balance = current_balanc;
    }

    @Override
    public void createAccount(){

    }
    public int getId(){
        return this.number;
    }
    public float getBalance(){
        return this.current_balance;
    }

    public void addBalance(float  balance){
        this.current_balance=this.current_balance + balance;
    }

    public void restBalance(float  balance){
        this.current_balance=this.current_balance - balance;
    }

    public void addTransaction( float amount, LocalDateTime date, String description){
        this.ultimoIdGenerado = ultimoIdGenerado +1;
        Transaction tran = new Transaction(ultimoIdGenerado,amount,date,description);
        this.transactions.add(tran);

    }

    public ArrayList<ATM_Transaction> get5Transaction()throws RemoteException{
        int numTransaction = transactions.size();
        int primerIndiceAMostrar = Math.max(0, numTransaction - 5);
        ArrayList<ATM_Transaction> ultimasTransacciones = new ArrayList<>();
        for (int i = primerIndiceAMostrar; i < numTransaction; i++) {
            ultimasTransacciones.add(transactions.get(i));
        }

        return ultimasTransacciones;
    }

}

class Transaction implements ATM_Transaction,Serializable{
    int id = 0;
    float amount;
    LocalDateTime date;
    String description;

    public Transaction(int id, float amount, LocalDateTime date, String description){
        this.id = id;
        this.amount = amount;   
        this.description = description;
        this.date = date;
    }

    public int getId(){
        return this.id;
    }
    public float geAmount(){
        return this.amount;
    }
    public LocalDateTime getDate(){
        return this.date;
    }
    public String getDescription(){
        return this.description;
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