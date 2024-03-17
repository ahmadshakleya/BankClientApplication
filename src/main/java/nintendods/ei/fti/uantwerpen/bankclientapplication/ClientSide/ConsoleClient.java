package nintendods.ei.fti.uantwerpen.bankclientapplication.ClientSide;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleClient implements CommandLineRunner {

    private final ClientService clientService;

    @Autowired
    public ConsoleClient(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void run(String... args) {
        clientService.interactWithServer();
    }
}
