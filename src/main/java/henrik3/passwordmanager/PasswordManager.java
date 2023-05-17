package henrik3.passwordmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;

import henrik3.passwordgenerator.PasswordGenerator;
import henrik3.passwordmanager.Model.Password;


public class PasswordManager {

    private static final Integer PASSWORD_MIN_LENGTH = 8;
    private static final String BACKUP_FILE = "data.bin";
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static ArrayList<Password> passwords = new ArrayList<>();

    private static boolean running = true;

    public static void main(String[] args) throws IOException {

        readStorage();

        while (running) {
            switch (menu()) {
                case 1 ->
                    createPassword();
                case 2 ->
                    seeAllPasswords();
                case 3 ->
                    deletePasswords();
                case 4 ->
                    running = false;
                default -> {
                    print("Tente novamente.");
                }
            }
        }

    }

    private static int menu() {

        return intInput("\nGerenciador de Senhas\n[1]Criar senha\n[2]Ver senhas\n[3]Excluir senhas\n[4]Sair\n");

    }

    private static String input(String msg) {
        try {
            print(msg);

            return reader.readLine();
        } catch (IOException e) {
            return "";
        }
    }

    private static int intInput(String msg) {
        try {
            return Integer.parseInt(input(msg));
        } catch (InputMismatchException | NumberFormatException e) {
            return 0;
        }
    }

    private static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException e) {
            // Handle any exceptions
        }
    }

    private static void print(String msg) {
        System.out.print(msg);
    }

    private static void printPasswords() {
        if (passwords.size() < 1) {
            print("\nNenhuma senha salva.");
            return;
        }

        print("\nUsuário - Senha\n\n");

        int c = 0;
        for (Password item : passwords) {
            String msg = String.format("[%d] %s %s\n", c++, item.getUser(), item.getPassword());
            print(msg);
        }
    }

    private static void createPassword() {
        clear();
        
        String user;
        int length = PASSWORD_MIN_LENGTH;

        String msg = String.format("\nCriar senha\nInforme o tamanho de senha desejado [DEFAULT %d]: ", PASSWORD_MIN_LENGTH);
        int tmp = intInput(msg);
        if (tmp > length) length = tmp;

        user = input("\nInforme o usuario da senha: ").toUpperCase();

        createOne(user, length);

        print("\nSenha salva.");

    }

    private static void seeAllPasswords() {
        clear();

        printPasswords();
    }

    private static void deletePasswords() {
        clear();

        if (passwords.size() < 1) {

            print("\nNenhuma senha salva.");

            return;
        }

        printPasswords();

        int pos = intInput("\nInforme o item a ser removido: ");

        if (deleteOne(pos)) {
            print("\nItem removido.");
        } else {
            print("\nErro ao remover.");
        }

    }

    // -------------------------------------
    private static void readStorage() {
        try (FileInputStream fileIn = new FileInputStream(BACKUP_FILE); ObjectInputStream objectIn = new ObjectInputStream(fileIn);) {
            passwords = (ArrayList<Password>) objectIn.readObject();
        } catch (Exception e) {
            print("No backup file.");
        }
    }

    private static void setToStorage() {
        try (FileOutputStream fileOut = new FileOutputStream(BACKUP_FILE); ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);) {
            objectOut.writeObject(passwords);
        } catch (Exception e) {
            print("Erro ao salvar dados." + e.getMessage());
        }
    }

    private static Password createOne(String user, int length) {
        Password pass = new Password(user, PasswordGenerator.create(length));

        passwords.add(pass);

        setToStorage();

        return pass;
    }

    private static boolean deleteOne(int pos) {

        try {
            passwords.remove(pos);
            setToStorage();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

}
