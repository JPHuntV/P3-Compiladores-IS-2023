package src;

import java.util.ArrayList;

// Clase que representa un nodo del arbol sintactico abstracto
public class ASTNode {
    private String type;
    private Object value;
    private ArrayList<ASTNode> children;

    // Constructor para nodos sin valor
    public ASTNode(String type) {
        this.type = type;
        this.value = null;
        this.children = new ArrayList<>();
    }

    // Constructor para nodos con valor
    public ASTNode(String type, Object value) {
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
    }

    /*
     * setType
     * E::type: tipo de nodo
     * S::cambiar el tipo de nodo
     * R::type debe ser un tipo de nodo valido en la gramatica
     * O::Cambiar el tipo de nodo
     */
    public void setType(String type){
        this.type = type;
    }

    /*
     * addChild
     * E::child: nodo hijo
     * S::agregar un nodo hijo
     * R::child debe ser un nodo valido
     * O::Agregar un nodo hijo
     */
    public void addChild(ASTNode child) {
        this.children.add(child);
    }

    /*
     * addChildren
     * E::newChildren: nodos hijos
     * S::agregar nodos hijos
     * R::newChildren debe ser una lista de nodos validos
     * O::Agregar nodos hijos
     */
    public void addChildren(ArrayList<ASTNode> newChildren) {
        children.addAll(newChildren);
    }

    /*
     * getType
     * E::-
     * S::tipo de nodo
     * R::-
     * O::Obtener el tipo de nodo
     */
    public String getType() {
        return type;
    }

    /*
     * getValue
     * E::-
     * S::valor del nodo
     * R::-
     * O::Obtener el valor del nodo
     */
    public Object getValue() {
        return value;
    }

    /*
     * getChildren
     * E::-
     * S::lista de nodos hijos
     * R::-
     * O::Obtener la lista de nodos hijos
     */
    public ArrayList<ASTNode> getChildren() {
        return children;
    }

    /*
     * navigateAST
     * E::-
     * S::true si el nodo es un returnStm, false en otro caso
     * R::-
     * O::Recorrer el arbol sintactico abstracto
     */
    public boolean navigateAST() {
        return navigateASTNode(this);
    }

    /*
     * navigateASTNode
     * E::node: nodo a recorrer
     * S::true si el nodo es un returnStm, false en otro caso
     * R::-
     * O::Recorrer el arbol sintactico abstracto
     */
    private boolean navigateASTNode(ASTNode node) {
        if(node.type.equals("returnStm")){
            return true;
        }
        for (ASTNode child : node.children) {
            if(navigateASTNode(child)){
                return true;
            }
        }
        return false;
    }


    /*
     * toString
     * E::-
     * S::representacion en string del nodo
     * R::-
     * O::Obtener la representacion en string del nodo
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        if (value != null) {
            sb.append(": ");
            sb.append(value);
        }
        if (children != null && children.size() > 0) {
            sb.append(" {\n");
            for (ASTNode child : children) {
                sb.append(child.toString().replaceAll("(?m)^", "  "));
                sb.append("\n");
            }
            sb.append("}");
        }
        return sb.toString();
    }
}
