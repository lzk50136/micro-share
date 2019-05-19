package online.reiam.share.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
public class ScheduledTask {

    /**
     * cron表达式
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void executeScheduledTask() {

    }

}
