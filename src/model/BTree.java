package model;

/**
 * Nodo de un árbol B.
 * 
 * Un árbol B de grado mínimo 'gradoMinimo' (t) cumple:
 * - Cada nodo (excepto raíz) tiene al menos t-1 claves.
 * - Cada nodo tiene como máximo 2t-1 claves.
 * - Los nodos internos con k claves tienen k+1 hijos.
 */
public class NodoB {
    // Arreglo de claves (números enteros). Tamaño máximo = 2*t -1
    private int[] claves;
    // Arreglo de hijos (referencias a otros nodos). Tamaño máximo = 2*t
    private NodoB[] hijos;
    // Cantidad actual de claves almacenadas en este nodo
    private int cantidadClaves;
    // Si es hoja (true) o nodo interno (false)
    private boolean esHoja;

    /**
     * Constructor.
     * @param gradoMinimo  t (grado mínimo del árbol B)
     * @param esHoja       true si el nodo será una hoja
     */
    public NodoB(int gradoMinimo, boolean esHoja) {
        // El máximo de claves es 2t-1
        this.claves = new int[2 * gradoMinimo - 1];
        // El máximo de hijos es 2t (porque puede haber 2t hijos cuando está lleno)
        this.hijos = new NodoB[2 * gradoMinimo];
        this.cantidadClaves = 0;
        this.esHoja = esHoja;
    }

    // ---------- Métodos getter y setter ----------
    public int[] getClaves() { return claves; }
    public NodoB[] getHijos() { return hijos; }
    public int getCantidadClaves() { return cantidadClaves; }
    public void setCantidadClaves(int cantidad) { this.cantidadClaves = cantidad; }
    public boolean esHoja() { return esHoja; }
    public void setEsHoja(boolean esHoja) { this.esHoja = esHoja; }

    /**
     * Devuelve la clave en una posición dada (índice válido).
     */
    public int getClaveEn(int indice) {
        if (indice >= 0 && indice < cantidadClaves)
            return claves[indice];
        return -1; // valor centinela
    }

    /**
     * Inserta una clave en orden ascendente dentro del arreglo.
     * Asume que hay espacio disponible (cantidadClaves < máximo).
     */
    public void insertarClave(int clave) {
        int i = cantidadClaves - 1;
        // Desplazar claves mayores hacia la derecha
        while (i >= 0 && claves[i] > clave) {
            claves[i + 1] = claves[i];
            i--;
        }
        claves[i + 1] = clave;
        cantidadClaves++;
    }

    /**
     * Elimina y devuelve la última clave del nodo (útil para dividir).
     */
    public int eliminarUltimaClave() {
        int ultima = claves[cantidadClaves - 1];
        cantidadClaves--;
        return ultima;
    }

    /**
     * Agrega un hijo en una posición específica, desplazando los demás.
     */
    public void agregarHijo(int indice, NodoB hijo) {
        // Desplazar hijos a la derecha para hacer espacio
        for (int i = cantidadClaves + 1; i > indice; i--) {
            hijos[i] = hijos[i - 1];
        }
        hijos[indice] = hijo;
    }

    /**
     * Elimina y devuelve el hijo en la posición dada.
     */
    public NodoB eliminarHijoEn(int indice) {
        NodoB eliminado = hijos[indice];
        // Desplazar hijos restantes a la izquierda
        for (int i = indice; i < cantidadClaves + 1; i++) {
            hijos[i] = hijos[i + 1];
        }
        return eliminado;
    }
}
