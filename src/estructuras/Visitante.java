package estructuras;

// contrato del patrón Visitor: cualquier estructura que reciba un Visitante<T> le entrega
// sus elementos uno por uno llamando a visitar(), sin exponer cómo están organizados por dentro
public interface Visitante<T> {
    void visitar(T valor);
}