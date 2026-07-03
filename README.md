# Sistema de Gestion de Finanzas Personales

Proyecto de consola en Java para el curso de Algoritmos y Estructuras de Datos.
No usa `ArrayList`, `LinkedList`, `Stack` ni `Queue` de Java. Las estructuras del curso estan implementadas manualmente.

## Como abrirlo en Visual Studio Code

1. Abre la carpeta `FinanzasPersonalesAED` en VS Code.
2. En la terminal integrada ejecuta:

```bash
javac -d bin src/Principal.java src/estructuras/*.java src/modelo/*.java src/servicio/*.java
java -cp bin Principal
```

En Windows tambien puedes ejecutar `run.bat`.

## Organizacion por paquetes

- `src/estructuras`: contiene las estructuras de datos implementadas a mano.
- `src/modelo`: contiene las clases que representan datos del sistema financiero.
- `src/servicio`: contiene la logica principal del sistema.
- `src/Principal.java`: contiene el menu y arranque del programa.

## Tema del sistema

El programa permite registrar ingresos y gastos, buscar movimientos, eliminarlos, ver reportes mensuales, procesar pagos pendientes y revisar operaciones internas de estructuras de datos.

## Clases principales

- `Principal`: contiene el menu de consola. Solo pide datos al usuario y llama a `GestorFinanzas`.
- `GestorFinanzas`: coordina todo el sistema financiero. Guarda movimientos, categorias, pagos pendientes, notificaciones, tareas prioritarias, historial y reportes.
- `Transaccion`: representa un ingreso o gasto. Implementa `Comparable` para poder ordenarse por monto.
- `Categoria`: representa una categoria financiera con limite mensual.
- `TareaPrioridad`: representa una tarea con prioridad para la cola prioritaria.

## Estructuras implementadas

- `ListaArreglo`: arreglo dinamico propio. Incluye insercion, actualizacion, eliminacion, recorrido, copia, comparacion por size y fusion.
- `ListaEnlazada`: lista enlazada simple con nodos y apuntadores. Incluye insercion, eliminacion, busqueda, recorrido y ordenamiento.
- `ListaDobleEnlazada`: lista doblemente enlazada. Se usa para recorrer categorias hacia adelante y hacia atras.
- `ListaCircular`: lista circular. Se usa para mostrar consejos financieros por turnos.
- `PilaArreglo`: pila implementada con arreglo. Se usa como pila tecnica de auditoria.
- `PilaEnlazada`: pila dinamica con nodos. Se usa para mostrar la ultima accion realizada.
- `ColaArreglo`: cola circular implementada con arreglo. Se usa para notificaciones.
- `ColaEnlazada`: cola dinamica con nodos. Se usa para pagos pendientes.
- `ColaPrioridad`: cola con prioridad. Atiende primero tareas financieras urgentes.
- `InterfazPila`: contrato que obliga a implementar `apilar`, `desapilar`, `verCima` y `estaVacia`.
- `InterfazCola`: contrato que obliga a implementar `encolar`, `desencolar` y `estaVacia`.
- `MatrizDispersa`: matriz bidimensional. Guarda ingresos y gastos por mes, y demuestra transposicion y simetria.
- En la opcion 15, `MatrizDispersa` tambien demuestra matriz triangular inferior, triangular superior y tridiagonal.
- `ArbolBusquedaTransacciones`: arbol binario de busqueda por ID. Permite insertar, buscar, eliminar y recorrer en preorden, inorden y postorden.
- `ArbolAvlTransacciones`: arbol AVL ordenado por monto. Inserta y elimina con balanceo y rotaciones.
- `Visitante`: interfaz simple para recorrer estructuras sin usar librerias externas.

## Relacion con los temas del silabo

- Arreglos unidimensionales: `ListaArreglo` y `PilaArreglo`.
- Operaciones de arreglos: insertar, actualizar, eliminar, recorrer, copiar, comparar y fusionar en `ListaArreglo`.
- Arreglos bidimensionales: `MatrizDispersa`.
- TAD: cada estructura define operaciones publicas y oculta sus nodos/datos internos.
- Lista simple: `ListaEnlazada`.
- Lista doble: `ListaDobleEnlazada`.
- Lista circular: `ListaCircular`.
- Pilas: `PilaArreglo` y `PilaEnlazada`.
- Colas: `ColaArreglo` y `ColaEnlazada`.
- Cola con prioridad: `ColaPrioridad`.
- Interfaces: `InterfazPila` e `InterfazCola` muestran el TAD como contrato antes de cada implementacion.
- Arbol binario y ABB: `ArbolBusquedaTransacciones`.
- Arbol AVL: `ArbolAvlTransacciones`.

## Uso recomendado para exponer

1. Ejecuta el programa.
2. Usa la opcion 2 para mostrar los movimientos guardados en el arreglo dinamico.
3. Usa la opcion 1 para registrar un nuevo ingreso o gasto.
4. Usa la opcion 3 para buscar por ID usando el ABB.
5. Usa la opcion 5 para ordenar por monto con la lista simple.
6. Usa la opcion 6 para mostrar la matriz mensual.
7. Usa la opcion 7 para explicar los recorridos de arboles y el AVL.
8. Usa la opcion 15 para explicar matrices cuadradas poco densas.
9. Usa las opciones 9, 10, 11 y 12 para demostrar cola dinamica, cola con prioridad, cola con arreglo y pila.

