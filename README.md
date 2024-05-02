# Aula-Grupo-2-Back

# Guia de instalación
El proyecto usa Angular js para el frontend, Spring Boot para el backend, PostgresSQL para la base de datos y Keycloak para el login y register.
# Repositorios:
### 1 - Copiar repositorio del frontend 
git clone https://github.gsissc.myatos.net/ES-TEF-CEDEI-FORMACIONES/Aula-Grupo-2-Front.git <br>
### 2 . Copiar repositorio del backend 
git clone https://github.gsissc.myatos.net/ES-TEF-CEDEI-FORMACIONES/Aula-Grupo-2-Back.git .
# Instalación de paquetes:
### 1 -En la carpeta del frontend ejecutar el comando:
npm install <br>
### 2 - En la carpeta del backend necesitamos arrancar el docker con la base de datos y el keycloak para ello usaremos:
docker compose up -d
# Arrancar el proyecto:
### 1 - Usamos el comando ng serve -o en la carpeta del frontend para iniciar nuestro servidor. <br>
### 2 - Arrancamos el proyecto del backend que correrá en el puerto 8085.
