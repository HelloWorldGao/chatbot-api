package cn.gyp.chatbot.api.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class ApiTest {

    @Test
    public void query_unanswered_question() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/51112155528824/topics?scope=all&count=20");
        get.addHeader("cookie","zsxq_access_token=FC18685C-E92D-C144-7A29-C7B9538A36E1_09A9AB306DE08273; abtest_env=product; zsxqsessionid=17f24fc027ed11b1af235cc777b112bb; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross={\"distinct_id\":\"815145848488142\",\"first_id\":\"1872ba60a1211ae-056c04c69e96288-26031951-3686400-1872ba60a1311cb\",\"props\":{},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg3MmJhNjBhMTIxMWFlLTA1NmMwNGM2OWU5NjI4OC0yNjAzMTk1MS0zNjg2NDAwLTE4NzJiYTYwYTEzMTFjYiIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjgxNTE0NTg0ODQ4ODE0MiJ9\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"815145848488142\"},\"$device_id\":\"1872ba60a1211ae-056c04c69e96288-26031951-3686400-1872ba60a1311cb\"}");
        get.addHeader("Content-Type","application/json;charset=utf8");

        CloseableHttpResponse httpResponse = httpClient.execute(get);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(res);
        }else {
            System.out.println(httpResponse.getStatusLine().getStatusCode());
        }

    }

    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/412584154125848/comments");
        post.addHeader("cookie","zsxq_access_token=FC18685C-E92D-C144-7A29-C7B9538A36E1_09A9AB306DE08273; abtest_env=product; zsxqsessionid=17f24fc027ed11b1af235cc777b112bb; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross={\"distinct_id\":\"815145848488142\",\"first_id\":\"1872ba60a1211ae-056c04c69e96288-26031951-3686400-1872ba60a1311cb\",\"props\":{},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg3MmJhNjBhMTIxMWFlLTA1NmMwNGM2OWU5NjI4OC0yNjAzMTk1MS0zNjg2NDAwLTE4NzJiYTYwYTEzMTFjYiIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjgxNTE0NTg0ODQ4ODE0MiJ9\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"815145848488142\"},\"$device_id\":\"1872ba60a1211ae-056c04c69e96288-26031951-3686400-1872ba60a1311cb\"}");
        post.addHeader("Content-Type","application/json;charset=utf8");

        String parmamJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"我也不会\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"mentioned_user_ids\": []\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(parmamJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse httpResponse = httpClient.execute(post);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(res);
        }else {
            System.out.println(httpResponse.getStatusLine().getStatusCode());
        }

    }


}
