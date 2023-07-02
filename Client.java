import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
public class Client {
	//private static final String IP = "192.168.1.15"; // Puedes cambiar a localhost
	private static final int PUERTO = 1100; //Si cambias aquí el puerto, recuerda cambiarlo en el servidor
	
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry( PUERTO);
        ATM_Bank interfaz = (ATM_Bank) registry.lookup("Bank"); //Buscar en el registro...
		ATM_User interfaz2 = (ATM_User) registry.lookup("User");
        Scanner sc = new Scanner(System.in);
        int eleccion;
		String name = "",username= "", password= "";
        //float numero1, numero2, resultado = 0;
		int document_id;
		//ATM_User user;
        String menu = "\n\n------------------\n\n[-1] => Salir\n[0] => Apertura de Cuenta\n[1] => Realizar Transaccion\n[2] => Multiplicar\n[3] => Dividir\nElige: ";
        do {
			System.out.println("Bienvenido ATM");
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
							System.out.println("aun no pasa nada jajajajajaj");

						}else{							
							registryUser(interfaz,name,username,password,sc,document_id);
							
							System.out.println(interfaz.getUser(document_id).getName());
							
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