package com.example.findproject;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class requester {
    OkHttpClient client = new OkHttpClient();

    public int getRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }

    public static void main(String[] args) throws IOException {
        //requester example = new requester();
        //String response = example.getRequest("https://raw.github.com/square/okhttp/master/README.md");
        //System.out.println(response);
    }
}
