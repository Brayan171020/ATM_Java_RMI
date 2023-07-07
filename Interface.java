
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;
import java.time.LocalDateTime;
/*
	Declarar firma de métodos que serán sobrescritos
*/
public interface Interface extends Remote {
    float sumar(float numero1, float numero2) throws RemoteException;
    float restar(float numero1, float numero2) throws RemoteException;
    float multiplicar(float numero1, float numero2) throws RemoteException;
    float dividir(float numero1, float numero2) throws RemoteException;
}


interface ATM_Bank extends Remote{
    boolean userExist (int document)throws RemoteException;
    ATM_User getUser(int document)throws RemoteException;
    void addUser(int document_id, String name, String username, String password)throws RemoteException;  
    void createAccount(int document_id, int numeroDeCuenta,float amount)throws RemoteException; 
    boolean authenticateUser(int document_id,String username, String password)throws RemoteException;
    boolean isMaxAccounts(int document_id)throws RemoteException;
    void addTransaction(int document_id,int account, float amount,LocalDateTime date, String description)
    throws RemoteException;
    float getBalance(int document_id,int account) throws RemoteException;
    void addDeposit(int to,int from,int account, int account2,float amount,LocalDateTime date, 
    String description)throws RemoteException;
    String confirmUser(int document_id, int number)throws RemoteException;
}

interface ATM_User extends Remote{
    void createUser (int document_id, String name, String username, String password)throws RemoteException;
    String getName() throws RemoteException;
    int getDocument_id() throws RemoteException;
    String getUserName() throws RemoteException;
    String getPassword() throws RemoteException;
    void addAccount(int number, float current_balance)throws RemoteException;
    boolean isMaxAccounts()throws RemoteException;
    ArrayList<ATM_Account> getAccounts() throws RemoteException;
    ATM_Account getAccount(int number) throws RemoteException;
}

interface ATM_Account extends Remote{
    void createAccount() throws RemoteException;
    ArrayList<ATM_Transaction> get5Transaction() throws RemoteException;
    void addTransaction( float amount, LocalDateTime date, String description) throws RemoteException;
    int getId()throws RemoteException;
    float getBalance()throws RemoteException;
    void addBalance(float  balance) throws RemoteException;
    void restBalance(float  balance)throws RemoteException;
    //void consultAccount() throws RemoteException;
    //void depositAccount() throws RemoteException;
    //void withdrawalAccount() throws RemoteException;
    //void transferenceAccount() throws RemoteException;
    
}

interface ATM_Transaction extends Remote{
    int getId()throws RemoteException;
    float geAmount()throws RemoteException;
    LocalDateTime getDate()throws RemoteException;
    String getDescription()throws RemoteException;
}