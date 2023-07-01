
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


interface ATM_Account extends Remote{
    void createAccount() throws RemoteException;
    //void consultAccount() throws RemoteException;
    //void depositAccount() throws RemoteException;
    //void withdrawalAccount() throws RemoteException;
    //void transferenceAccount() throws RemoteException;
    
}