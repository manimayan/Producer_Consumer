package com.ford.rabbitmq.application.util;

import com.ford.rabbitmq.model.OutputData;
import com.ford.rabbitmq.model.Response;

import java.util.ArrayList;
import java.util.List;

public class ConsumerTestData {

    public static Response getResponse() {
        Response response = new Response();
        response.setOutputData(getOutputData());
        response.setRefresh("hello");
        response.setMessage("test");
        response.setStatus(true);
        return response;
    }

    public static List<OutputData> getOutputData() {
        List<OutputData> output = new ArrayList<>();
        OutputData item = new OutputData();
        item.setId(2);
        item.setNumberOne(21);
        item.setNumberTwo(12);
        item.setOperator("+");
        item.setResult(90);
        output.add(item);
        return output;
    }
}
