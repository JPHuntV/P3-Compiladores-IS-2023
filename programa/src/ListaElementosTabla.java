package src;

import java.util.ArrayList;
import java.util.List;

// Clase que representa una lista de elementos de la tabla de simbolos
public class ListaElementosTabla {
    private List<ElementoTabla> params;

    /*
     * ListaElementosTabla
     * E::-
     * S::crear una lista de elementos de tabla de simbolos
     * R::-
     * O::Crear una lista de elementos de tabla de simbolos
     */
    public ListaElementosTabla() {
        params = new ArrayList<>();
        //params.add(param);
    }

    /*
     * addParameter
     * E::param: elemento de tabla de simbolos
     * S::agregar un elemento a la lista de elementos de tabla de simbolos
     * R::param debe ser un elemento de tabla de simbolos valido
     * O::Agregar un elemento a la lista de elementos de tabla de simbolos
     */
    public void addParameter(ElementoTabla param) {
        params.add(param);
    }
    
    /*
     * existe
     * E::nombre: nombre del elemento
     * S::verificar si existe un elemento con el nombre dado
     * R::nombre debe ser un nombre valido
     * O::Verificar si existe un elemento con el nombre dado
     */
    public String existe(String nombre){
        for (ElementoTabla elementoTabla : params) {
            if(nombre.equals(elementoTabla.getName())){
                return elementoTabla.getType();
            }
        }
        return null;
    }

    /*
     * getParams
     * E::-
     * S::obtener los parametros de la funcion
     * R::-
     * O::Obtener los parametros de la funcion
     */
    public List<ElementoTabla> getParams() {
        return params;
    }
}