package controller;

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