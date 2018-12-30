# Java Echo Algorithm using RMI.

## Metodología:
Existe una entidad llamada "server" desde la cual se realizan dos tareas, el crear los nodos del grafo mediante el uso de modelos (Pojo), cada nodo se inicia de forma independiente en distintas terminales. Además el server se ocupa de iniciar la ejecución del algoritmo de elección.                                                                                                                                                                   

## Explicacion del funcionamiento
En primera instancia el usuario escribe los comandos en distintas terminales para crear nodos

`$ java Server <Id1> <Id2,Id3,Id4> false `                                                                                                                                                                  

Si se quiere crear un nodo candidato se escribe                                                                                                                                                                                                                                                                                                           
`$ java Server <Id1> <Id2,Id3,Id4> true home/grupo5/cifrado_grupo5.txt 10.10.2.214`                                                                                

Luego desde la última terminal se ejecuta el codigo con                                                                                                                                                                                                                                                                                                   

`$ java Server Start`                                                   

Este último comando gatilla que la entidad Server ejecute el método *firstwave (id)* con el nodo candidato antes definido. Este método se dedica a que el nodo actual explore a sus vecinos mediante RMI. 
                                                                                                                                                                                                                                                                                               
Una vez que el método *firstwave (id)* llega hasta una hoja, se producen los mensajes echo con el método *echo (id)*  que se propaga en sentido contrario, donde cada nodo ejecuta el método antes mencionado si y solo si cuando el número de mensajes *num_mensaje* es igual al número de vecinos *neighborhood*.         

Cuando el mensaje echo llega hasta el nodo inicial, este queda como coordinador y se conecta con el servidor de la dirección *10.10.2.214* (puede definirse otro hardcodeando este método), usando la interfaz entregada donde se obtiene la llave de descifrado, y luego se vuelve a usar la interfaz para descifrar el mensaje. 

Como última acción el nodo coordinador vuelve a inundar la red, esta vez con la función *response (id,text)*. En donde este tiene como función el avisar a cada nodo quien es el nuevo coordinador, además cada nodo recibe el mensaje decifrado. 
