package btreeapp.controlador;

import btreeapp.modelo.ArbolB;
import btreeapp.vista.VistaArbolB;
import java.util.Scanner;

/**
 * Controlador: maneja el flujo de la aplicación,
 * recibe entradas del usuario y actualiza el modelo/vista.
 */
public class ControladorArbolB {
    private ArbolB modelo;
    private VistaArbolB vista;
    private Scanner scanner;

    public ControladorArbolB() {
        // Grado mínimo = 2 → árbol B de orden 4 (máx 3 claves por nodo)
        modelo = new ArbolB(2);
        vista = new VistaArbolB();
        scanner = new Scanner(System.in);
    }

    public void ejecutar() {
        int opcion;
        do {
            vista.mostrarMenu();
            opcion = leerEntero();
            switch (opcion) {
                case 1:
                    insertarClave();
                    break;
                case 2:
                    buscarClave();
                    break;
                case 3:
                    vista.mostrarArbol(modelo);
                    break;
                case 4:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    vista.mostrarError("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 4);
        scanner.close();
    }

    private void insertarClave() {
        System.out.print("Ingrese el número a insertar: ");
        int clave = leerEntero();
        if (modelo.buscar(clave)) {
            vista.mostrarMensajeInsercion(clave, false);
        } else {
            modelo.insertar(clave);
            vista.mostrarMensajeInsercion(clave, true);
            vista.mostrarArbol(modelo);  // muestra el árbol actualizado
        }
    }

    private void buscarClave() {
        System.out.print("Ingrese el número a buscar: ");
        int clave = leerEntero();
        boolean encontrado = modelo.buscar(clave);
        vista.mostrarMensajeBusqueda(clave, encontrado);
    }

    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                vista.mostrarError("Debe ingresar un número entero.");
                System.out.print("Intente de nuevo: ");
            }
        }
    }

    public static void main(String[] args) {
        ControladorArbolB controlador = new ControladorArbolB();
        controlador.ejecutar();
    }
}package controller;

import model.BTree;
import view.BTreeView;
import java.util.Scanner;

public class BTreeController {
    private BTree model;
    private BTreeView view;
    private Scanner scanner;

    public BTreeController() {
        model = new BTree(2); // grado 2 -> orden 4
        view = new BTreeView();
        scanner = new Scanner(System.in);
    }

    public void run() {
        int option;
        do {
            view.showMenu();
            option = readInt();
            switch (option) {
                case 1:
                    insertKey();
                    break;
                case 2:
                    searchKey();
                    break;
                case 3:
                    view.displayTree(model);
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    view.showError("Opción inválida.");
            }
        } while (option != 4);
        scanner.close();
    }

    private void insertKey() {
        System.out.print("Ingrese el número a insertar: ");
        int key = readInt();
        if (model.search(key)) {
            view.showInsertMessage(key, false);
        } else {
            model.insert(key);
            view.showInsertMessage(key, true);
            view.displayTree(model);
        }
    }

    private void searchKey() {
        System.out.print("Ingrese el número a buscar: ");
        int key = readInt();
        boolean found = model.search(key);
        view.showSearchMessage(key, found);
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                view.showError("Debe ingresar un número entero.");
                System.out.print("Intente de nuevo: ");
            }
        }
    }

    public static void main(String[] args) {
        BTreeController controller = new BTreeController();
        controller.run();
    }
}
