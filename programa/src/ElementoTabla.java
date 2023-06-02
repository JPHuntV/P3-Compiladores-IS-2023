package src;


// Clase que representa un elemento de una tabla de simbolos
public class ElementoTabla {
    private String name;
    private String type;
    private int size;

    /*
     * ElementoTabla
     * E::name: nombre del elemento
     *  type: tipo del elemento
     * S::crear un elemento de tabla de simbolos
     * R::name debe ser un nombre valido
     *  type debe ser un tipo valido
     * O::Crear un elemento de tabla de simbolos
     */
    public ElementoTabla(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /*
     * ElementoTabla
     * E::name: nombre del elemento
     *  type: tipo del elemento
     *  size: tamaño del elemento
     * S::crear un elemento de tabla de simbolos
     * R::name debe ser un nombre valido
     *  type debe ser un tipo valido
     *  size debe ser un tamaño valido
     * O::Crear un elemento de tabla de simbolos
     */
    public ElementoTabla(String name, String type, int size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }

    /*
     * getName
     * E::-
     * S::obtener el nombre del elemento
     * R::-
     * O::Obtener el nombre del elemento
     */
    public String getName() {
        return name;
    }

    /*
     * getType
     * E::-
     * S::obtener el tipo del elemento
     * R::-
     * O::Obtener el tipo del elemento
     */
    public String getType() {
        return type;
    }

    /*
     * getSize
     * E::-
     * S::obtener el tamaño del elemento
     * R::-
     * O::Obtener el tamaño del elemento
     */
    public int getSize(){
        return size;
    }
}
