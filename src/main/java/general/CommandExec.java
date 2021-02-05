package general;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PostgreSql Database Backup Operation Class
 *
 * @author Zekeriya Furkan İNCE
 * @date 05.02.2021 09:03:22
 */
public class CommandExec {

    static final String SCRIPT_PATH_IN_UNIX = "/home/furkanince/backup_postgres_unix.sh";
    static final String BACKUP_OUTPUT_IN_UNIX = "/home/furkanince/backup_postgres_unix.bak";

    static final String DATABASE_HOST = "127.0.0.1";
    static final String DATABASE_DBNAME = "postgres";
    static final String DATABASE_USERNAME = "zekeriyafince";
    static final String DATABASE_PASSWORD = "12***21";
    static final String DATABASE_PORT = "5432";

    public static boolean isLinux() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().indexOf("linux") >= 0;
    }

    public static String backupPostgresDatabase() {
        String exitRunResponse = "";
        if (isLinux()) {
            exitRunResponse = backupDatabaseLinux();
        }
        return exitRunResponse;
    }

    public static String backupDatabaseLinux() {
        String response = "";
        try {
            Process p = null;
            String[] cmd = {"/bin/sh",
                "-c",
                SCRIPT_PATH_IN_UNIX + " " + DATABASE_USERNAME + " " + DATABASE_PASSWORD + " " + DATABASE_HOST + " " + DATABASE_DBNAME + BACKUP_OUTPUT_IN_UNIX};
            Logger.getLogger(CommandExec.class.getName()).log(Level.INFO, SCRIPT_PATH_IN_UNIX + " " + DATABASE_USERNAME + " " + DATABASE_PASSWORD + " " + DATABASE_HOST + " " + DATABASE_DBNAME + BACKUP_OUTPUT_IN_UNIX);
            p = Runtime.getRuntime().exec(cmd);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                response = response + line.trim();
            }
            try {
                p.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(CommandExec.class.getName()).log(Level.SEVERE, "backupDatabaseLinux 1", ex);
            }
            p.destroy();
        } catch (Exception ex) {
            Logger.getLogger(CommandExec.class.getName()).log(Level.SEVERE, "backupDatabaseLinux 2", ex);
        }
        return response;
    }

}
