package src;

import java.util.Stack;

// Clase que representa una pila de tablas de simbolos
public class SymbolTableStack {
    private Stack<SymbolTable> stack;

    /*
     * SymbolTableStack
     * E::-
     * S::crear una pila de tablas de simbolos
     * R::-
     * O::Crear una pila de tablas de simbolos
     */
    public SymbolTableStack() {
        stack = new Stack<SymbolTable>();
    }

    /*
     * push
     * E::symbolTable: tabla de simbolos
     * S::agregar una tabla de simbolos a la pila
     * R::symbolTable debe ser una tabla de simbolos valida
     * O::Agregar una tabla de simbolos a la pila
     */
    public void push(SymbolTable symbolTable) {
        stack.push(symbolTable);
    }

    /*
     * pop
     * E::-
     * S::sacar una tabla de simbolos de la pila
     * R::-
     * O::Sacar una tabla de simbolos de la pila
     */
    public SymbolTable pop() {
        return stack.pop();
    }

    /*
     * peek
     * E::-
     * S::obtener la tabla de simbolos en la cima de la pila
     * R::-
     * O::Obtener la tabla de simbolos en la cima de la pila
     */
    public SymbolTable peek() {
        return stack.peek();
    }

    /*
     * isEmpty
     * E::-
     * S::verificar si la pila esta vacia
     * R::-
     * O::Verificar si la pila esta vacia
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /*
     * getStack
     * E::-
     * S::obtener la pila de tablas de simbolos
     * R::-
     * O::Obtener la pila de tablas de simbolos
     */
    public Stack<SymbolTable> getStack(){
        return this.stack;
    }
}
