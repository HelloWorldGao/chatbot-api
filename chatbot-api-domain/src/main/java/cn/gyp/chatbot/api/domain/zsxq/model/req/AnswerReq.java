package cn.gyp.chatbot.api.domain.zsxq.model.req;

import cn.gyp.chatbot.api.domain.zsxq.model.res.RespData;

public class AnswerReq {
    private ReqData req_data;

    public AnswerReq(ReqData req_data) {
        this.req_data = req_data;
    }

    public ReqData getReq_data() {
        return req_data;
    }

    public void setReq_data(ReqData req_data) {
        this.req_data = req_data;
    }
}
