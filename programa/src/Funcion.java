package src;

import java.util.List;

// Clase que representa una funcion
public class Funcion {
    private String name;
    private String tipoRetorno;
    private List<ElementoTabla> parameters;

    /*
     * Funcion
     * E::name: nombre de la funcion
     *  parameters: parametros de la funcion
     *  tipoRetorno: tipo de retorno de la funcion
     *  size: tamaño del elemento
     * S::crear una funcion
     * R::name debe ser un nombre valido
     *  parameters debe ser una lista de elementos validos
     *  tipoRetorno debe ser un tipo valido
     * O::Crear una funcion
     */
    public Funcion(String name, List<ElementoTabla> parameters, String tipoRetorno) {
        this.name = name;
        this.tipoRetorno = tipoRetorno;
        this.parameters = parameters;
    }

    /*
     * getName
     * E::-
     * S::obtener el nombre de la funcion
     * R::-
     * O::Obtener el nombre de la funcion
     */
    public String getName() {
        return name;
    }

    /*
     * getTipoRetorno
     * E::-
     * S::obtener el tipo de retorno de la funcion
     * R::-
     * O::Obtener el tipo de retorno de la funcion
     */
    public String getTipoRetorno() {
        return tipoRetorno;
    }

    /*
     * getParameters
     * E::-
     * S::obtener los parametros de la funcion
     * R::-
     * O::Obtener los parametros de la funcion
     */
    public List<ElementoTabla> getParameters() {
        return parameters;
    }
}
