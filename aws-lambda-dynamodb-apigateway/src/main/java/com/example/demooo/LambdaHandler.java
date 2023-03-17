package com.example.demooo;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    @Override
    public APIGatewayProxyResponseEvent  handleRequest(APIGatewayProxyRequestEvent apiGatewayRequest, Context context) {
        StudentService StudentService = new StudentService();
        switch (apiGatewayRequest.getHttpMethod()) {

            case "POST":
                return StudentService.saveStudent(apiGatewayRequest, context);

            case "GET":
                if (apiGatewayRequest.getPathParameters() != null) {
                    return StudentService.getStudentById(apiGatewayRequest, context);
                }
                return StudentService.getStudents(apiGatewayRequest, context);
            case "DELETE":
                if (apiGatewayRequest.getPathParameters() != null) {
                    return StudentService.deleteStudentById(apiGatewayRequest, context);
                }
            default:
                throw new Error("Unsupported Methods:::" + apiGatewayRequest.getHttpMethod());

        }
    }
 }
