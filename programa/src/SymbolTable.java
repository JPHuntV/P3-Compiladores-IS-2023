package src;
import java.util.HashMap;
import java.util.Map;

// Clase que representa una tabla de simbolos
public class SymbolTable {
    private Map<String, String> symbols;
    private String name;
    private String tipoRetorno;
    private int cantParametros;

    /*
     * SymbolTable
     * E::name: nombre de la tabla de simbolos
     *  tipoRetorno: tipo de retorno de la tabla de simbolos
     * S::crear una tabla de simbolos
     * R::name debe ser un nombre valido
     *  tipoRetorno debe ser un tipo valido
     * O::Crear una tabla de simbolos
     */
    public SymbolTable(String name, String tipoRetorno) {
        this.name = name;
        this.tipoRetorno = tipoRetorno;
        symbols = new HashMap<String, String>();
    }

    /*
     * addSymbol
     * E::name: nombre del simbolo
     *  type: tipo del simbolo
     * S::agregar un simbolo a la tabla de simbolos
     * R::name debe ser un nombre valido
     *  type debe ser un tipo valido
     * O::Agregar un simbolo a la tabla de simbolos
     */
    public void addSymbol(String name, String type) {
        symbols.put(name, type);
    }

    /*
     * containsSymbol
     * E::name: nombre del simbolo
     * S::verificar si la tabla de simbolos contiene un simbolo
     * R::name debe ser un nombre valido
     * O::Verificar si la tabla de simbolos contiene un simbolo
     */
    public boolean containsSymbol(String name) {
        return symbols.containsKey(name);
    }

    /*
     * getType
     * E::name: nombre del simbolo
     * S::obtener el tipo de un simbolo
     * R::name debe ser un nombre valido
     * O::Obtener el tipo de un simbolo
     */
    public String getType(String name) {
        return symbols.get(name);
    }

    /*
     * getName
     * E::-
     * S::obtener el nombre de la tabla de simbolos
     * R::-
     * O::Obtener el nombre de la tabla de simbolos
     */
    public String getName(){
        return this.name;
    }

    /*
     * getTipoRetorno
     * E::-
     * S::obtener el tipo de retorno de la tabla de simbolos
     * R::-
     * O::Obtener el tipo de retorno de la tabla de simbolos
     */
    public String getTipoRetorno(){
        return this.tipoRetorno;
    }

    /*
     * setCantParametros
     * E::cant: cantidad de parametros de la tabla de simbolos
     * S::setear la cantidad de parametros de la tabla de simbolos
     * R::cant debe ser un numero valido
     */
    public void setCantParametros(int cant){
        this.cantParametros = cant;
    }

    /*
     * getCantParametros
     * E::-
     * S::obtener la cantidad de parametros de la tabla de simbolos
     * R::-
     * O::Obtener la cantidad de parametros de la tabla de simbolos
     */
    public int getCantParametros(){
        return this.cantParametros;
    }

    /*
     * getSymbols
     * E::-
     * S::obtener los simbolos de la tabla de simbolos
     * R::-
     * O::Obtener los simbolos de la tabla de simbolos
     */
    public Map<String, String> getSymbols(){
        return this.symbols;
    }
}
