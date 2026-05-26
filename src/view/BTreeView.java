package view;

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