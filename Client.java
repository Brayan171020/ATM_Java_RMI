import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Client {
	//private static final String IP = "192.168.1.15"; // Puedes cambiar a localhost
	private static final int PUERTO = 1100; //Si cambias aquí el puerto, recuerda cambiarlo en el servidor
	
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry( PUERTO);
        ATM_Bank interfaz = (ATM_Bank) registry.lookup("Bank"); //Buscar en el registro...
        Scanner sc = new Scanner(System.in);
        int eleccion,eleccion2,eleccion3, eleccion4;
		String name = "",username= "", password= "";
        //float numero1, numero2, resultado = 0;
		int document_id;
		//ATM_User user;
        String menu = "\n------------------\n\n[-1] => Salir\n[0] => Apertura de Cuenta\n[1] => Realizar Transaccion\n\nElige: ";
		String menuTrasanccion = "\n------------------\n\n[-1] => Salir\n[0] => Consulta de Cuenta\n[1] => Deposito de cuenta\n\nElige: ";
        do {
			System.out.println("\n\nBienvenido ATM");
            System.out.println(menu);
			eleccion = electionOp(sc);
            if(eleccion != -1){
                switch (eleccion) {
	                case 0:
						document_id=inDomunetId(sc);						
						if (interfaz.userExist(document_id)){							
							if (interfaz.isMaxAccounts(document_id)){
								System.out.println("\n\nUsted ya ha creado el maximo de cuentas disponibles (3)\n\n");
								break;
							}else{
								if(!authUser(interfaz,sc,document_id)){
									System.out.println("Usuario o contrase;a invalidos\n");
									break;
								}else{
									System.out.println("Ahora ingrese los datos para crear su Cuenta:\n");
									registryAccount(interfaz,sc,document_id);
								}

							}

						}else{							
							registryUser(interfaz,name,username,password,sc,document_id);
							System.out.println("Ahora ingrese los datos para crear su Cuenta:\n");
							registryAccount(interfaz,sc,document_id);
							document_id=0;
							name = "";username= ""; password= "";
							//System.out.println(user.getName());
						}
						
	                    //resultado = interfaz.sumar(numero1, numero2);
	                    break;
	                case 1:
						document_id=inDomunetId(sc);
						if (interfaz.userExist(document_id)){
							if(!authUser(interfaz,sc,document_id)){
								System.out.println("Usuario o contrase;a invalidos\n");
								break;
							}else{								
								System.out.println(menuTrasanccion);
								eleccion2 = electionOp(sc);
								if(eleccion2 != -1){
                					switch (eleccion2) {
										case 0:
											ArrayList<ATM_Account> accounts = showAccounts(interfaz,document_id,false);
											eleccion3 = electionOp(sc);
											if(eleccion3 != -1){
												switch (eleccion3) {
													case 0:
														showAccount(accounts.get(0));
														break;
													case 1:
														showAccount(accounts.get(1));
														break;
													case 2:
														showAccount(accounts.get(2));
														break;
												}
											}
											//accounts=null;
											break;
										case 1:
											ArrayList<ATM_Account> accounts1 = showAccounts(interfaz,document_id, true);
											
											eleccion3 = electionOp(sc);
											if(eleccion3 != -1){
												switch (eleccion3) {
													case 0:
														addTransaction(interfaz,document_id,sc,0);
														break;
													case 1:
														addTransaction(interfaz,document_id,sc,1);
														break;
													case 2:
														addTransaction(interfaz,document_id,sc,2);
														break;
													case 3:
															int document_id2,id_account;
															System.out.println("Ingrese los datos del otro usuario: ");
															System.out.println("Numero de Documento de identidad: ");
															try{
																document_id2 = Integer.parseInt(sc.nextLine());
															}catch(NumberFormatException e){
																document_id2 = 0;
															};
															System.out.println("Numero de Cuenta: ");
															try{
																id_account = Integer.parseInt(sc.nextLine());
															}catch(NumberFormatException e){
																id_account = 0;
															};
															if (interfaz.confirmUser(document_id2, id_account)!=null){
																System.out.println("Se realizara un deposito a "+ interfaz.confirmUser(document_id2, id_account)+"\n");
																System.out.println("[0] Confirmar\n");
																System.out.println("[1] Denegar\n\n");
																System.out.println("Elije:");
																eleccion4 = Integer.parseInt(sc.nextLine());
																if (eleccion4==0){
																	Float amount2;
																	String description;
																	System.out.println("Ingrese la cantidad a depositar:\n");
																	amount2 = Float.parseFloat(sc.nextLine());
																	System.out.println("Ingrese la Descripcion de la operacion\n");
																	description =sc.nextLine();
																	interfaz.addTransaction(document_id2, id_account, amount2, LocalDateTime.now(), description);
																}else {
																	break;
																}
															}
														break;
												}
											}
											break;

									}
									//break;
								}
							}
						}else{
							System.out.println("No existe ese Usuario el sistema:\n");
						}
	            }
                System.out.println("Presiona ENTER para continuar");
                sc.nextLine();
            }
        } while (eleccion != -1);
        sc.close();
    }

public static void addTransaction(ATM_Bank interfaz,int document_id,Scanner sc, int id_number)throws RemoteException{
	Float amount2;
	String description;
	System.out.println("Ingrese la cantidad a depositar:\n");
	amount2 = Float.parseFloat(sc.nextLine());
	System.out.println("Ingrese la Descripcion de la operacion\n");
	description =sc.nextLine();
	interfaz.addTransaction(document_id, id_number, amount2, LocalDateTime.now(), description);
	System.out.println("\n\nLa operacion ha sido realizada exitosamente\n");
	System.out.println("El nuevo balance es:"+interfaz.getBalance(document_id, id_number));
}

public static ArrayList<ATM_Account> showAccounts(ATM_Bank interfaz,int document_id, boolean i)throws RemoteException{
	ArrayList<ATM_Account> accounts;											
	accounts=interfaz.getUser(document_id).getAccounts();
	int cont = 0;
	System.out.println("Cuentas del usuario "+interfaz.getUser(document_id).getUserName()+":\n");
	System.out.println("\n[-1] => Salir\n");
	for (ATM_Account account : accounts){
		System.out.println("[3] => "+account.getId() + "\n");
		cont = cont+1;
	}
	
	if (i){
		System.out.println("["+cont+"] => Cuenta de 3ros\n");
	}
	cont = 0;
	System.out.println("\n\nElige:");
	return accounts;

}

public static void showAccount(ATM_Account account) throws RemoteException{
	System.out.println("\n\n\n Cuenta:");
	System.out.println("\nId :"+account.getId()+"\n");
	System.out.println("Balance de la cuenta: "+account.getBalance()+"\n");
	System.out.println("Ultimos 5 movimentos: \n");
	ArrayList<ATM_Transaction> trans;
	trans = account.get5Transaction();
	for (ATM_Transaction tran : trans){
		System.out.println("id: "+tran.getId()+" Monto:"+tran.geAmount()+" Fecha:"+tran.getDate());
		System.out.println("Descripcion: "+tran.getDescription()+"\n\n");
	}

}

public static int electionOp(Scanner sc){
	int eleccion;
	try {
    	eleccion = Integer.parseInt(sc.nextLine());
    } catch (NumberFormatException e) {
        eleccion = -1;
    }
	return eleccion;
}

public static int inDomunetId(Scanner sc){
	int document_id;
	System.out.println("Ingrese su numero de Documento de identidad: ");
	try{
    	document_id = Integer.parseInt(sc.nextLine());
    }catch(NumberFormatException e){
    	document_id = 0;
    }
	return document_id;
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