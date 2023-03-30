package cn.gyp.chatbot.api.test;

import cn.gyp.chatbot.api.domain.ai.IOpenAI;
import cn.gyp.chatbot.api.domain.zsxq.IZsxqApi;
import cn.gyp.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.gyp.chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);
    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
        logger.info("测试结果: {}", JSON.toJSONString(unAnsweredQuestionsAggregates));

        List<Topics> topicsList = unAnsweredQuestionsAggregates.getResp_data().getTopics();
        for (Topics topics : topicsList) {
            String topicId = topics.getTopic_id();
            String text = topics.getTalk().getText();
            logger.info("topicId: {} text: {}", topicId, text);

            //回答问题
            zsxqApi.answer(groupId, cookie, topicId, text);

        }
    }


}
