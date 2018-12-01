# CRIS
Java tomcat server with CRIS 

#Despliegue en google cloud commit 2

Para ejecutarlo:

Debes convertirlo a app engine standard y el icono del proyecto cambia y en web-inf aparece appengine-web.xml donde se definen los servicios a activar. Opciones de despliegue:

1)Local: run as app engine. Te sale la app y te lo abre: Tarda la primera vez porque se tiene  que instalar las cosas. Debes tener buena conexión sino te sale un time out
2)Nube: Derecho deploy to app engine standar. Selecciona la cuenta donde creaste el proyecto. Eliges el proyecto y le das a display

Modo de autenticación con google:
En local: No aparece la ventana de google pero me deja autenticarme
En la nube: Me aparece la autenticación con google.

Si estoy autenticado y le doy al botón de login (con los campos vacíos) comprueba que el email autenticado está en el sistema. Los campos del formulario solo se utilizan par el caso en el que sea root root

Ya no hace falta iniciar hibernate.

Atento en los sitios que hay que meter el gae_project_id (ID de la app de google)
Envío de corroes a hola@idAPP.appsportalmail.com solo funciona cuando está en la nube

#Sistema de colas y conexión API scopus

Para scopus tienes que hacerlo en localhost no desde la nube y con la vpn y la cuenta de scopus debe ser @alumnos.upm.es

Las colas se ejecutan o en local o en google
para hacerlo desde local: run run configurations app engine standard new environment GOOGLE_APPLICATION_CREDENTIALS="/home/apsv/Downloads/credentials.json"
Las credenciales las obtienes del google cloud
Mejor hazlo desde la nube
