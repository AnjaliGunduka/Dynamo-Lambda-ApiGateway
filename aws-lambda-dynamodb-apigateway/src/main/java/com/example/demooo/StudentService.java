package com.example.demooo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.demooo.entity.Student;

import java.util.List;
import java.util.Map;

public class StudentService {
    private DynamoDBMapper dynamoDBMapper;
    private static  String jsonBody = null;

    public APIGatewayProxyResponseEvent saveStudent(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        Student Student = Utility.convertStringToObj(apiGatewayRequest.getBody(),context);
        dynamoDBMapper.save(Student);
        jsonBody = Utility.convertObjToString(Student,context) ;
        context.getLogger().log("data saved successfully to dynamodb:::" + jsonBody);
        return createAPIResponse(jsonBody,200,Utility.createHeaders());
    }
    public APIGatewayProxyResponseEvent getStudentById(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        String empId = apiGatewayRequest.getPathParameters().get("empId");
        Student Student =   dynamoDBMapper.load(Student.class,empId)  ;
        if(Student!=null) {
            jsonBody = Utility.convertObjToString(Student, context);
            context.getLogger().log("fetch Student By ID:::" + jsonBody);
             return createAPIResponse(jsonBody,200,Utility.createHeaders());
        }else{
            jsonBody = "Student Not Found Exception :" + empId;
             return createAPIResponse(jsonBody,400,Utility.createHeaders());
        }
       
    }

    public APIGatewayProxyResponseEvent getStudents(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        List<Student> Students = dynamoDBMapper.scan(Student.class,new DynamoDBScanExpression());
        jsonBody =  Utility.convertListOfObjToString(Students,context);
        context.getLogger().log("fetch Student List:::" + jsonBody);
        return createAPIResponse(jsonBody,200,Utility.createHeaders());
    }
    public APIGatewayProxyResponseEvent deleteStudentById(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        String empId = apiGatewayRequest.getPathParameters().get("empId");
        Student Student =  dynamoDBMapper.load(Student.class,empId)  ;
        if(Student!=null) {
            dynamoDBMapper.delete(Student);
            context.getLogger().log("data deleted successfully :::" + empId);
            return createAPIResponse("data deleted successfully." + empId,200,Utility.createHeaders());
        }else{
            jsonBody = "Student Not Found Exception :" + empId;
            return createAPIResponse(jsonBody,400,Utility.createHeaders());
        }
    }


    private void initDynamoDB(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDBMapper = new DynamoDBMapper(client);
    }
    private APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode, Map<String,String> headers ){
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(body);
        responseEvent.setHeaders(headers);
        responseEvent.setStatusCode(statusCode);
        return responseEvent;
    }

}
