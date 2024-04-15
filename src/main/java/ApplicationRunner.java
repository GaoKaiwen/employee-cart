import exception.CsvRepositoryException;
import gui.Main;
import service.MainService;

public class ApplicationRunner {
    public void run() throws CsvRepositoryException {
        Main main = new Main();
        MainService mainService = new MainService(main);

        mainService.createWindow();
    }
}
