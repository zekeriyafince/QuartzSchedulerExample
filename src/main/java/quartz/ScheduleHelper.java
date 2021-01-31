package quartz;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.CronScheduleBuilder;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Quartz Schedule Builder Helper
 *
 * @author Zekeriya Furkan İNCE
 * @date 31.01.2021 14:25
 */
public class ScheduleHelper {

    public static final int EVERYDAY = 0;
    public static final int ODDDAYS = 1;
    public static final int EVERYMONDAY = 2;
    public static final int EVERYMINUTE = 3;
    public static final int ATMINUTE = 4;

    private static ScheduleHelper scheduleHelper;
    private Scheduler scheduler;

    public ScheduleHelper() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException ex) {
            Logger.getLogger(ScheduleHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCronExpression(int hour, int minute, int type) {
        String cronExpression = null;
        switch (type) {
            case EVERYDAY:
                cronExpression = "0 " + minute + " " + hour + " 1/1 * ? *";
                break;
            case ODDDAYS:
                cronExpression = "0 " + minute + " " + hour + " 1/2 * ? *";
                break;
            case EVERYMONDAY:
                cronExpression = "0 " + minute + " " + hour + " ? * MON *";
                break;
            case EVERYMINUTE:
                cronExpression = "0 */" + minute + " * 1/1 * ? *";
                break;
            case ATMINUTE:
                cronExpression = "0 " + minute + " 0-23 * * ? *";
                break;
        }
        return cronExpression;
    }

    public void scheduleDatabaseBackup(String cronExpression) {
        JobKey jobKey = new JobKey("databasejob", "databasegroup");
        try {
            if (scheduleHelper.scheduler.checkExists(jobKey)) {
                scheduleHelper.scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException ex) {
            Logger.getLogger(ScheduleHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        JobDetail job = newJob(DatabaseBackup.class)
                .withIdentity(jobKey)
                .build();
        ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        Trigger trigger = newTrigger()
                .withIdentity("databasetrigger", "databasegroup")
                .startNow()
                .withSchedule(scheduleBuilder)
                .build();
        try {
            scheduleHelper.scheduler.scheduleJob(job, trigger);
            System.out.println("--- > " + trigger.getKey().getName() + "database nexttime:" + trigger.getNextFireTime());
            Logger.getLogger(ScheduleHelper.class.getName()).log(Level.INFO, trigger.getKey().getName() + "database nexttime:" + trigger.getNextFireTime());
        } catch (SchedulerException ex) {
            Logger.getLogger(ScheduleHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ScheduleHelper getInstance() {
        if (scheduleHelper == null) {
            scheduleHelper = new ScheduleHelper();
        }
        return scheduleHelper;
    }
}
