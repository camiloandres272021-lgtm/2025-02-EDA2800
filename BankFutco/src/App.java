import java.util.Scanner;
import java.util.Optional;
import model.Account;
import services.AccountService;

public class App {
    private static AccountService accountService = new AccountService();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMainMenu();
                String option = sc.nextLine().trim();
                switch (option) {
                    case "1":
                        runCrudMenu(sc, "Account");
                        break;
                    case "2":
                        runCrudMenu(sc, "Balance");
                        break;
                    case "3":
                        runCrudMenu(sc, "Loans");
                        break;
                    case "4":
                        runCrudMenu(sc, "Cards");
                        break;
                    case "0":
                        running = false;
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=== Menú Principal ===");
        System.out.println("1. Account");
        System.out.println("2. Balance");
        System.out.println("3. Loans");
        System.out.println("4. Cards");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void runCrudMenu(Scanner sc, String entityName) {
        boolean back = false;
        while (!back) {
            printCrudMenu(entityName);
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1":
                    // CREATE
                    System.out.println("\n[" + entityName + "] Crear nuevo registro");
                    System.out.print("Ingrese ID: ");
                    String id = sc.nextLine().trim();
                    System.out.print("Ingrese nombre del titular: ");
                    String owner = sc.nextLine().trim();
                    System.out.print("Ingrese correo: ");
                    String email = sc.nextLine().trim();
                    System.out.print("Ingrese teléfono: ");
                    String phone = sc.nextLine().trim();
                    System.out.print("Ingrese tipo de cuenta: ");
                    String type = sc.nextLine().trim();
                    System.out.print("Ingrese dirección: ");
                    String address = sc.nextLine().trim();

                    Account newAccount = new Account(id, owner, email, phone, type, address);
                    accountService.save(newAccount);
                    System.out.println("Cuenta creada exitosamente.");
                    break;

                case "2":
                    // READ BY ID
                    System.out.print("\n[" + entityName + "] Leer por ID - ingrese id: ");
                    String idSearch = sc.nextLine().trim();
                    Optional<Account> found = accountService.findById(idSearch);
                    if (found.isPresent()) {
                        System.out.println("Encontrado: " + found.get());
                    } else {
                        System.out.println(entityName + " con id=" + idSearch + " no encontrado.");
                    }
                    break;

                case "3":
                    // LIST ALL
                    System.out.println("\n[" + entityName + "] Listar todos:");
                    accountService.findAll().forEach(System.out::println);
                    break;

                case "4":
                    // UPDATE
                    System.out.print("\n[" + entityName + "] Actualizar - ingrese id existente: ");
                    String idUp = sc.nextLine().trim();

                    Optional<Account> existing = accountService.findById(idUp);
                    if (existing.isEmpty()) {
                        System.out.println("No existe una cuenta con ese ID.");
                        break;
                    }

                    System.out.print("Ingrese nuevo nombre (deje vacío para mantener): ");
                    String newOwner = sc.nextLine().trim();
                    System.out.print("Ingrese nuevo correo (deje vacío para mantener): ");
                    String newEmail = sc.nextLine().trim();
                    System.out.print("Ingrese nuevo teléfono (deje vacío para mantener): ");
                    String newPhone = sc.nextLine().trim();
                    System.out.print("Ingrese nuevo tipo (deje vacío para mantener): ");
                    String newType = sc.nextLine().trim();
                    System.out.print("Ingrese nueva dirección (deje vacío para mantener): ");
                    String newAddress = sc.nextLine().trim();

                    Account accToUpdate = existing.get();
                    if (!newOwner.isEmpty()) accToUpdate.setName(newOwner);
                    if (!newEmail.isEmpty()) accToUpdate.setEmail(newEmail);
                    if (!newPhone.isEmpty()) accToUpdate.setPhone(newPhone);
                    if (!newType.isEmpty()) accToUpdate.setAccountType(newType);
                    if (!newAddress.isEmpty()) accToUpdate.setAddress(newAddress);

                    accountService.save(accToUpdate);
                    System.out.println("Cuenta actualizada exitosamente.");
                    break;

                case "5":
                    // DELETE
                    System.out.print("\n[" + entityName + "] Eliminar - ingrese id: ");
                    String idDel = sc.nextLine().trim();
                    boolean deleted = accountService.deleteById(idDel);
                    if (deleted) {
                        System.out.println("Cuenta eliminada exitosamente.");
                    } else {
                        System.out.println("No se encontró cuenta con ese ID.");
                    }
                    break;

                case "0":
                    back = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void printCrudMenu(String entityName) {
        System.out.println("\n--- " + entityName + " CRUD ---");
        System.out.println("1. Create");
        System.out.println("2. Read by id");
        System.out.println("3. List all");
        System.out.println("4. Update");
        System.out.println("5. Delete");
        System.out.println("0. Back");
        System.out.print("Seleccione una opción: ");
    }
}
