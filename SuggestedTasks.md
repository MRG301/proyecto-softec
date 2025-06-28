# Suggested Tasks

A continuación se listan las tareas pendientes para completar la funcionalidad del proyecto.

1. **Servicio de Sincronización**
   - Completar `SincronizacionService.sincronizarTodo()` para traer pólizas, clientes y beneficiarios del sistema remoto y reflejarlos en la base local.
   - Manejar fallos de comunicación con el servicio remoto.

2. **Operaciones CRUD sincronizadas**
   - Implementar métodos en `SincronizacionService` para crear, actualizar y eliminar clientes, pólizas y beneficiarios tanto de forma local como remota.
   - Extender `PolizaExternalClient` con las llamadas HTTP correspondientes.

3. **Controladores y Servicios Locales**
   - Ajustar controladores y servicios para usar la lógica de sincronización al modificar datos.
   - Garantizar coherencia entre la base local y el sistema externo.

4. **Pruebas**
   - Añadir pruebas unitarias e integrales que cubran controladores y la sincronización.
   - Utilizar la configuración H2 incluida para las pruebas.

5. **Documentación**
   - Actualizar `README.md` con la explicación de la sincronización y los pasos para ejecutar la aplicación junto con el sistema remoto.

6. **Consideraciones adicionales**
   - Respetar las rutas del sistema remoto para las operaciones de clientes, pólizas y beneficiarios.
   - Revisar si existen rutas para actualizar o eliminar beneficiarios en el sistema externo y, de ser así, implementarlas.
