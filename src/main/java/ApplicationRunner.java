import gui.Main;
import service.MainService;

import java.io.IOException;

public class ApplicationRunner {
    public void run() throws IOException {
        Main main = new Main();
        MainService mainService = new MainService(main);

        mainService.createWindow();
    }
}
