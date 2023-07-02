
import java.rmi.Remote;
import java.rmi.RemoteException;

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
}

interface ATM_User extends Remote{
    void createUser (int document_id, String name, String username, String password)throws RemoteException;
    String getName() throws RemoteException;
    int getDocument_id() throws RemoteException;
    
}

interface ATM_Account extends Remote{
    void createAccount() throws RemoteException;
    //void consultAccount() throws RemoteException;
    //void depositAccount() throws RemoteException;
    //void withdrawalAccount() throws RemoteException;
    //void transferenceAccount() throws RemoteException;
    
}