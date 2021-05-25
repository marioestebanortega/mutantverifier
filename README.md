# Encontrar mutantes

Aplicación que ayuda a identificar si una persona es humano o mutante basaod en su cadena de AND


## Tecnologias usadas

Java 8
Maven 3.2.1
Postgres 12.6

### IDE: IntelliJ


## Configuraciones iniciales



En el archivo "..src/main/resources/application.properties" se realizan las siguientes configuración de una base de datos creada en [heroku](https://www.heroku.com/)

```
spring.datasource.url=jdbc:postgresql://ec2-52-86-73-86.compute-1.amazonaws.com:5432/del73739lbv7ia
spring.datasource.username=***
spring.datasource.password=***
```

Ya existe una base de datos que fue creada en Heroku , sin embargo si se desea configurarlo con una base de datos locals seria así:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/basedatos
spring.datasource.username=***
spring.datasource.password=***
```


## Instalación

Use maven para configurar instalar y testear el proyecto [Maven](https://maven.apache.org/) to install foobar.

```bash
mvn clean compile package
```

#### - Al ejecutar esta sentencia se genera en la carpeta /target el archivo .jar de la aplicación
#### - Al ejecutar esta sentencia se genera el reporte de cobertura y pruebas unitarias gracias a jacoco en la carpeta /target/site/jacoco , para verlo de manera interactiva con el archivo index.html

## Ejecutar la aplicación


Tener el puerto 8080 disponible y si se requiere cambiar el puerto en el archivo application.properties agregar la siguiente linea
```
server.port=PUERTO
```
Para ejecutar la aplicación , ingresar a la carpeta /target y ejecutar el siguiente comando:

```
java -jar mutantverifier-0.0.1.jar com.mercadolibre.mutantverifier.MutantVerifierApplication
```

Una vez ejecutado el comando las urls de la aplicación son las siguientes:

##Servicio POST para verificar adn
```
http://localhost:8080/mutant
```
en el body enviar un Json con esta forma:

```
{
"dna":["ACAA",
        "AATT",
        "ATAT",
        "ATAT"]}
```

##Servicio Get para obtener estadiscticas

Este servivio se ejecuta con la siguiente url de la api

```
http://localhost:8080/stats
```


## Pruebas

Con la sentencia anterior: mvn clean compile package ya se ejecuta las pruebas unitarias, sin embargo si se requiere realizarlas nuevamente pueden ejecutar el comando : 

```
mvn test
```

# API en AWS

Los servicios de AWS para las API fueron desplegados usando Lambda de AWS y expuestos con Api Gateway de AWS

Servicio POST (Se envia en el body el mismo objeto como el mencionado anteriormente en la ejecución local)
```
https://s6x0z9rr4k.execute-api.us-east-1.amazonaws.com/dev/mutant
```

Servicio GET (se ejecuta de igual manera como en la ejecucíon local)
```
https://xxifoo33q6.execute-api.us-east-1.amazonaws.com/dev/stats
```

Para estos servicios se crearon dos proyectos statslambda para el servicio GET y mutantslambda para el servicio POST y se despliegan con la ayuda del frameworks Serverless framework


