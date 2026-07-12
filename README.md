# Sistema de Gestion de Finanzas Personales

Proyecto final del curso de Algoritmos y Estructuras de Datos.

Este proyecto implementa un sistema de finanzas personales en Java. Permite registrar ingresos, registrar gastos, organizar movimientos por categoria, consultar historial, buscar transacciones, eliminar movimientos, procesar pagos pendientes y ver estadisticas mensuales.

La finalidad principal del proyecto es demostrar el uso practico de estructuras de datos implementadas manualmente, sin depender de `ArrayList`, `LinkedList`, `Stack` ni `Queue` de Java.

## Objetivo Del Proyecto

Desarrollar una aplicacion de escritorio para administrar movimientos financieros personales, aplicando estructuras de datos vistas en el curso para resolver operaciones reales del sistema:

- Guardar ingresos y gastos.
- Clasificar movimientos por categorias.
- Buscar movimientos por identificador.
- Eliminar movimientos registrados.
- Ordenar movimientos por monto.
- Mostrar resumen mensual de ingresos y gastos.
- Procesar pagos pendientes en orden de llegada.
- Consultar la ultima accion realizada.

## Ejecucion Del Proyecto

### Ejecutar Interfaz Grafica

En Windows se puede ejecutar:

```bat
run.bat
```

Tambien se puede compilar y ejecutar manualmente:

```bat
javac -d bin src\Principal.java src\view\*.java src\estructuras\*.java src\modelo\*.java src\servicio\*.java
java -cp bin Principal
```

Al iniciar, se muestra una ventana de login simple. Solo se valida que el correo tenga formato basico y que la contrasena no este vacia.

### Ejecutar Modo Consola

El menu anterior por consola sigue disponible:

```bat
java -cp bin Principal consola
```

## Organizacion Del Codigo

- `src/Principal.java`: punto de inicio del programa. Abre la interfaz grafica o el modo consola.
- `src/view`: contiene las ventanas Swing del sistema.
- `src/modelo`: contiene las clases de datos del sistema financiero.
- `src/servicio`: contiene la logica principal de negocio.
- `src/estructuras`: contiene las estructuras de datos implementadas manualmente.

## Funcionalidades Principales

- Login simple antes de entrar al sistema.
- Registro de ingresos.
- Registro de gastos.
- Validacion de campos vacios antes de guardar movimientos.
- Separacion de categorias de ingreso y categorias de gasto.
- Historial de movimientos en una ventana propia.
- Eliminacion de movimientos seleccionando una fila del historial.
- Consulta de categorias.
- Consulta de estadisticas mensuales.
- Busqueda de movimientos por ID.
- Ordenamiento de movimientos por monto.
- Procesamiento de pagos pendientes.
- Consulta de la ultima accion realizada.

## Modelo Del Sistema

### `Transaccion`

Representa un movimiento financiero. Tiene:

- ID.
- Tipo: `INGRESO` o `GASTO`.
- Categoria.
- Monto.
- Mes.
- Descripcion.

Implementa `Comparable<Transaccion>` para permitir ordenar movimientos por monto.

### `Categoria`

Representa una categoria financiera. Tiene:

- Nombre.
- Limite mensual.
- Tipo: `INGRESO` o `GASTO`.

Esto permite evitar casos incorrectos, por ejemplo registrar un ingreso con la categoria `Comida`.

### `TareaPrioridad`

Representa una tarea con prioridad. Se conserva para demostrar la cola de prioridad como estructura adicional del curso.

## Logica Principal

La clase `GestorFinanzas` coordina todo el sistema. Cuando se registra un movimiento, esta clase:

1. Valida el tipo, categoria, monto y mes.
2. Crea una `Transaccion`.
3. La guarda en la lista principal de movimientos.
4. La agrega a la lista enlazada para poder ordenarla por monto.
5. La inserta en el arbol binario de busqueda por ID.
6. Actualiza la matriz mensual de ingresos y gastos.
7. Guarda la accion realizada en una pila.

De esta forma, una misma operacion del usuario actualiza varias estructuras de datos, cada una con un proposito distinto.

## Estructuras De Datos Usadas

### 1. `ListaArreglo`

Es un arreglo dinamico implementado manualmente.

Uso en el sistema:

- Guarda la lista principal de transacciones.
- Permite recorrer todos los movimientos.
- Permite eliminar movimientos por posicion.
- Incluye operaciones de insertar, actualizar, eliminar, copiar, fusionar y comparar tamanios.

Relacion con el curso:

- Arreglos unidimensionales.
- Arreglos dinamicos.
- Operaciones basicas sobre arreglos.
- TAD con encapsulamiento.

### 2. `ListaEnlazada`

Es una lista simple implementada con nodos.

Uso en el sistema:

- Guarda transacciones.
- Permite ordenar movimientos por monto usando `compareTo`.

Relacion con el curso:

- Listas enlazadas simples.
- Nodos y referencias.
- Recorrido secuencial.
- Ordenamiento sobre estructuras enlazadas.

### 3. `ListaDobleEnlazada`

Es una lista doblemente enlazada.

Uso en el sistema:

- Guarda las categorias financieras.
- Permite recorrer categorias hacia adelante y hacia atras.

Relacion con el curso:

