# ListaCompra_RodrigoRojasRedondo
## Esta es la segunda aplicación de tres que incluye el ejercicio Prueba de Programación Android 1:
ListaCompra:
https://github.com/Rodrivdlc/ListaCompra_RodrigoRojasRedondo.git


Esta es una aplicación de lista de compras desarrollada en Android utilizando Kotlin y Jetpack Compose. La aplicación permite a los usuarios agregar, ver y eliminar productos de una lista de compras, con detalles opcionales como cantidad y precio. La aplicación almacena los datos en una base de datos SQLite local para persistencia.

## Características

Agregar productos a la lista con nombre (obligatorio), cantidad y precio (opcional).
Visualizar la lista de productos con la cantidad y el precio de cada producto.
Eliminar productos de la lista con un simple botón.
Precio total de los productos calculado automáticamente y mostrado en la parte superior de la pantalla.
Interfaz moderna y personalizada en negro con texto blanco, usando Material Design 3 y Jetpack Compose.
Tecnologías Usadas
Kotlin: Lenguaje de programación principal.
Jetpack Compose: Biblioteca de UI declarativa de Android.
SQLite: Base de datos local para almacenamiento de datos persistentes.
Material Design 3: Para una interfaz de usuario atractiva y moderna.
Estructura del Proyecto
MainActivity.kt: Contiene la actividad principal y la interfaz de usuario, incluyendo la barra superior, lista de productos y formularios de entrada.
ShoppingListRepository.kt: Gestiona las operaciones CRUD en la base de datos SQLite.
ShoppingListDatabaseHelper.kt: Clase que define y configura la base de datos SQLite.
ShoppingItem.kt: Clase de datos para representar un producto en la lista de compras.

## Uso de SQLite
La aplicación utiliza SQLite para almacenar la lista de productos de forma persistente. Cada producto se guarda en una tabla SQLite con los siguientes campos:

id: ID único del producto (Primary Key).
name: Nombre del producto (Campo obligatorio).
quantity: Cantidad del producto (Opcional).
price: Precio del producto (Opcional).
Operaciones CRUD
El repositorio ShoppingListRepository.kt contiene las funciones necesarias para realizar operaciones CRUD en la base de datos:

addItem(item: ShoppingItem): Agrega un producto a la base de datos.
removeItem(itemId: Long): Elimina un producto de la base de datos.
getItems(): Obtiene todos los productos de la base de datos.
