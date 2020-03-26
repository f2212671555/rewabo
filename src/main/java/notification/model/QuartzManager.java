package notification.model;

import notification.javabean.Notification;
import notification.javabean.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;



public class QuartzManager {

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /**
     *  新增一個定時任務
     */

    public static void addJob(ScheduleJob scheduleJob, Notification notification) {
        try {
            JobDataMap dataMap = new JobDataMap();
            dataMap.put("notification",notification);
            Scheduler scheduler = schedulerFactory.getScheduler();
            // 任務名，任務組，任務執行類
            JobDetail jobDetail = JobBuilder.newJob(JobManager.class)
                    .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroupName())
                    .usingJobData("name",notification.getName())
                    .usingJobData(dataMap)
                    .build();
            jobDetail.getJobDataMap().put("scheduleJob", scheduleJob); //JobDataMap可用於儲存任何您希望在執行時對作業例項可用的資料物件

            // 觸發器
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduleJob.getTriggerName(), scheduleJob.getTriggerGroupName())
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()))
                    .build();
            // 排程容器設定JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);
            // 啟動
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一個任務的觸發時間
     */
    public static void modifyJobTime(ScheduleJob scheduleJob) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getTriggerName(), scheduleJob.getTriggerGroupName());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(scheduleJob.getCronExpression())) {
                trigger = //TriggerBuilder.newTrigger()
                        trigger.getTriggerBuilder()
                                .withIdentity(triggerKey)
                                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()))
                                .build();
                // 修改一個任務的觸發時間
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 暫停一個任務
     */
    public static void pauseJob(ScheduleJob scheduleJob) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroupName());
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢復一個任務
     */
    public static void resumeJob(ScheduleJob scheduleJob) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroupName());
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 立即執行一個任務
     *
     * 說明：
     * 		這裡的立即執行，只會執行一次，方便測試時用。
     * 		quartz是通過臨時生成一個trigger的方式來實現的，這個trigger將在本次任務執行完成之後自動刪除。
     */
    public static void triggerJob(ScheduleJob scheduleJob) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroupName());
            scheduler.triggerJob(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除一個任務
     */
    public static void removeJob(ScheduleJob scheduleJob) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getTriggerName(), scheduleJob.getTriggerGroupName());
            // 停止觸發器
            scheduler.pauseTrigger(triggerKey);
            // 移除觸發器
            scheduler.unscheduleJob(triggerKey);
            // 刪除任務
            scheduler.deleteJob(JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroupName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * 啟動所有定時任務
     */
    public static void startJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 關閉所有定時任務
     */
    public static void shutdownJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}