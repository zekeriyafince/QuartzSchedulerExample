
import org.junit.Test;
import quartz.ScheduleHelper;


/**
 * Database Backup Scheduler Test
 *
 * @author Zekeriya Furkan İNCE
 * @date 31.01.2021 14:30
 */
public class DatabaseBackupSchedulerTest {
    /**
     * At every minute past hour 10.
     */
    @Test
    public void everydayAt10HourCronjobTest() {
        System.out.println("everydayAt10HourCronjobTest start");
        ScheduleHelper scheduleHelper = ScheduleHelper.getInstance();
        String cronExpression = scheduleHelper.getCronExpression(10, 0, 0);
        scheduleHelper.scheduleDatabaseBackup(cronExpression);
        System.out.println("everydayAt10HourCronjobTest end");
    }
    
    /**
     * At minute 25.
     */
    @Test
    public void everyhourAt25MinuteCronjobTest() {
        System.out.println("everyhourAt25MinuteCronjobTest start");
        ScheduleHelper scheduleHelper = ScheduleHelper.getInstance();
        String cronExpression = scheduleHelper.getCronExpression(0, 25, 4);
        scheduleHelper.scheduleDatabaseBackup(cronExpression);
        System.out.println("everyhourAt25MinuteCronjobTest end");
    }
}
