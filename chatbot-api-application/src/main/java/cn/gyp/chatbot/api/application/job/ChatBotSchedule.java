package cn.gyp.chatbot.api.application.job;


import cn.gyp.chatbot.api.domain.ai.IOpenAI;
import cn.gyp.chatbot.api.domain.zsxq.IZsxqApi;
import cn.gyp.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.gyp.chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

@EnableScheduling
@Configuration
public class ChatBotSchedule {
    private Logger logger = LoggerFactory.getLogger(ChatBotSchedule.class);
    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

    //表达式 cron.qqe2.com
    @Scheduled(cron = "0 0/1 * * * ?")
    public void run() {
        try {
            if (new Random().nextBoolean()) {
                logger.info("随机打烊中。。。。。");
                return;
            }
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            int hour = gregorianCalendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 22 || hour < 7) {
                logger.info("太早了太晚了，不工作了");
                return;
            }

            //查找问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
            logger.info("检索结果: {}", JSON.toJSONString(unAnsweredQuestionsAggregates));

            List<Topics> topicsList = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            if (topicsList == null || topicsList.isEmpty()) {
                logger.info("本次检索未找到问题");
                return;
            }

            //ai回答
            Topics topics = topicsList.get(0);
            String answer = openAI.doChatGpt(topics.getTalk().getText());
            //问题回答
            boolean status = zsxqApi.answer(groupId, cookie, topics.getTopic_id(), answer);
            logger.info("编号：{} 问题：{} 回答：{} 状态：{}", topics.getTopic_id(), topics.getTalk().getText(), answer, status);
        } catch (Exception e) {
            logger.info("自动回答问题异常");
        }
    }


}
