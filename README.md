# Aula-Grupo-2-Back

# Guía de instalación:
EL proyecto usa Angular 17 para el frontend y Spring Boot 3.2.5 para el backend. Para la base de datos usa PosgrestSQL y Keycloak para la autenticación
## Copia de repositorios:
git clone https://github.gsissc.myatos.net/ES-TEF-CEDEI-FORMACIONES/Aula-Grupo-2-Back.git <br>
git clone https://github.gsissc.myatos.net/ES-TEF-CEDEI-FORMACIONES/Aula-Grupo-2-Front.git
## Instalación de paquetes:
### En la carpeta del frontend (Angular) ejecutaremos el comando:
npm i

### En la carpeta del backend (Spring Boot) ejecutaremos el comando para iniciar el docker con la base de datos y el keycloak:
docker compose up -d

### Arranque de proyectos:
Arrancamos el proyecto backend en el IDE y luego para el frontend usaremos el comando ng serve -o en el direcctorio del proyecto frontend.

# Crear un aula virtual donde se puedan realizar cursos y llevar a cabo una gestión adecuada de los mismos.​

Esta plataforma estará destinada tanto a profesores como a alumnos, brindando a los profesores la capacidad de crear, editar y eliminar cursos según sea necesario.​

Además, los profesores podrán llevar a cabo reuniones para aclarar dudas o explicar conceptos.​

# Características

Tecnologías:​

Front: Angular 17​

Back: Spring​

BBDD: Oracle o Postgre​

Extras:​

Docuementar la api (Swagger) ​

Seguimiento de tareas con Git ​

Realizar pruebas unitarias (cobertura mínima de 60% ) y SonarQube (sin incidencias críticas ni bloqueantes )​

Tener en cuenta la eficiencia de la página​

Hacer readme​

Hacer Wiki en git ​

# Requerimiento 1: Definición de la estructura de los datos y organización de tareas ​

Descripción:​

Crear la estructura de la base de datos (campos, tablas,...) e invetigar mejoras y optimización de la misma. Llevar al dia las tareas de cada uno, actulizaciónes de las mismas.​

Criterios de aceptación:​

Generar pdf con la estructura de bbdd, y tareas (con sus correspondientes horas).​

Escoger Team Leader​

# Requerimiento 2: Login/Register con Keylcloak​

Descripción:​

Realizar un login/register con keylcloak, dicho login/register permitirá acceder al perfil del usuario, cursos, … ​

Criterios de aceptación:​

Usar kelycloak​

Comprobar que el correo es real​

# Requerimiento 3: Roles​

Descripción:​

Crear rol de profesor y alumno​

Criterios de aceptación:​

Los roles se gestionan en BBDD

# Requerimiento 4: Reuniones​

Descripción:​

El profesor podrá realizar llamadas a alumnos, a través de Zoom. La llamada será por curso, el profesor será el que indicará quien son los que se unen, se avisará a los alumos por correo, y en el calendario de outlook se verá la reunión ​

Criterios de aceptación:​

Usar zoom​

Avisar al usuario por correo y que en su calendario se vea la reunión​

Solo se unen a la llamada las personas que estén inscritas en el curso 

# Requerimiento 5: Crearción, edición y elimnación de los cursos​

Descripción:​

El porfesor podrá crear, editar y eliminar cursos. Los cursos tendrán contenido multimedia incluidos, se deberá indicar el porcentaje del curso que llevan realizado, el usuario puede guardar los cursos​

Criterios de aceptación:​

Poder crear, editar y eliminar (notificar al alumno de que se eliminó el curso)​

El procentaje se tiene que actualizar sin recargar la página ​

# Requerimiento 6: Perfil de usuario​

Descripción:​

El usuario podrá ver su información y editarla, los cursos que tiene en curso, los finalizados y los que tiene guardados​

Criterios de aceptación:​

El correo no se puede editar, el resto si​

Hacer un distinción entre cursos gratis y de pago​

# Requerimiento 7: Cursos de pago​

Descripción:​

Hay algunos cursos que son de pago, en dichos cursos se podrá hacer un preview y si quieres puede comprarlos, la compra se realizará con PayPal​

Criterios de aceptación:​

Notificar al usuario por correo de lo que se ha comprado, con los datos relevantes​

# Requerimiento 8: Certificado​

Descripción:​

Al terminar el curso se generar un pdf indicado que el curso ha sido competado, dicho pdf se puede descargar.​

Criterios de aceptación:​

En el pdf tiene que ir los datos relevantes, como la fecha, nombre del curso 

# Requerimiento 9: Comnetarios​

Descripción:​

El usuario podrá añadir comentarios a los cursos, tanto a nivel general como en un tema en específico, solo se puede comentar si estas inscrito en el curso.​

Criterios de aceptación:​

El usuario puede editar, crear y eliminar los comentarios.​

# Requerimiento 10: Desplegar en AWS​

Descripción:​

Desplegar TODO en aws​

Criterios de aceptación:​

Todo deplegado en AWS​
