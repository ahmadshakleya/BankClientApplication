package nintendods.ei.fti.uantwerpen.bankclientapplication.ClientSide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@Service
public class ClientService {

    private final RestTemplate restTemplate;
    private final Scanner scanner;
    private String baseUrl = null;

    private boolean connected = false;

    @Autowired
    public ClientService(RestTemplate restTemplate, Scanner scanner) {
        this.restTemplate = restTemplate;
        this.scanner = scanner;
    }

    public void interactWithServer() {
        while (!connected) {
            System.out.println("Do you want to connect to a server? (yes/no)");
            String choice = scanner.nextLine();
            if ("yes".equalsIgnoreCase(choice)) {
                connect();
                connected = true;
            } else if ("no".equalsIgnoreCase(choice)) {
                System.out.println("Exiting the program.");
                return;
            } else {
                System.out.println("Invalid choice!");
            }
        }

        while (true) {
            System.out.println("Choose an action: ");
            System.out.println("1. Create Account");
            System.out.println("2. Get Balance");
            System.out.println("3. Add Money");
            System.out.println("4. Remove Money");
            System.out.println("5. Transfer Money");
            System.out.println("6. Disconnect");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    getBalance();
                    break;
                case 3:
                    addMoney();
                    break;
                case 4:
                    removeMoney();
                    break;
                case 5:
                    transferMoney();
                    break;
                case 6:
                    disconnect();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void connect() {
        System.out.println("Enter server IP:");
        String serverIp = scanner.nextLine();

        System.out.println("Enter server port:");
        int serverPort = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        baseUrl = "http://" + serverIp + ":" + serverPort;
        System.out.println("Connected to server: " + baseUrl);
        connected = true;
        interactWithServer();
    }

    private void disconnect() {
        baseUrl = null;
        connected = false;
        System.out.println("Do you want to connect to another server? (yes/no)");
        String choice = scanner.nextLine();
        if ("yes".equalsIgnoreCase(choice)) {
            connect();
        } else if ("no".equalsIgnoreCase(choice)) {
            System.out.println("Exiting the program.");
            System.exit(0);
        } else {
            System.out.println("Invalid choice!");
        }
    }

    private void createAccount() {
        if (baseUrl != null) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter name:");
            String name = scanner.nextLine();

            System.out.println("Enter bank number:");
            String bankNumber = scanner.nextLine();

            System.out.println("Enter initial balance:");
            int initialBalance = scanner.nextInt();


            // Construct the request URL
            String createAccountUrl = baseUrl + "/createAccount?name=" + name + "&bankNumber=" + bankNumber + "&initialBalance=" + initialBalance;

            try {
                // Make the HTTP request
                ResponseEntity<String> response = restTemplate.postForEntity(createAccountUrl, null, String.class);

                // Handle the response
                if (response.getStatusCode() == HttpStatus.OK) {
                    //System.out.println("Account created successfully.");
                    System.out.println("Response: \n" + response.getBody());
                } else {
                    //System.out.println("Error creating account. Status code: " + response.getStatusCodeValue());
                    System.out.println("Error message: \n" + response.getBody());
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            connect();
        }
    }

    private void getBalance() {
        if (baseUrl != null) {
            // Ask for input (bank number)
            System.out.println("Enter bank number:");
            String bankNumber = scanner.nextLine();

            // Construct the HTTP request
            String getBalanceUrl = baseUrl + "/balance?bankNumber=" + bankNumber;
            try {
                // Make the request using restTemplate.getForEntity(...)
                ResponseEntity<String> response = restTemplate.getForEntity(getBalanceUrl, String.class);

                // Handle the response
                if (response.getStatusCode() == HttpStatus.OK) {
                    //System.out.println("Balance for bank number " + bankNumber + ": " + response.getBody());
                    System.out.println("Response: \n" + response.getBody());
                } else {
                    //System.out.println("Error retrieving balance. Status code: " + response.getStatusCodeValue());
                    //System.out.println("Error message: " + response.getBody());
                    System.out.println("Error message: \n" + response.getBody());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            connect();
        }
    }


    private void addMoney() {
        if (baseUrl != null) {
            // Ask for input (bank number, amount)
            System.out.println("Enter bank number:");
            String bankNumber = scanner.nextLine();

            System.out.println("Enter amount to add:");
            int amount = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Construct the HTTP request
            String addMoneyUrl = baseUrl + "/add?bankNumber=" + bankNumber + "&amount=" + amount;
            try {
                // Make the request using restTemplate.put(...)
                ResponseEntity<String> response = restTemplate.exchange(addMoneyUrl, HttpMethod.PUT, null, String.class);

                // Handle the response
                if (response.getStatusCode() == HttpStatus.OK) {
                    //System.out.println("Money added successfully.");
                    //System.out.println("New balance: " + response.getBody());
                    System.out.println("Response: \n" + response.getBody());
                } else {
                    //System.out.println("Error adding money. Status code: " + response.getStatusCodeValue());
                    System.out.println("Error message: \n" + response.getBody());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            connect();
        }
    }


    private void removeMoney() {
        if (baseUrl != null) {
            // Ask for input (bank number, amount)
            System.out.println("Enter bank number:");
            String bankNumber = scanner.nextLine();

            System.out.println("Enter amount to remove:");
            int amount = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Construct the HTTP request
            String removeMoneyUrl = baseUrl + "/remove?bankNumber=" + bankNumber + "&amount=" + amount;

            try {
                // Make the request using restTemplate.exchange(...)
                ResponseEntity<String> response = restTemplate.exchange(removeMoneyUrl, HttpMethod.DELETE, null, String.class);

                // Handle the response
                if (response.getStatusCode() == HttpStatus.OK) {
                    //System.out.println("Money removed successfully.");
                    //System.out.println("New balance: " + response.getBody());
                    System.out.println("Response: \n" + response.getBody());
                } else {
                    //System.out.println("Error removing money. Status code: " + response.getStatusCodeValue());
                    //System.out.println("Error message: " + response.getBody());
                    System.out.println("Error message: \n" + response.getBody());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            connect();
        }
    }


    private void transferMoney() {
        if (baseUrl != null) {
            // Ask for input (source bank number, destination bank number, amount)
            System.out.println("Enter source bank number:");
            String sourceBankNumber = scanner.nextLine();

            System.out.println("Enter destination bank number:");
            String destinationBankNumber = scanner.nextLine();

            System.out.println("Enter amount to transfer:");
            int amount = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Construct the HTTP request
            String transferMoneyUrl = baseUrl + "/transfer?sourceBankNumber=" + sourceBankNumber +
                    "&destinationBankNumber=" + destinationBankNumber + "&amount=" + amount;
            try {
                // Make the request using restTemplate.postForEntity(...)
                ResponseEntity<String> response = restTemplate.postForEntity(transferMoneyUrl, null, String.class);

                // Handle the response
                if (response.getStatusCode() == HttpStatus.OK) {
                    //System.out.println("Transfer successful.");
                    System.out.println("Response: \n" + response.getBody());
                } else {
                    //System.out.println("Error transferring money. Status code: " + response.getStatusCodeValue());
                    System.out.println("Error message: \n" + response.getBody());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            connect();
        }
    }
}
