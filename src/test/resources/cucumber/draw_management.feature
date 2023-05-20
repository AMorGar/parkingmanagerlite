# language: es
Característica: Gestion de sorteos
  
  Escenario: Navegación a la lista de sorteos
    Dado un usuario esta en la pagina inicial
    Cuando el usuario hace click sobre el botón sorteos
    Entonces esta en la pagina de lista de sorteos
  
  Escenario: Se crea un sorteo correctamente
    Dado un usuario esta pagina creacion de sorteos
    Cuando se rellena el campo descripcion
    Entonces estoy en la pagina lista de sorteos 
    Y se ha persistido el sorteo en la base de datos

  Escenario: Mostrar correctamente pagina creacion de sorteos
    Dado un usuario esta en la pagina creacion de sorteos
    Entonces el titulo es de creacion de sorteos
    Y se muestra el campo de descripcion
    Y se muestra el boton de enviar formulario de sorteos

  Escenario: Mostrar correctamente pagina lista de sorteos
    Dado un usuario esta en la lista de sorteos
    Entonces el titulo es de lista de sorteos
    Y se muestra el boton de crear sorteos