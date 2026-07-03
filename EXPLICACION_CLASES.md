# Explicacion de uso de cada clase

## Paquetes del proyecto

- `estructuras`: aqui estan los metodos y clases de estructuras de datos, separados de la logica real del sistema.
- `modelo`: aqui estan las clases que representan informacion financiera.
- `servicio`: aqui esta la clase que usa las estructuras para resolver el problema de finanzas.

## `Principal`

Es el punto de inicio del programa. Muestra el menu, lee los datos con `Scanner` y llama a los metodos de `GestorFinanzas`.

## `GestorFinanzas`

Es la clase controladora del sistema. Une las entidades financieras con las estructuras de datos. Por ejemplo, cuando registras un gasto, lo guarda en `ListaArreglo`, lo inserta en el ABB, lo inserta en el AVL, actualiza la matriz mensual y guarda una accion en la pila.

## `Transaccion`

Representa un ingreso o gasto. Tiene ID, tipo, categoria, monto, mes y descripcion. Implementa `Comparable` para que la lista simple y el AVL puedan ordenar movimientos por monto.

## `Categoria`

Representa una categoria como `Comida`, `Transporte` o `Servicios`. Se guarda en una lista doble para demostrar recorrido hacia adelante y hacia atras.

## `TareaPrioridad`

Representa una tarea pendiente con prioridad. Se usa en `ColaPrioridad`, donde una prioridad mayor se atiende primero.

## `ListaArreglo`

Es un arreglo dinamico propio. Internamente usa `Object[]` y aumenta su capacidad cuando se llena. En el sistema guarda los movimientos principales y demuestra insertar, actualizar, eliminar, recorrer, copiar, comparar y fusionar.

## `ListaEnlazada`

Es una lista enlazada simple. Cada nodo conoce al siguiente. En el sistema guarda movimientos y permite ordenarlos por monto sin usar `LinkedList` de Java.

## `ListaDobleEnlazada`

Es una lista doblemente enlazada. Cada nodo conoce al anterior y al siguiente. En el sistema guarda categorias y permite recorrerlas en ambos sentidos.

## `ListaCircular`

Es una lista circular. El ultimo nodo apunta nuevamente al primero. En el sistema se usa para mostrar consejos financieros por turnos.

## `PilaArreglo`

Es una pila implementada con arreglo. Implementa `InterfazPila`, por eso debe tener `apilar`, `desapilar`, `verCima` y `estaVacia`. En el sistema funciona como una pila de auditoria tecnica.

## `PilaEnlazada`

Es una pila dinamica implementada con nodos. Tambien implementa `InterfazPila`. En el sistema guarda el historial de acciones y permite ver la ultima accion registrada.

## `InterfazPila`

Es el contrato del TAD pila. Define que cualquier pila del proyecto debe saber apilar, desapilar, ver la cima y comprobar si esta vacia.

## `ColaArreglo`

Es una cola circular implementada con arreglo. Implementa `InterfazCola`, por eso debe tener `encolar`, `desencolar` y `estaVacia`. En el sistema guarda notificaciones.

## `ColaEnlazada`

Es una cola dinamica implementada con nodos. Tambien implementa `InterfazCola`. En el sistema guarda pagos pendientes y los procesa en orden de llegada.

## `ColaPrioridad`

Es una cola con prioridad implementada usando `ListaArreglo`. Implementa `InterfazCola<TareaPrioridad>`. Inserta cada tarea en su posicion segun prioridad, para que al retirar salga primero la mas importante.

## `InterfazCola`

Es el contrato del TAD cola. Define que cualquier cola del proyecto debe saber encolar, desencolar y comprobar si esta vacia.

## `MatrizDispersa`

Es una matriz bidimensional. En el sistema guarda el resumen mensual de ingresos y gastos. Tambien demuestra transpuesta, matriz triangular inferior, matriz triangular superior, matriz tridiagonal y matriz simetrica/asimetrica.

## `ArbolBusquedaTransacciones`

Es un arbol binario de busqueda por ID. Permite insertar movimientos, buscarlos rapidamente por ID, eliminarlos y recorrerlos en inorden, preorden y postorden.

## `ArbolAvlTransacciones`

Es un arbol AVL ordenado por monto. Mantiene el arbol balanceado usando rotaciones izquierda y derecha despues de insertar o eliminar.

## `Visitante`

Es una interfaz usada para recorrer estructuras. Permite que `ListaArreglo`, listas y otras clases ejecuten una accion por cada elemento sin depender de librerias externas.

