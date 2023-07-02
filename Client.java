import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Scanner;
public class Client {
	//private static final String IP = "192.168.1.15"; // Puedes cambiar a localhost
	private static final int PUERTO = 1100; //Si cambias aquí el puerto, recuerda cambiarlo en el servidor
	
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry( PUERTO);
        ATM_Bank interfaz = (ATM_Bank) registry.lookup("Bank"); //Buscar en el registro...
        Scanner sc = new Scanner(System.in);
        int eleccion;
		String name = "",username= "", password= "";
        //float numero1, numero2, resultado = 0;
		int document_id;
		//ATM_User user;
        String menu = "\n------------------\n\n[-1] => Salir\n[0] => Apertura de Cuenta\n[1] => Realizar Transaccion\n\nElige: ";
        do {
			System.out.println("\n\nBienvenido ATM");
            System.out.println(menu);

            try {
                eleccion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                eleccion = -1;
            }

            if(eleccion != -1){

            	/*System.out.println("Ingresa el número 1: ");
            	try{
                	numero1 = Float.parseFloat(sc.nextLine());
            	}catch(NumberFormatException e){
            		numero1 = 0;
            	}

            	System.out.println("Ingresa el número 2: ");
            	try{
                	numero2 = Float.parseFloat(sc.nextLine());                    
            	}catch(NumberFormatException e){
            		numero2 = 0;
            	}*/
                switch (eleccion) {
	                case 0:
						System.out.println("Ingrese su numero de Documento de identidad: ");
						try{
    						document_id = Integer.parseInt(sc.nextLine());
    					}catch(NumberFormatException e){
    						document_id = 0;
    					}						
						if (interfaz.userExist(document_id)){
							
							
							if (interfaz.isMaxAccounts(document_id)){
								System.out.println("\n\nUsted ya ha creado el maximo de cuentas disponibles (3)\n\n");
								break;
							}else{
								if(!authUser(interfaz,sc,document_id)){
									break;
								}else{
									registryAccount(interfaz,sc,document_id);
								}

							}

						}else{							
							registryUser(interfaz,name,username,password,sc,document_id);
							System.out.println("Ahora vamos a  crear su Cuenta:\n");
							registryAccount(interfaz,sc,document_id);
							document_id=0;
							name = "";username= ""; password= "";
							//System.out.println(user.getName());
						}
						
	                    //resultado = interfaz.sumar(numero1, numero2);
	                    break;
	                case 1:
	                    //resultado = interfaz.restar(numero1, numero2);
						//System.out.println(interfaz.getName());
	                    break;
	                case 2:
	                    //resultado = interfaz.multiplicar(numero1, numero2);
	                    break;
	                case 3:
	                    //resultado = interfaz.dividir(numero1, numero2);
	                    break;
	            }

                //System.out.println("Resultado => " + String.valueOf(resultado));
                System.out.println("Presiona ENTER para continuar");
                sc.nextLine();
            }
        } while (eleccion != -1);
        sc.close();
    }

public static boolean authUser(ATM_Bank interfaz,Scanner sc,int document_id)throws RemoteException{
	String username, password;
	boolean result;
	System.out.println("\n\nIngrese sus datos porfavor:");
	System.out.println("Ingrese su username: ");
	username = sc.nextLine();
	System.out.println("Ingrese su password: ");
	password = sc.nextLine();
	result=interfaz.authenticateUser(document_id, username, password);
	return result;
}

public static void registryAccount(ATM_Bank interfaz,Scanner sc,int document_id)
throws RemoteException{
	Float amount;
	System.out.println("\n\nIngrese el  monto inicial de  la cuenta(deposito inicial):");
	amount = Float.parseFloat(sc.nextLine());
	Random random = new Random();
    StringBuilder builder = new StringBuilder();
       
    // Agregar los primeros 4 dígitos
    builder.append(random.nextInt(9000) + 1000);
        
    // Agregar los siguientes 4 dígitos
    builder.append(random.nextInt(9000) + 1000);

    String numero = builder.toString();
    int numeroDeCuenta = Integer.parseInt(numero) ;
	interfaz.createAccount(document_id,numeroDeCuenta, amount);
	System.out.println("\nSu numero de cuenta es: "+numeroDeCuenta+"\n\n");

}

public static void registryUser(ATM_Bank interfaz,String name, String username, 
String password,Scanner sc, int document_id) 
throws RemoteException{	
	System.out.println("Usted no tiene una cuenta en el sistema, complete el siguiente registro para crear su Usuario:\n");
	System.out.println("Ingrese su nombre: ");
	name = sc.nextLine();
	System.out.println("Ingrese su nombre de usuario: ");
	username = sc.nextLine();
	System.out.println("Ingrese su contrase;a: ");
	password = sc.nextLine();
	interfaz.addUser(document_id, name, username, password);
	System.out.println("Su Usuario ha sido creada exitosamente");
}
}