package examples.test;

import edu.umd.cs.guitar.event.JFCSelectionHandler;
import edu.umd.cs.guitar.replayer.JFCReplayerMain;

/**
 * Created by oliver on 31/03/16.
 */
public class Replay {
    public static void main(String[] args) {
        String[] ss = {"-c", "examples.ticket.BaradTicket", "-g", "log/barad-ticket/barad-ticket.GUI", "-e", "log/barad-ticket/barad-ticket.EFG", "-t", "log/barad-ticket/testcases/t_e1471183336.tst", "-i", "2000", "-d", "200", "-l", "log/barad-ticket/logs/t_e1471183336.log", "-gs", "log/barad-ticket/states/t_e1471183336.sta", "-cf", "./conf/barad-ticket/configuration.xml"};
        JFCReplayerMain.main(ss);
    }
}
