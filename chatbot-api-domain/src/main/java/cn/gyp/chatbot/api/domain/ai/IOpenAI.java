package cn.gyp.chatbot.api.domain.ai;

import java.io.IOException;

/**
 * @author gyp
 */
public interface IOpenAI {

    /**
     * 调用OpenAI接口得到答案
     *
     * @param question
     * @return
     * @throws IOException
     */
    public String doChatGpt(String question) throws IOException;
}
