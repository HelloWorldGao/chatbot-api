package cn.gyp.chatbot.api.domain.zsxq;

import cn.gyp.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;

import java.io.IOException;

/**
 * @author gyp
 */
public interface IZsxqApi {
    /**
     *
     * 查询知识星球最新的问答
     *
     * @param groupId
     * @param cookie
     * @return
     * @throws IOException
     */
    UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException;

    /**
     * 回答知识星球问题
     *
     * @param groupId
     * @param cookie
     * @param topicId
     * @param text
     * @return
     * @throws IOException
     */
    boolean answer(String groupId,String cookie,String topicId,String text)throws IOException;
}
