`--``-

# Proyecto Final - Gestión de Préstamos de Computadores
# Presentado por:
# Diego Andres Cubillos
# Cristhian Camilo Fernandez
## Descripción

Este proyecto es una aplicación Android que permite gestionar el préstamo de computadores. Los usuarios pueden registrarse, iniciar sesión, registrar nuevos computadores, solicitantes, y préstamos. Utiliza **Room Database** para la persistencia de datos locales y se organiza en varias entidades como `Usuario`, `Solicitante`, `Computador` y `Prestamo`.

## Características

- **Gestión de Computadores**: Los administradores pueden registrar, editar, eliminar y consultar los computadores, usuarios, estudiantes para la hora del prestammo préstamo.
- **Interfaz de usuario**: La aplicación está construida utilizando **Jetpack Compose** y **Material Design 3**, proporcionando una interfaz moderna y fácil de usar.
- **Gestión de Solicitudes**: Los usuarios pueden gestionar los solicitantes y los préstamos asociados.
- **Búsqueda avanzada**: La funcionalidad de búsqueda permite filtrar los computadores, usuarios, estudiantes y prestampos.
- **Pantallas principales**:
  - **Login**: Los usuarios pueden iniciar sesión con su nombre de usuario y contraseña.
  - **Interfaz de resgistro**: En la cual se podran registrar las personas las cuales vayan a manejar la plataforma.
  - **Interfaz Inicial**: Pantalla principal que mostrara informacion relevante que tenga la universidad.
  - **Registro de Computadores**: Los administradores pueden registrar nuevos computadores.
  - **Registro de Solicitantes**: Los administradores pueden registrar nuevos solicitantes.
  - **Registro de Préstamos**: Se pueden registrar nuevos préstamos asociando un solicitante con un computador.
  - **Listados**: Pantallas para listar y consultar los usuarios, estudiantes, computadores y préstamos.
  - **Listado de Computadores**: Muestra los computadores registrados con opción de búsqueda, edición y eliminación.
  - **Listado de Usuarios**: Muestra los usuarios registrados con opción de búsqueda, edición y eliminación.
  - **Listado de Estudiantes**: Muestra los estudiantes registrados con opción de búsqueda, edición y eliminación.
  - **Listado de Prestamos**: Muestra los equipos que estan actualmente en calidad de prestamo registrados con opción de búsqueda, edición y eliminación.


## Estructura del Proyecto

### 1. **Base de Datos (Room Database)**

La base de datos de la aplicación está definida en la clase `AppDatabase`. Las entidades que forman la base de datos incluyen:

- **Usuario**: Representa a los usuarios del sistema.
- **Solicitante**: Representa a los solicitantes que piden computadores.
- **Computador**: Representa los computadores disponibles para préstamo.
- **Prestamo**: Registra los préstamos, asociando solicitantes y computadores con fechas de préstamo y devolución.

#### Relaciones

- **SolicitanteConUsuario**: Relaciona a los solicitantes con los usuarios que los gestionan.
- **PrestamoConDetalles**: Relaciona los préstamos con los detalles de los solicitantes y computadores.

### 2. **DAO (Data Access Object)**

El acceso a los datos se maneja a través de DAOs. Estos son los principales DAOs de la aplicación:

- **UsuarioDao**: Define las operaciones CRUD para la entidad `Usuario`. Incluye la operación de login, la cual permite validar las credenciales de los usuarios.
- **SolicitanteDao**: Permite realizar operaciones sobre los solicitantes, incluidos obtener todos los solicitantes, insertar nuevos y obtenerlos con sus usuarios asociados.
- **ComputadorDao**: Maneja las operaciones sobre los computadores, como insertar, actualizar el estado y eliminar.
- **PrestamoDao**: Permite registrar, obtener y gestionar los préstamos realizados.
- **DetallesDao**: Permite obtener información detallada de los préstamos, incluyendo las relaciones con solicitantes y computadores.

### 3. **Repositorios**

Cada repositorio maneja las operaciones de negocio y facilita el acceso a los DAOs:

- **UsuarioRepository**: Proporciona funciones para insertar usuarios, obtener todos los usuarios y realizar el login.
- **SolicitanteRepository**: Permite interactuar con los solicitantes, desde su inserción hasta la obtención de todos los solicitantes con sus usuarios asociados.
- **ComputadorRepository**: Gestiona los computadores, permitiendo su inserción, actualización y eliminación.
- **PrestamoRepository**: Maneja los préstamos, desde su inserción hasta la obtención de los préstamos por solicitante.
- **DetallesRepository**: Proporciona detalles de los préstamos con información adicional sobre los solicitantes y computadores.

### 4. **Pantallas y Navegación**

La aplicación está construida utilizando **Jetpack Compose** para la interfaz de usuario. Las principales pantallas de la aplicación incluyen:

- **Login**: Los usuarios pueden iniciar sesión con su nombre de usuario y contraseña.
- **Registro de Usuario**: Los administradores pueden registrar nuevos usuarios.
- **Interfaz Inicial**: Pantalla principal después de iniciar sesión.
- **Registro de Computadores**: Los administradores pueden registrar nuevos computadores.
- **Registro de Solicitantes**: Los administradores pueden registrar nuevos solicitantes.
- **Registro de Préstamos**: Se pueden registrar nuevos préstamos asociando a un solicitante con un computador.
- **Listado de Usuarios, Solicitantes, Computadores y Préstamos**: Vistas para listar y consultar los registros existentes.

### 5. **Navegación**

La navegación entre pantallas se gestiona mediante **Jetpack Navigation Component**. Las pantallas están configuradas de la siguiente manera:

- **Login**: Pantalla inicial donde el usuario se autentica.
- **Registro der usuarios**: permite registrar nuevos usuarios.
- **Interfaz Inicial**: Pantalla principal accesible tras el login.
- **Registro de Computadores**: Permite registrar computadores en el sistema.
- **Registro de Solicitantes**: Permite registrar solicitantes que deseen realizar préstamos.
- **Registro de Préstamos**: Permite registrar un préstamo de un computador a un solicitante.
- **Listados**: Existen varias pantallas de listado donde se pueden consultar los usuarios, solicitantes, computadores y préstamos.

## Requisitos

- Android Studio con soporte para Kotlin y Room Databas

## Dependencias

El proyecto utiliza las siguientes bibliotecas:

```gradle
dependencies {
    implementation "androidx.activity:activity-compose:1.6.0"
    implementation "androidx.compose.foundation:foundation:1.5.0"
    implementation "androidx.compose.material3:material3:1.0.0"
    implementation "androidx.compose.ui:ui:1.5.0"
    implementation "androidx.navigation:navigation-compose:2.5.3"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
}
```
