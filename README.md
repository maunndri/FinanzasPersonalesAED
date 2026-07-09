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

El programa permite registrar ingresos y gastos, buscar movimientos, eliminarlos, ordenar transacciones por monto, ver reportes mensuales, registrar y procesar pagos pendientes, consultar categorias y revisar el historial reciente de acciones.

## Clases principales

- `Principal`: contiene el menu de consola. Solo pide datos al usuario y llama a `GestorFinanzas`.
- `GestorFinanzas`: coordina todo el sistema financiero. Guarda movimientos, categorias, pagos pendientes, historial y reportes.
- `Transaccion`: representa un ingreso o gasto. Implementa `Comparable` para poder ordenarse por monto.
- `Categoria`: representa una categoria financiera con limite mensual.
- `TareaPrioridad`: representa una tarea con prioridad. Queda disponible para una ampliacion con cola prioritaria.

## Estructuras usadas en el sistema principal

- `ListaArreglo`: arreglo dinamico propio. Guarda las transacciones principales e incluye insercion, actualizacion, eliminacion, recorrido, copia, comparacion por tamanio y fusion.
- `ListaEnlazada`: lista enlazada simple con nodos y apuntadores. Guarda transacciones y permite ordenarlas por monto.
- `ListaDobleEnlazada`: lista doblemente enlazada. Guarda categorias y permite recorrerlas hacia adelante y hacia atras.
- `PilaEnlazada`: pila dinamica con nodos. Guarda el historial de acciones y permite consultar la ultima accion.
- `ColaEnlazada`: cola dinamica con nodos. Guarda pagos pendientes y los procesa en orden de llegada.
- `MatrizDispersa`: matriz bidimensional. Guarda ingresos y gastos acumulados por mes.
- `ArbolBusquedaTransacciones`: arbol binario de busqueda por ID. Permite insertar, buscar, eliminar y recorrer movimientos.
- `Visitante`: interfaz simple para recorrer estructuras sin usar librerias externas.

## Estructuras adicionales disponibles

La carpeta `src/estructuras` tambien conserva implementaciones adicionales del curso, como `ListaCircular`, `PilaArreglo`, `ColaArreglo`, `ColaPrioridad` y `ArbolAvlTransacciones`. No forman parte del flujo principal porque el objetivo actual prioriza las estructuras que tienen relacion directa con la gestion de finanzas personales.

## Relacion con los temas del silabo

- Arreglos unidimensionales: `ListaArreglo`.
- Operaciones de arreglos: insertar, actualizar, eliminar, recorrer, copiar, comparar y fusionar en `ListaArreglo`.
- Arreglos bidimensionales: `MatrizDispersa`.
- TAD: cada estructura define operaciones publicas y oculta sus nodos/datos internos.
- Lista simple: `ListaEnlazada`.
- Lista doble: `ListaDobleEnlazada`.
- Pilas: `PilaEnlazada`.
- Colas: `ColaEnlazada`.
- Interfaces: `InterfazPila` e `InterfazCola` muestran el TAD como contrato antes de cada implementacion.
- Arbol binario y ABB: `ArbolBusquedaTransacciones`.

## Uso recomendado para exponer

1. Ejecuta el programa.
2. Usa la opcion 2 para mostrar los movimientos guardados en el arreglo dinamico.
3. Usa la opcion 1 para registrar un nuevo ingreso o gasto.
4. Usa la opcion 3 para buscar por ID usando el ABB.
5. Usa la opcion 5 para ordenar por monto con la lista simple.
6. Usa la opcion 6 para mostrar la matriz mensual.
7. Usa la opcion 7 para explicar los recorridos del arbol binario de busqueda.
8. Usa la opcion 8 para registrar un pago pendiente.
9. Usa la opcion 9 para procesar pagos en orden de llegada.
10. Usa la opcion 10 para explicar el historial con pila.

