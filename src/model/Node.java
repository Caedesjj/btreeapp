package model;

public class Node {
    private int[] keys;        // arreglo fijo de claves
    private Node[] children;   // arreglo fijo de hijos
    private int keyCount;      // número actual de claves
    private boolean leaf;

    public Node(int t, boolean leaf) {
        this.keys = new int[2 * t - 1];
        this.children = new Node[2 * t];
        this.keyCount = 0;
        this.leaf = leaf;
    }

    // Getters y setters básicos
    public int[] getKeys() { return keys; }
    public Node[] getChildren() { return children; }
    public int getKeyCount() { return keyCount; }
    public void setKeyCount(int count) { this.keyCount = count; }
    public boolean isLeaf() { return leaf; }
    public void setLeaf(boolean leaf) { this.leaf = leaf; }

    // Método para insertar una clave en orden (asumiendo que hay espacio)
    public void insertKey(int key) {
        int i = keyCount - 1;
        while (i >= 0 && keys[i] > key) {
            keys[i + 1] = keys[i];
            i--;
        }
        keys[i + 1] = key;
        keyCount++;
    }

    // Eliminar la última clave (útil para split)
    public int removeLastKey() {
        int last = keys[keyCount - 1];
        keyCount--;
        return last;
    }

    // Obtener clave en índice
    public int getKeyAt(int index) {
        if (index >= 0 && index < keyCount) return keys[index];
        return -1;
    }

    // Agregar hijo en posición específica
    public void addChild(int index, Node child) {
        // Desplazar hijos a la derecha si es necesario
        for (int i = keyCount + 1; i > index; i--) {
            children[i] = children[i - 1];
        }
        children[index] = child;
    }

    // Eliminar hijo en índice
    public Node removeChildAt(int index) {
        Node removed = children[index];
        for (int i = index; i < keyCount + 1; i++) {
            children[i] = children[i + 1];
        }
        return removed;
    }
}