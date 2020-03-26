package notification.javabean;

public class ScheduleJob {
    /** job名稱 **/
    private String jobName;

    /** job組 **/
    private String jobGroupName = "QUARTZ_JOB_GROUP";// 預設組名

    /** trigger名稱 **/
    private String triggerName;// 未賦值時預設返回jobName

    /** jtrigger組 **/
    private String triggerGroupName = "QUARTZ_TRIGGER_GROUP";// 預設組名

    /** cron時間表達式 **/
    private String cronExpression;

    /** 任務執行的類名：service類名 **/
    private String springId;

    /** 任務執行的類名：包名+類名 **/
    private String beanClass;

    /** 任務執行的方法名 **/
    private String methodName;

    /** 是否自定義 **/
    private String isZdy = "n";// 反射時預設不指定引數型別

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getTriggerName() {
        if(triggerName == null || "".equals(triggerName)) {
            return jobName;
        }else {
            return triggerName;
        }
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getSpringId() {
        return springId;
    }

    public void setSpringId(String springId) {
        this.springId = springId;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getIsZdy() {
        return isZdy;
    }

    public void setIsZdy(String isZdy) {
        this.isZdy = isZdy;
    }
}