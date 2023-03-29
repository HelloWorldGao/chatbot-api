package cn.gyp.chatbot.api.domain.zsxq.model.vo;

import cn.gyp.chatbot.api.domain.zsxq.model.res.RespData;

public class Root {
    private boolean succeeded;

    private RespData respData;

    public void setSucceeded(boolean succeeded){
        this.succeeded = succeeded;
    }
    public boolean getSucceeded(){
        return this.succeeded;
    }
    public void setResp_data(RespData resp_data){
        this.respData = resp_data;
    }
    public RespData getResp_data(){
        return this.respData;
    }
}
