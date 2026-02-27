Ingresar a customer-service:
cd customer-service
ejecutar:
.\mvnw clean package

Ingresar a account-service:
cd account-service
ejecutar: (sin test porque el test de integración requiere que esté arriba el servicio)
.\mvnw clean package -DskipTests 

Regresar al directorio raiz:
cd ..

