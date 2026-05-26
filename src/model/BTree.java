package model;

public class BTree {
    private Node root;
    private final int t; // grado mínimo

    public BTree(int t) {
        this.t = t;
        root = new Node(t, true);
    }

    public int getT() { return t; }
    public Node getRoot() { return root; }

    // Insertar clave pública
    public void insert(int key) {
        Node r = root;
        if (r.getKeyCount() == 2 * t - 1) {
            Node s = new Node(t, false);
            s.getChildren()[0] = r;
            splitChild(s, 0, r);
            root = s;
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }

    // Insertar en nodo no lleno
    private void insertNonFull(Node node, int key) {
        int i = node.getKeyCount() - 1;

        if (node.isLeaf()) {
            node.insertKey(key);
        } else {
            // Encontrar hijo adecuado
            while (i >= 0 && key < node.getKeyAt(i)) {
                i--;
            }
            i++;
            Node child = node.getChildren()[i];
            if (child.getKeyCount() == 2 * t - 1) {
                splitChild(node, i, child);
                if (key > node.getKeyAt(i)) {
                    i++;
                }
            }
            insertNonFull(node.getChildren()[i], key);
        }
    }

    // Dividir un hijo lleno
    private void splitChild(Node parent, int index, Node fullChild) {
        Node newChild = new Node(t, fullChild.isLeaf());
        int midIndex = t - 1;
        int median = fullChild.getKeyAt(midIndex);

        // Copiar las claves mayores que la mediana al nuevo hijo
        int newKeyCount = 0;
        for (int j = midIndex + 1; j < fullChild.getKeyCount(); j++) {
            newChild.getKeys()[newKeyCount++] = fullChild.getKeyAt(j);
        }
        newChild.setKeyCount(newKeyCount);

        // Copiar los hijos si no es hoja
        if (!fullChild.isLeaf()) {
            for (int j = midIndex + 1; j <= fullChild.getKeyCount(); j++) {
                newChild.getChildren()[j - (midIndex + 1)] = fullChild.getChildren()[j];
            }
        }

        // Reducir el hijo original
        fullChild.setKeyCount(midIndex); // ahora tiene claves 0..midIndex-1

        // Desplazar hijos del padre para hacer espacio
        for (int j = parent.getKeyCount(); j > index; j--) {
            parent.getChildren()[j + 1] = parent.getChildren()[j];
        }
        parent.getChildren()[index + 1] = newChild;

        // Insertar mediana en el padre
        parent.insertKey(median);
    }

    // Búsqueda
    public boolean search(int key) {
        return searchRec(root, key);
    }

    private boolean searchRec(Node node, int key) {
        int i = 0;
        while (i < node.getKeyCount() && key > node.getKeyAt(i)) {
            i++;
        }
        if (i < node.getKeyCount() && key == node.getKeyAt(i)) {
            return true;
        }
        if (node.isLeaf()) {
            return false;
        }
        return searchRec(node.getChildren()[i], key);
    }
}