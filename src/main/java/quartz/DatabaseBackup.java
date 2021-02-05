package quartz;

import general.CommandExec;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Database Backup Class
 *
 * @author Zekeriya Furkan İNCE
 * @date 31.01.2021 14:30
 */
public class DatabaseBackup implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        databaseBackup();
    }

    public static void databaseBackup() {
        System.out.println("database backup start");
        CommandExec.backupPostgresDatabase();
    }
}
