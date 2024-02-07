import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private Map<String, String> contacts;
    private static final String FILENAME = "contacts.txt";

    public AddressBook() {
        this.contacts = new HashMap<>();
    }

    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) { // Verificar si hay dos partes después de dividir por la coma
                    contacts.put(parts[0], parts[1]);
                } else {
                    System.out.println("Error de formato en la linea: " + line);
                }
            }
            System.out.println("Contactos cargados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar los contactos.");
        }
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
            System.out.println("Cambios guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los cambios.");
        }
    }

    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void create(String number, String name) {
        contacts.put(number, name);
        System.out.println("Contacto creado correctamente.");
    }

    public void delete(String number) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Estas seguro de que quieres borrar el contacto con numero " + number + "? (s/n)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("s")) {
            contacts.remove(number);
            System.out.println("Contacto eliminado correctamente.");
        } else {
            System.out.println("No se ha eliminado ningun contacto.");
        }
        scanner.close();
    }

    public void deleteAll() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el numero de telefono del contacto a borrar: ");
        String number = scanner.nextLine();
        
        System.out.println("¿Estas seguro de que quieres borrar el contacto con numero " + number + "? (s/n)");
        String answer = scanner.nextLine().toLowerCase();
        if (answer.equals("s")) {
            contacts.remove(number);
            System.out.println("Contacto eliminado correctamente.");
        } else if (!answer.equals("n")) {
            System.out.println("Respuesta no valida. Por favor, responde con 's' o 'n'.");
        }
    
        scanner.close();
    }
    
    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        addressBook.load();

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\nMenu de Agenda Telefonica:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Borrar contacto");
            System.out.println("4. Borrar todos los contactos");
            System.out.println("5. Guardar cambios");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un numero valido.");
                scanner.next(); // Consumir la entrada inválida
            }
            option = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (option) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    System.out.print("Introduce el numero de telefono del nuevo contacto: ");
                    String newNumber = scanner.nextLine();
                    System.out.print("Introduce el nombre del nuevo contacto: ");
                    String newName = scanner.nextLine();
                    addressBook.create(newNumber, newName);
                    break;
                case 3:
                    System.out.print("Introduce el numero de telefono del contacto a borrar: ");
                    String deleteNumber = scanner.nextLine();
                    addressBook.delete(deleteNumber);
                    break;
                case 4:
                    addressBook.deleteAll();
                    break;
                case 5:
                    addressBook.save();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opcion no valida. Por favor, elige una opcion del menu.");
            }
        } while (option != 0);
        
        scanner.close(); // Cerrar el scanner al final del método main
    }
}
