# Propuesta de enfoque del proyecto

## Objetivo recomendado

El proyecto puede presentarse como un **sistema de gestion de finanzas personales para registrar, consultar, organizar y analizar ingresos y gastos mensuales**.

Desde el punto de vista del curso de Algoritmos y Estructuras de Datos, el objetivo no debe ser usar todas las estructuras posibles, sino elegir las que realmente ayudan a resolver operaciones frecuentes del sistema:

- Registrar movimientos financieros.
- Buscar movimientos por identificador.
- Eliminar movimientos.
- Ordenar gastos o ingresos por monto.
- Consultar resumen mensual de ingresos y gastos.
- Administrar pagos pendientes.
- Ver historial reciente de acciones.
- Clasificar movimientos por categoria.

## Estructuras necesarias para el objetivo

### 1. ListaArreglo

Es necesaria como almacenamiento principal de transacciones.

Uso en el proyecto:

- Guardar ingresos y gastos registrados.
- Listar todos los movimientos.
- Eliminar un movimiento recorriendo por posicion.
- Demostrar operaciones de arreglo dinamico: insertar, actualizar, eliminar, copiar, fusionar y comparar tamanios.

Justificacion:

En finanzas personales se necesita una coleccion principal donde se guarden todos los movimientos. Un arreglo dinamico es una buena estructura base porque permite acceso por indice y crecimiento automatico.

### 2. ArbolBusquedaTransacciones

Es necesario para buscar transacciones por ID.

Uso en el proyecto:

- Insertar cada movimiento nuevo.
- Buscar rapidamente un movimiento por identificador.
- Eliminar del arbol cuando se elimina la transaccion.
- Mostrar recorridos inorden, preorden y postorden.

Justificacion:

En un sistema financiero, buscar por ID es una operacion comun. El arbol binario de busqueda permite relacionar directamente el tema de arboles con una necesidad real del proyecto.

### 3. ListaEnlazada

Es util para ordenar movimientos por monto.

Uso en el proyecto:

- Guardar las mismas transacciones en una lista simple.
- Ordenarlas usando `compareTo`.
- Mostrar gastos o ingresos de menor a mayor monto.

Justificacion:

Permite demostrar lista enlazada simple y ordenamiento sin depender de `ArrayList` ni `LinkedList` de Java. Esta estructura si se relaciona con una necesidad financiera: revisar montos ordenados para detectar gastos altos o bajos.

### 4. MatrizDispersa

Es necesaria para el resumen mensual.

Uso en el proyecto:

- Usar 12 filas para los meses.
- Usar 2 columnas para ingresos y gastos.
- Acumular montos por mes.
- Mostrar un reporte mensual simple.

Justificacion:

El analisis mensual es parte natural de las finanzas personales. La matriz permite conectar arreglos bidimensionales con un reporte financiero claro.

### 5. ColaEnlazada

Es necesaria si el proyecto incluye pagos pendientes.

Uso en el proyecto:

- Encolar pagos pendientes.
- Procesarlos en orden de llegada.

Justificacion:

Los pagos pendientes tienen comportamiento FIFO: el primero que se registra puede ser el primero que se procesa. Esto hace que la cola tenga sentido dentro del dominio financiero.

### 6. PilaEnlazada

Es util para historial de acciones.

Uso en el proyecto:

- Guardar acciones realizadas.
- Mostrar la ultima accion registrada.

Justificacion:

Una pila representa naturalmente el historial reciente: la ultima accion es la primera que se consulta.

### 7. ListaDobleEnlazada

Es aceptable para categorias financieras.

Uso en el proyecto:

- Guardar categorias como Sueldo, Comida, Transporte y Servicios.
- Recorrer categorias hacia adelante y hacia atras.

Justificacion:

No es tan esencial como la lista principal o el arbol, pero se puede defender porque las categorias son parte del modelo financiero y el doble recorrido permite explicar listas doblemente enlazadas.

## Estructuras que pueden quedar como demostracion secundaria

### ColaPrioridad

Puede usarse para tareas financieras urgentes, como pagar deudas, servicios o vencimientos importantes.

Es defendible, pero no indispensable. Conviene presentarla como una funcionalidad adicional:

> "El sistema atiende primero tareas financieras de mayor prioridad, por ejemplo pagos de monto alto o vencimientos urgentes."

### ColaArreglo

Actualmente se usa para notificaciones.

Es una estructura valida para demostrar cola circular con arreglo, pero no es central para el objetivo financiero. Si el profesor pide reducir el proyecto, esta puede pasar a segundo plano.

### ListaCircular

Actualmente se usa para consejos financieros rotativos.

Es creativa y funciona para demostrar lista circular, pero no es necesaria para registrar ni analizar finanzas. Conviene presentarla como complemento visual o educativo.

### PilaArreglo

Actualmente se usa como auditoria tecnica.

Es redundante porque ya existe `PilaEnlazada` para historial. Puede conservarse solo si se quiere comparar pila con arreglo contra pila enlazada.

### ArbolAvlTransacciones

Actualmente se usa para ordenar por monto y demostrar balanceo.

Es academicamente valioso, pero para el objetivo basico del sistema no es indispensable porque ya existe `ListaEnlazada.ordenar()`. Se puede justificar si el enfoque incluye:

> "Mantener consultas ordenadas por monto de forma mas eficiente y balanceada."

Si se busca simplicidad, el ABB por ID es mas facil de defender que el AVL por monto.

## Recomendacion final

Para una exposicion clara como estudiante de Ingenieria de Sistemas, conviene dividir el proyecto en dos niveles:

### Nivel principal: sistema de finanzas personales

Estas estructuras deben presentarse como parte del funcionamiento real:

- `ListaArreglo`: almacenamiento principal de transacciones.
- `ArbolBusquedaTransacciones`: busqueda por ID.
- `ListaEnlazada`: ordenamiento por monto.
- `MatrizDispersa`: resumen mensual de ingresos y gastos.
- `ColaEnlazada`: pagos pendientes.
- `PilaEnlazada`: historial de acciones.
- `ListaDobleEnlazada`: categorias.

### Nivel secundario: demostracion de temas del curso

Estas estructuras pueden mostrarse como extensiones academicas:

- `ColaPrioridad`: tareas financieras urgentes.
- `ColaArreglo`: notificaciones.
- `ListaCircular`: consejos financieros.
- `PilaArreglo`: auditoria tecnica.
- `ArbolAvlTransacciones`: balanceo y orden por monto.

## Objetivo redactado para el informe

Desarrollar un sistema de consola en Java para la gestion de finanzas personales, capaz de registrar ingresos y gastos, organizar movimientos por categoria, buscar transacciones por identificador, ordenar movimientos por monto, procesar pagos pendientes y generar un resumen mensual de ingresos y gastos, aplicando estructuras de datos implementadas manualmente como listas, pilas, colas, matrices y arboles.

## Alcance recomendado

El sistema no busca ser una aplicacion bancaria completa. Su finalidad es academica: demostrar como las estructuras de datos pueden resolver problemas concretos dentro de un contexto de finanzas personales.

Por eso, las estructuras deben explicarse desde la necesidad que resuelven, no solo desde el tema del silabo.
