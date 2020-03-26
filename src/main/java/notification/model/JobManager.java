package notification.model;

import notification.javabean.Notification;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobManager implements Job {

        public void execute(JobExecutionContext context) throws JobExecutionException {
            JobDataMap dataMap =   context.getJobDetail().getJobDataMap();
            Notification notification = (Notification)dataMap.get("notification");

            System.out.println(notification.getName());
            JobHandle jobHandle  =new JobHandle();
            jobHandle.doJob(notification.getName());




            jobHandle = null;
            ///這裡缺 用name去資料庫把資料拉出來 資後包成 學長所需的格式並post給他
////////////////////////////////////////////////////////////
//            SchedulerContext schedulerContext = null;
//            try {
//                schedulerContext = context.getScheduler().getContext();
//            } catch (SchedulerException e1) {
//                e1.printStackTrace();
//            }
//            Set set =
//                    (Set) schedulerContext.get("set");
//
///////////////////////////////////////////////////////////





        }


}
