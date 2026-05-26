package btreeapp.modelo;

/**
 * Implementación de un Árbol B simple (solo inserción y búsqueda).
 * Propiedades:
 * - Grado mínimo t (t >= 2). Cada nodo puede tener entre t-1 y 2t-1 claves.
 * - La raíz puede tener menos de t-1 claves.
 * - Todas las hojas están al mismo nivel.
 * 
 * En este ejemplo, t = 2, por lo que el orden (máximo de claves) es 3.
 */
public class ArbolB {
    private NodoB raiz;
    private final int gradoMinimo;  // t

    public ArbolB(int gradoMinimo) {
        this.gradoMinimo = gradoMinimo;
        raiz = new NodoB(gradoMinimo, true); // raíz vacía, hoja
    }

    public int getGradoMinimo() { return gradoMinimo; }
    public NodoB getRaiz() { return raiz; }

    // ---------- Método público de inserción ----------
    public void insertar(int clave) {
        NodoB nodoActual = raiz;
        // Si la raíz está llena, hay que dividirla y crear una nueva raíz
        if (nodoActual.getCantidadClaves() == 2 * gradoMinimo - 1) {
            NodoB nuevaRaiz = new NodoB(gradoMinimo, false);
            nuevaRaiz.getHijos()[0] = nodoActual;
            dividirHijo(nuevaRaiz, 0, nodoActual);
            raiz = nuevaRaiz;
            insertarNoLleno(nuevaRaiz, clave);
        } else {
            insertarNoLleno(nodoActual, clave);
        }
    }

    /**
     * Inserta una clave en un nodo que no está lleno (tiene espacio).
     */
    private void insertarNoLleno(NodoB nodo, int clave) {
        int i = nodo.getCantidadClaves() - 1;

        if (nodo.esHoja()) {
            // Nodo hoja: insertar directamente en orden
            nodo.insertarClave(clave);
        } else {
            // Nodo interno: buscar el hijo adecuado
            while (i >= 0 && clave < nodo.getClaveEn(i)) {
                i--;
            }
            i++;
            NodoB hijo = nodo.getHijos()[i];
            // Si el hijo está lleno, dividirlo antes de bajar
            if (hijo.getCantidadClaves() == 2 * gradoMinimo - 1) {
                dividirHijo(nodo, i, hijo);
                // Después de dividir, decidir cuál de los dos hijos usar
                if (clave > nodo.getClaveEn(i)) {
                    i++;
                }
            }
            insertarNoLleno(nodo.getHijos()[i], clave);
        }
    }

    /**
     * Divide un hijo lleno (con 2t-1 claves) en dos nodos de t-1 claves cada uno.
     * La mediana sube al padre.
     * 
     * @param padre   Nodo padre que contiene al hijo
     * @param indice  Índice del hijo en el arreglo de hijos del padre
     * @param hijo    El hijo que está lleno
     */
    private void dividirHijo(NodoB padre, int indice, NodoB hijo) {
        int indiceMediana = gradoMinimo - 1;  // posición de la mediana (0-based)
        int mediana = hijo.getClaveEn(indiceMediana);
        
        // Crear un nuevo nodo que tomará las claves mayores a la mediana
        NodoB nuevoHijo = new NodoB(gradoMinimo, hijo.esHoja());
        int contadorNuevo = 0;
        
        // Copiar claves mayores (desde indiceMediana+1 hasta el final)
        for (int j = indiceMediana + 1; j < hijo.getCantidadClaves(); j++) {
            nuevoHijo.getClaves()[contadorNuevo++] = hijo.getClaveEn(j);
        }
        nuevoHijo.setCantidadClaves(contadorNuevo);
        
        // Si no es hoja, también copiar los hijos correspondientes
        if (!hijo.esHoja()) {
            for (int j = indiceMediana + 1; j <= hijo.getCantidadClaves(); j++) {
                nuevoHijo.getHijos()[j - (indiceMediana + 1)] = hijo.getHijos()[j];
            }
        }
        
        // Reducir el hijo original: solo conserva las claves menores a la mediana
        hijo.setCantidadClaves(indiceMediana); // claves en índices 0..indiceMediana-1
        
        // En el padre, desplazar hijos para hacer espacio para el nuevoHijo
        for (int j = padre.getCantidadClaves(); j > indice; j--) {
            padre.getHijos()[j + 1] = padre.getHijos()[j];
        }
        padre.getHijos()[indice + 1] = nuevoHijo;
        
        // Insertar la mediana en el padre
        padre.insertarClave(mediana);
    }

    // ---------- Método de búsqueda ----------
    public boolean buscar(int clave) {
        return buscarRecursivo(raiz, clave);
    }

    private boolean buscarRecursivo(NodoB nodo, int clave) {
        int i = 0;
        // Avanzar mientras la clave actual sea menor que la buscada
        while (i < nodo.getCantidadClaves() && clave > nodo.getClaveEn(i)) {
            i++;
        }
        // Si encontramos la clave en este nodo
        if (i < nodo.getCantidadClaves() && clave == nodo.getClaveEn(i)) {
            return true;
        }
        // Si es hoja, no está
        if (nodo.esHoja()) {
            return false;
        }
        // Sino, seguir buscando en el hijo correspondiente
        return buscarRecursivo(nodo.getHijos()[i], clave);
    }
}package model;

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