- Lista doble.
- Nodo anterior y nodo siguiente.
- Recorrido bidireccional.

### 4. `PilaEnlazada`

Es una pila implementada con nodos.

Uso en el sistema:

- Guarda el historial de acciones.
- Permite consultar la ultima accion realizada.

Relacion con el curso:

- TAD pila.
- Comportamiento LIFO: ultimo en entrar, primero en salir.

### 5. `ColaEnlazada`

Es una cola implementada con nodos.

Uso en el sistema:

- Guarda pagos pendientes.
- Procesa los pagos en orden de llegada.

Relacion con el curso:

- TAD cola.
- Comportamiento FIFO: primero en entrar, primero en salir.

### 6. `MatrizDispersa`

Es una matriz bidimensional implementada para almacenar valores numericos.

Uso en el sistema:

- Tiene 12 filas, una por cada mes.
- Tiene 2 columnas: ingresos y gastos.
- Acumula el total mensual de ingresos y gastos.

Relacion con el curso:

- Arreglos bidimensionales.
- Matrices.
- Reportes numericos por fila y columna.

### 7. `ArbolBusquedaTransacciones`

Es un arbol binario de busqueda ordenado por ID.

Uso en el sistema:

- Inserta cada movimiento registrado.
- Busca transacciones por ID.
- Elimina transacciones del arbol.
- Permite recorridos inorden, preorden y postorden.

Relacion con el curso:

- Arbol binario.
- Arbol binario de busqueda.
- Recorridos de arboles.
- Busqueda estructurada.

### 8. `Visitante`

Es una interfaz usada para recorrer estructuras sin exponer sus datos internos.

Uso en el sistema:

- Permite recorrer listas y arboles.
- Facilita que la consola y la interfaz grafica reutilicen la misma logica.

Relacion con el curso:

- Interfaces.
- Separacion entre estructura y accion a ejecutar.
- Recorrido generico de estructuras.

## Estructuras Adicionales

El proyecto tambien conserva otras estructuras implementadas durante el curso:

- `ListaCircular`.
- `PilaArreglo`.
- `ColaArreglo`.
- `ColaPrioridad`.
- `ArbolAvlTransacciones`.

Estas estructuras quedan disponibles como demostracion academica o como posibles ampliaciones del sistema.

## Interfaz Grafica

La interfaz esta desarrollada con Java Swing y se encuentra en el paquete `view`.

Clases principales:

- `LoginVentana`: muestra el login simple.
- `VentanaFinanzas`: ventana principal del sistema.

La ventana principal permite:

- Registrar ingresos.
- Registrar gastos.
- Registrar pagos pendientes.
- Procesar pagos pendientes.
- Abrir historial de movimientos.
- Eliminar movimientos desde el historial.
- Ver categorias.
- Ver estadisticas.
- Buscar por ID.
- Ver movimientos ordenados por monto.
- Ver recorridos del arbol binario de busqueda.

## Validaciones Implementadas

El sistema valida:

- Que el monto sea mayor que cero.
- Que el mes este entre 1 y 12.
- Que los campos del formulario no esten vacios.
- Que una categoria de gasto no se use como ingreso.
- Que una categoria de ingreso no se use como gasto.

Ejemplo:

No se permite registrar un ingreso con categoria `Comida`, porque `Comida` es una categoria de gasto.

## Relacion Con El Silabo Del Curso

- Arreglos: `ListaArreglo`.
- Arreglos bidimensionales: `MatrizDispersa`.
- Listas simples: `ListaEnlazada`.
- Listas dobles: `ListaDobleEnlazada`.
- Pilas: `PilaEnlazada`.
- Colas: `ColaEnlazada`.
- Interfaces: `InterfazPila`, `InterfazCola` y `Visitante`.
- Arboles binarios: `ArbolBusquedaTransacciones`.
- Ordenamiento: orden por monto usando `Comparable`.
- TAD: cada estructura tiene operaciones publicas y oculta su implementacion interna.

## Guia Para Exponer Al Profesor

1. Ejecutar el programa con `run.bat`.
2. Ingresar al login con cualquier correo valido y una contrasena no vacia.
3. Mostrar el resumen principal de ingresos, gastos y movimientos.
4. Registrar un ingreso y explicar que se guarda en varias estructuras.
5. Registrar un gasto y mostrar la validacion de categorias.
6. Abrir el historial de movimientos y eliminar un movimiento seleccionado.
7. Abrir categorias y explicar la `ListaDobleEnlazada`.
8. Abrir estadisticas y explicar la `MatrizDispersa`.
9. Buscar un movimiento por ID y explicar el ABB.
10. Mostrar movimientos ordenados por monto y explicar la `ListaEnlazada`.
11. Registrar y procesar un pago pendiente para explicar la `ColaEnlazada`.
12. Ver la ultima accion para explicar la `PilaEnlazada`.

## Conclusion

El sistema no busca ser una aplicacion bancaria completa. Su objetivo es academico: demostrar como las estructuras de datos pueden resolver problemas concretos dentro de un sistema de finanzas personales.

Cada estructura fue usada con una finalidad dentro del dominio del proyecto, no solo como codigo aislado. Esto permite explicar el proyecto desde la funcionalidad y desde los temas del curso.
