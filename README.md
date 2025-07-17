# AtsVentas2
# Introducción
En Banco Pichinchaen la parte tributaria no ha pasado la transformación digital, teniendo como base de datos herramientas como Excle que se divide en varios archivos mensuales por la gigante información que posee estos ATS y el hacer una revisión, control, chequeo resulta en una actividad fuerte y operativa.
Este insumo se necesita para pagar el impuesto a la renta, sirve para generar los Informes de Cumplimiento Tributario, los informes de precios de transferencia y finalmente sirve para las auditorías tributarias de la institución como de terceros.

# Proyecto
Este proyecto está compuesto por varios microservicios desarrollados con Spring Boot y utiliza Spring Cloud Netflix Eureka para el descubrimiento de servicios y Spring Cloud Gateway para la gestión de rutas y balanceo de carga. El microservicio principal, llamado atsventas, se encarga de la gestión y procesamiento de información relacionada con ventas ATS, incluyendo la importación de archivos XLSX, almacenamiento en una base de datos MongoDB y el envío de correos electrónicos mediante SMTP (Gmail).

# Componentes principales:

Eureka Server:
Permite el registro y descubrimiento de microservicios en el ecosistema. Los servicios se comunican entre sí usando los nombres registrados en Eureka.

Gateway:
Actúa como punto de entrada único para las peticiones externas. Redirige las solicitudes a los microservicios correspondientes según las rutas configuradas en application.yml.

Microservicio atsventas:
Expone endpoints REST para importar archivos de ventas, consultar registros, y otras operaciones relacionadas.
Utiliza MongoDB como base de datos y tiene integración para enviar correos electrónicos usando Gmail.

# Configuraciones relevantes:

MongoDB:
El microservicio atsventas se conecta a una base de datos MongoDB Atlas para almacenar la información de ventas.

Correo electrónico:
Configurado para enviar correos usando SMTP de Gmail. Es necesario usar una contraseña de aplicación si la cuenta tiene verificación en dos pasos.

Eureka y Gateway:
Los servicios se registran en Eureka y el Gateway enruta las peticiones usando balanceo de carga (lb://atsventas).

# Flujo típico de uso:

El usuario realiza una petición al Gateway (por ejemplo, /atsventas).
El Gateway enruta la petición al microservicio atsventas.
El microservicio procesa la solicitud, interactúa con MongoDB y, si es necesario, envía correos electrónicos.
La respuesta se devuelve al usuario a través del Gateway.
Tecnologías utilizadas:

Spring Boot
Spring Cloud Netflix Eureka
Spring Cloud Gateway
MongoDB Atlas
JavaMail (SMTP)
Swagger/OpenAPI (para documentación de endpoints, si está habilitado)
