# Springboot assessment

Este proyecto consiste en una aplicación SpringBoot que resuelte un problema a la hora de tener que mostrar productos con stock disponible para comprar.

Para ello se deben comprobar todas las tallas de cada producto para ver si tienen stock, y en caso de
que ninguna talla tenga stock, ese producto no deberá mostrarse en la parrilla.
Existen dos casuísticas especiales:
* La primera es cuando una talla está marcada como back soon, en este caso, aunque la talla
no tenga stock, el producto debe mostrarse igual, puesto que es un producto que va a
volver a estar a la venta cuando vuelva a entrar stock.
* La segunda es cuando un producto tiene tallas “especiales”, en este caso el producto solo
estará visible si al menos una talla especial y una no especial tiene stock (o está marcada
como back soon). Si solo tienen stock (o están marcadas como back soon) tallas de uno de
los dos grupos el producto no debe mostrarse. Esta casuística se utiliza en productos
compuestos, por ejemplo, un cojín que consta de un relleno y una funda, solo se muestra si
hay stock tanto del relleno como de la funda, si no hay stock de ninguno o solo del relleno o
  solo de la funda, entonces el cojín no se muestra.

Se quiere que ofrezca como salida **la lista de identificadores de producto, ordenados por el campo sequence,
  que cumplan las condiciones de visibilidad explicadas anteriormente y separados por comas**.

Se ha desarrollado la llamada a un endpoint GET `/view` el cual lee de tres ficheros csv previamente cargados en la carpeta resources del proyecto (**product.csv**, **size.csv** y **stock.csv**). Una vea leídas las filas de los ficheros introduce los datos de los mismos en su tabla correspondiente de base de datos PRODUCT, SIZES y STOCK.

Se ha dockerizado una imagen de base de datos Oracle de la web de Oracle Container Registry:

`$ docker run -d --name <oracle-db>
container-registry.oracle.com/database/express:21.3.0-xe`

``` sql
CREATE TABLE Product (
id varchar(255) NOT null,
seq varchar(255) NOT NULL,
PRIMARY KEY (id));

CREATE TABLE Sizes (
id varchar(255) NOT null,
productId varchar(255) NOT NULL,
backSoon varchar(5) NOT NULL,
special varchar(5) NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (productId) REFERENCES Product(id));

CREATE TABLE Stock (
id number,
sizeId varchar(255) NOT NULL,
quantity varchar(5) NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (sizeId) REFERENCES Sizes(id));
```

Para probar la ejecución del proyecto y una vez habiendo hecho el `clone` del repositorio en una carpeta, se abre el proyecto maven con el IDE que se prefiera.
Luego se compila con maven haciendo un `mvn clean install` para obtener todas las dependencias que sean necesarias.
A continuación el proyecto se puede ejecutar lanzando la función main en la clase principal org.springframework.boot.autoconfigure.SpringBootApplication.

En el fichero de propiedades `application.properties` se debe de poner el puerto que se quiera y que esté libre para poder correr la aplicación.

Una vez la ejecución ha sido correcta y tenemos nuestra aplicación ejecutándose, podemos abrir **Postman** o bien desde el mismo navegador y lanzar un GET con la dirección `http://localhost:<puerto>/api/store/view`

Muestra una salida de esta manera:

[
"5",
"1",
"4",
"3"
]

Donde aparecen los productos que se pueden visualizar y además ordenados por orden de secuencia.

