# language: es
Característica: Gestion de usuarios
  
  Escenario: Navegación a la lista de usuarios
    Dado un usuario esta en la pagina inicial
    Cuando el usuario hace click sobre el boton usuarios
    Entonces estamos en la pagina de lista de usuarios

  
  Escenario: Comprobar que el formulario de creación de usuarios tiene todos los elementos
    Dado un usuario esta en la pagina creación de usuarios
    Entonces se muestra el campo de correo 
    Y se muestra el campo de nombre
    Y se muestra el campo de primer apellido
    Y se muestra el campo de segundo apellido
    Y se muesra el campo de rol
    Y se muestra el boton de enviar formulario de usuarios

  Escenario: Crear un usuario correctamente
    Dado un usuario esta en la pagina creación de usuarios
    Y el correo usuario@g.educaand.com no esta asignado a otro usuario
    Cuando relleno el campo correo con usuario@g.educaand.com
    Y relleno el campo nombre con Alejandro
    Y relleno el campo primer apellido con Moreno
    Y el usuario hace click sobre el boton de guardar usuario
    Entonces estamos en la pagina de lista de usuarios
    Y se ha persistido el usuario en la base de datos

  Escenario: Crear un usuario erroneamente(correo existente)
    Dado un usuario esta en la pagina creación de usuarios
    Cuando relleno el campo correo con usuario@g.educaand.com
    Y el correo usuario@g.educaand.com si esta asignado a otro usuario
    Y relleno el campo Nombre con Alejandro
    Y relleno el campo primer apellido con Moreno
    Y el usuario hace click sobre el boton de guardar usuario
    Entonces estamos en la pagina de creacion de usuarios
    Y no se ha persistido el usuario en la base de datos

  Escenario: Crear un usuario erroneamente(correo vacio)
    Dado un usuario esta en la pagina creación de usuarios
    Y el correo usuario@g.educaand.com no esta asignado a otro usuario
    Cuando no relleno el campo correo
    Y relleno el campo nombre con Alejandro
    Y relleno el campo primer apellido con Moreno
    Y el usuario hace click sobre el boton de guardar usuario
    Entonces estamos en la pagina de creacion de usuarios
    Y no se ha persistido el usuario en la base de datos

  Escenario: Crear un usuario erroneamente(usuario vacio)
    Dado un usuario esta en la pagina de creacion de usuarios
    Y el correo usuario@g.educaand.com no esta asignado a otro usuario
    Cuando relleno el campo correo usuario@g.educaand.com
    Y relleno el campo primer apellido con Alejandro
    Y el usuario hace click sobre el boton de guardar usuario
    Entonces estamos en la pagina formulario de creacion de Usuarios
    Y no se ha persistido el usuario en la base de datos

  Escenario: Crear un usuario erroneamente(primer apellido vacio)
    Dado un usuario esta en la pagina de creacion de usuarios
    Y el correo usuario@g.educaand.com no esta asignado a otro usuario
    Cuando relleno el campo nombre con Alejandro
    Y relleno el campo correo con usuario@g.educaand.com
    Y no relleno el campo primer apellido
    Y el usuario hace click sobre el boton de guardar usuario
    Entonces estamos en la pagina formulario de creacion de Usuarios
    Y no se ha persistido el usuario en la base de datos