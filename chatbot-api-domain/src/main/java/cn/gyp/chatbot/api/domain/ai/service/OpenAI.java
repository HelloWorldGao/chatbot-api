package cn.gyp.chatbot.api.domain.ai.service;

import cn.gyp.chatbot.api.domain.ai.IOpenAI;
import cn.gyp.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import cn.gyp.chatbot.api.domain.ai.model.vo.Choices;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class OpenAI implements IOpenAI {

    private Logger logger = LoggerFactory.getLogger(OpenAI.class);

    @Value("${chatbot-api.openAiKey}")
    private String openAiKey;

    @Override
    public String doChatGpt(String question) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + openAiKey);

        String parmamJson = "{\n" +
                "     \"model\": \"gpt-3.5-turbo\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \"" + question + "\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";
        StringEntity stringEntity = new StringEntity(parmamJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse httpResponse = httpClient.execute(post);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String stringStr = EntityUtils.toString(httpResponse.getEntity());
            logger.info("调用OpenAI接口得到答案 question：{} ", question);
            AIAnswer aiAnswer = JSON.parseObject(stringStr, AIAnswer.class);
            StringBuilder answer = new StringBuilder();
            List<Choices> choicesList = aiAnswer.getChoices();
            for (Choices choices : choicesList) {
                answer.append(choices.getText());
            }
            return answer.toString();
        } else {
            throw new RuntimeException("doChatGpt Err Code is" + httpResponse.getStatusLine().getStatusCode());
        }
    }
}
