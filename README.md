# Instrucciones para levantar la API
- En la terminal ejecutar el siguiente comando: docker compose up -d (esto levanta el contenedor con una base de datos en postgres)
- Después, ejecutar el archivo con la función main: SocoblocksApplication

## Ejemplo para Postman
- Poner el tipo de método POST
- En la ruta colocar lo siguiente: http://localhost:8080/api/auth/register
- En el body: 
{
  "username": "usuario",
  "password": "password"
}
- Después, hacer login: http://localhost:8080/api/auth/login
- Usar el mismo body con usuario y contraseña
