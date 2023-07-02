Claro, aquí te dejo los pasos para ejecutar el servidor y el cliente del sistema de cajero automático:

## Servidor

1. Abre una terminal y ve al directorio donde están los archivos del servidor `Server.java`, `ATM_Bank.java`, `ATM_User.java`, `ATM_Account.java` y `Transaction.java`.

2. Compila los archivos con el siguiente comando:

   ````
   javac Server.java ATM_Bank.java ATM_User.java ATM_Account.java Transaction.java
   ```

3. Inicia el registro RMI con el siguiente comando:

   ````
   rmiregistry
   ```

4. Abre otra terminal y vuelve al directorio donde están los archivos compilados.

5. Inicia el servidor con el siguiente comando:

   ````
   java -Djava.rmi.server.hostname=<ip_address> Server
   ```

   Reemplaza `<ip_address>` con la dirección IP del servidor. Si estás ejecutando el servidor y el cliente en la misma máquina, puedes usar `localhost` como dirección IP.

   Nota: Si estás usando un firewall, debes abrir el puerto 1100 para permitir la comunicación RMI.

## Cliente

1. Abre una terminal y ve al directorio donde están los archivos del cliente `Client.java` y `ATM_Client.java`.

2. Compila los archivos con el siguiente comando:

   ````
   javac Client.java ATM_Client.java
   ```

3. Inicia el cliente con el siguiente comando:

   ````
   java -Djava.security.policy=policyfile -Djava.rmi.server.hostname=<ip_address> Client
   ```

   Reemplaza `<ip_address>` con la dirección IP del servidor. Si estás ejecutando el servidor y el cliente en la misma máquina, puedes usar `localhost` como dirección IP.

4. El cliente mostrará un menú con las opciones disponibles. Sigue las instrucciones en la pantalla para interactuar con el sistema de cajero automático.

Espero que esta guía te sea útil. Si tienes algún problema, no dudes en preguntar.