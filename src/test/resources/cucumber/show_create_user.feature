# language: es
Característica: Gestion de usuarios
  

  Escenario: Navegar a lista de usuarios
    Dado un usuario esta en la pagina inicial
    Cuando el usuario hace click sobre el botón de Usuarios
    Entonces se muestran todos los usuarios del sistema

  Escenario: Navegar a la creacion de usuarios
    Dado un usuario esta en la pagina lista de usuarios
    Cuando el usuario hace click sobre el botón de Crear Usuarios
    Entonces se muestra el formulario de Crear Usuarios

  Escenario: Navegar a la lista de Sorteos
    Dado un usuario esta en la pagina inicial
    Cuando el usuario hace click sobre el botón de Lista De Sorteos
    Entonces se muestra la Lista de Sorteos del sistema 

  Escenario: Navegar a la Creacion de Sorteos
    Dado un usuario esta en la pagina lista de sorteos
    Cuando el usuario hace click sobre el botón de crear Sorteo
    Entonces se muestra el formulario de Crear Sorteos 
    
  Escenario: Navegar a la lista de usuarios desde formulario de creacion de usuario
    Dado un usuario esta en la pagina creacion de usuario
    Cuando el usuario hace click sobre el botón de Usuarios
    Entonces se muestran todos los usuarios del sistema  

  