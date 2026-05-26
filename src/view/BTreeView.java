package btreeapp.vista;

import btreeapp.modelo.ArbolB;
import btreeapp.modelo.NodoB;

/**
 * Vista encargada de mostrar el árbol B y los mensajes al usuario.
 * No contiene lógica de negocio.
 */
public class VistaArbolB {

    /**
     * Muestra el árbol completo con formato jerárquico.
     */
    public void mostrarArbol(ArbolB arbol) {
        if (arbol.getRaiz() == null || arbol.getRaiz().getCantidadClaves() == 0) {
            System.out.println("El árbol está vacío.");
            return;
        }
        System.out.println("\n--- ÁRBOL B (orden " + (2 * arbol.getGradoMinimo()) + ") ---");
        mostrarNodo(arbol.getRaiz(), "", true);
        System.out.println("-------------------------------------------\n");
    }

    /**
     * Muestra recursivamente un nodo y sus hijos.
     * @param nodo      Nodo actual
     * @param prefijo   Espaciado para la indentación
     * @param esUltimo  Indica si es el último hijo de su padre (para la simbología)
     */
    private void mostrarNodo(NodoB nodo, String prefijo, boolean esUltimo) {
        // Mostrar las claves del nodo entre corchetes
        System.out.print(prefijo + (esUltimo ? "└── " : "├── ") + "[");
        for (int i = 0; i < nodo.getCantidadClaves(); i++) {
            System.out.print(nodo.getClaveEn(i));
            if (i < nodo.getCantidadClaves() - 1) System.out.print(", ");
        }
        System.out.print("]");
        if (nodo.esHoja()) {
            System.out.println(" (hoja)");
        } else {
            System.out.println();
        }

        // Mostrar los hijos si existen
        if (!nodo.esHoja()) {
            for (int i = 0; i <= nodo.getCantidadClaves(); i++) {
                NodoB hijo = nodo.getHijos()[i];
                if (hijo != null) {
                    boolean ultimoHijo = (i == nodo.getCantidadClaves());
                    mostrarNodo(hijo, prefijo + (esUltimo ? "    " : "│   "), ultimoHijo);
                }
            }
        }
    }

    // ----- Mensajes simples -----
    public void mostrarMensajeInsercion(int clave, boolean exito) {
        if (exito) {
            System.out.println("✓ Clave " + clave + " insertada correctamente.");
        } else {
            System.out.println("✗ La clave " + clave + " ya existe. No se insertó.");
        }
    }

    public void mostrarMensajeBusqueda(int clave, boolean encontrado) {
        if (encontrado) {
            System.out.println("✓ Clave " + clave + " ENCONTRADA en el árbol.");
        } else {
            System.out.println("✗ Clave " + clave + " NO encontrada.");
        }
    }

    public void mostrarError(String mensaje) {
        System.out.println("ERROR: " + mensaje);
    }

    public void mostrarMenu() {
        System.out.println("\n===== MENÚ ÁRBOL B =====");
        System.out.println("1. Insertar número");
        System.out.println("2. Buscar número");
        System.out.println("3. Mostrar árbol");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }
}package view;

import model.BTree;
import model.Node;

public class BTreeView {

    public void displayTree(BTree tree) {
        if (tree.getRoot() == null || tree.getRoot().getKeyCount() == 0) {
            System.out.println("Árbol vacío.");
            return;
        }
        System.out.println("\n--- Árbol B (orden " + (2 * tree.getT()) + ") ---");
        printNode(tree.getRoot(), "", true);
        System.out.println("-----------------------------------\n");
    }

    private void printNode(Node node, String prefix, boolean isTail) {
        // Mostrar claves del nodo
        System.out.print(prefix + (isTail ? "└── " : "├── ") + "[");
        for (int i = 0; i < node.getKeyCount(); i++) {
            System.out.print(node.getKeyAt(i));
            if (i < node.getKeyCount() - 1) System.out.print(", ");
        }
        System.out.print("]");
        if (node.isLeaf()) {
            System.out.println(" (hoja)");
        } else {
            System.out.println();
        }

        // Mostrar hijos
        if (!node.isLeaf()) {
            for (int i = 0; i <= node.getKeyCount(); i++) {
                Node child = node.getChildren()[i];
                if (child != null) {
                    boolean lastChild = (i == node.getKeyCount());
                    printNode(child, prefix + (isTail ? "    " : "│   "), lastChild);
                }
            }
        }
    }

    public void showInsertMessage(int key, boolean success) {
        if (success) {
            System.out.println("✓ Clave " + key + " insertada correctamente.");
        } else {
            System.out.println("✗ La clave " + key + " ya existe. No se insertó.");
        }
    }

    public void showSearchMessage(int key, boolean found) {
        if (found) {
            System.out.println("✓ Clave " + key + " ENCONTRADA en el árbol.");
        } else {
            System.out.println("✗ Clave " + key + " NO encontrada.");
        }
    }

    public void showError(String message) {
        System.out.println("ERROR: " + message);
    }

    public void showMenu() {
        System.out.println("\n===== MENÚ ÁRBOL B =====");
        System.out.println("1. Insertar número");
        System.out.println("2. Buscar número");
        System.out.println("3. Mostrar árbol");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }
}
