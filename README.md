
# AREP-LAB5

# TALLER DE DE MODULARIZACIÓN CON VIRTUALIZACIÓN E INTRODUCCIÓN A DOCKER Y A AWS
# Profesor: Luis Daniel Benavides
# AREP - Arquitecturas Empresariales 2021-1

En este taller podra encontrar una aplicación web pequeña usando el micro-framework de Spark java (http://sparkjava.com/). Ademas se van a construir varios container para docker para las aplicaciónes y base datos y los desplegaremos y configuraremos en nuestra máquina local. Luego, cerremos un repositorio en DockerHub y subiremos las imagenes a los diferentes repositorios. Finalmente, crearemos una máquina virtual de en AWS, instalaremos Docker , y desplegaremos los contenedores que acabamos de crear.

## Prerrequisitos

Como primera medida se debera intalar el JDK y maven y si es necesario un editor como ECLIPSE,NETBEANS,etc. Si solo se quiere ejecutar el proyecto de manera local solo se necesitara el JDK y maven. Para el JDK JAVA se instala la versión 8, a continuacion se adjuntara el link donde muestran las instrucciones detalladas de como descargar este y ademas en esta mismo link se podra realizar la descarga y futura instalacion, de igual forma se adjunta el link de la pagina oficial de maven en el cual esta el instructivo detallado y los paquetes necesarios para la version del sistema operativo en el cual se ejecutara, y por ultimo es recomdable descargar la aplicacion git tambien se anexara el link desde la pagina oficial, se anexan los links debido a que se especifican mas en cada uno con respecto a cada sistema operativo y no uno en especifico.

### Instalacion

Descargar JDK e instrucciones de instalacion
https://www.oracle.com/technetwork/es/java/javase/downloads/index.html
Descargar maven e instrucciones de instalacion
https://maven.apache.org/download.cgi
Descargar Git e instrucciones de instalacion
https://git-scm.com/downloads

## Construccion

Este proyecto basicamente esta construido en maven y el editor que se utilizo fue Visual Studio Code, teniendo el JDK antes mencionado, especificamente se utilizo el lenguaje Java, con el mini framework Spring propio.

## Diseño

Diagramas del diseño

Diseño General y de despliegue

![alt text](https://github.com/JuanRomero11/AREP-LAB5/blob/main/images/general.PNG)

Diseño Api RoundRobin

![alt text](https://github.com/JuanRomero11/AREP-LAB5/blob/main/images/roundrobin.PNG)

Diseño Api LogServices

![alt text](https://github.com/JuanRomero11/AREP-LAB5/blob/main/images/logservices.PNG)



## Descripcion del diseño
Esta se encuentra en el documento latex aqui se encuentra con extension pdf, es el unico archivo con esta extension en este repositorio.

## Como usar el Proyecto
En este mismo repositorio puede clonar o descargar el proyecto a traves de la aplicacion git ya instalada en su computador. Recomendacion, Si se clona utilizar el comando :

        git clone https://github.com/JuanRomero11/AREP-LAB4.git
        
Despues de que el proyecto esta clonado se accede a la consola del computador en este caso accedemos a la terminal de comandos de Windows(cmd) y entramos directamente en la carpeta en donde esta nuestro proyecto y como primer paso compilamos con el comando 

        mvn package

 la clase principal como se puede evidenciar es RoundRobin si se quiere ejecutar local se ubica en el directorio raiz de RoundRobin y ejecuta el comando:

        java -cp target/classes;target/dependency/* edu.eci.escualing.arep.AppLoundRoundRobin.App

al correr debe de correr en el puerto 4567 y el resultado es el siguiente 

![alt text](https://github.com/JuanRomero11/AREP-LAB5/blob/main/images/localRoundRobin.PNG)

## Despliegue

Como esta explicito en el resumen y en el documento latex. Para realizar el depliegue en la instancia EC2 de aws se utilizo la herramienta docker, en la cual se realizaron las pruebas locales de las imagenes en los contenedores, y posteriormente estas imagenes se subieron a Docker Hub para utilizarlas en dicha instancia.

### Creacion de imagenes

- Como primer paso se crean las imagenes en docker. Con el archivo docker-compose.yml se genera la imagen de la base de datos MONGO con el siguiente comando(este comando se tiene que ejecutar en la carpeta raiz de logservices).

        docker-compose up -d

- Despues de ello se generan las iamgenes de la Api de LogServices Y RoundRobin, con los siguientes comandos(estos comandos se tiene que ejecutar en la respectiva carpeta raiz de cada proyecto).

        docker build --tag logservices .
        
        docker build --tag roundrobin .

Como resultado se obtiene.

![alt text](https://github.com/JuanRomero11/AREP-LAB5/blob/main/images/dockeLocal.PNG)


### Creacion de Containers Local

- El container de la base de datos se crea por defecto al mismo tiempo que se creo la imagen de esta. En el caso de los Containers de RoundRobin y LogServices se ejecutan los siguientes comandos(estos comando se tiene que ejecutar en la respectiva carpeta raiz de
cada proyecto).

        docker run -d -p 35001:6000 --name logservice1 logservices 
        docker run -d -p 35002:6000 --name logservice2 logservices 
        docker run -d -p 35003:6000 --name logservice3 logservices 


        docker run -d -p 35004:6000 --name roundrobin1 roundrobin

Como resultado se obtiene.

![alt text](https://github.com/JuanRomero11/AREP-LAB5/blob/main/images/dokcerLocal.PNG)

###  Añadir iamgenes en repositorio de DockerHub

Para la añadir las imagenes en un repositorio de DockerHub, para ello se debe crear localmente una imagen con el nombre del repositorio a donde se va a subir y posteriormente realizar un push para empujar la imagen al repositorio(estos comando se tiene que ejecutar en la respectiva carpeta raiz de cada proyecto).

        docker tag roundrobin juanromero31/roundrobin
        docker push juanromero31/roundrobin
        
### containers en AWS 

Al tener configurados los grupos de seguridad lso puertos e instalado docker como dice en la guia se vuelven a ajecutar los comandos para crear los containers en la instancia.




Round Robin
http://ec2-54-237-117-148.compute-1.amazonaws.com:35004/

![alt text](https://github.com/JuanRomero11/AREP-LAB5/blob/main/images/aws.png)

LogServices

http://ec2-54-237-117-148.compute-1.amazonaws.com:35001/
http://ec2-54-237-117-148.compute-1.amazonaws.com:35002/
http://ec2-54-237-117-148.compute-1.amazonaws.com:35003/

la maquina instancia no esta activa debido a las recomendaciones dadas por el profesor sobre los creditos.

## Integracion continua

[![CircleCI](https://circleci.com/gh/circleci/circleci-docs.svg?style=svg)]()

## Autor
Juan Guillermo Romero 
## License
Este codigo tiene una licencia Apache License 2.0 la cual se detalla en https://github.com/JuanRomero11/AREP-LAB3/blob/main/LICENSE
