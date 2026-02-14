# loan-management-api
RESTful API for managing banking loan applications, clients, and payments.

Gestion de clientes `CRUD`
- Ver lista
- Agregar nuevo cliente
- Editar info de un cliente existente
- Eliminar cliente y TODAS sus solicitudes

Solicitud de prestamo
- permitir a un cliente solicitar un prestamo bancario. Debe incluir el monto solicitado, el plazo deseado y otros detalles relevantes.
- Ver la lista de solicitudes para cada cliente y su estado actual (aprobado, rechazado, en proceso)
- Aprobar o rechazar una solicitud de prestamo y registrar los detalles de la accion

Gestion de prestamos aprobados y pagos
- ver lista de prestamos aprobados de cada cliente, incluyendo los detalles del prestamo y el estado del pago
- registrar los pagos en efectivo realizados por los clientes para los prestamos aprobados
- calcular y mostrar el saldo pendiente para cada prestamo aprobado.


## 3 entidades
- `Clientes`
- `Prestamos`
- `Pago`


## docker de frontend
docker build -t my-next-app .
docker run --rm -p 3000:3000 --env-file .env.production my-next-app
