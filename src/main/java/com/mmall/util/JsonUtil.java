package com.mmall.util;

import com.google.common.collect.Lists;
import com.mmall.pojo.Category;
import com.mmall.pojo.TestPojo;
import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        //包含对象的字段 ALWAYS--全部列入,NON_NULL--非空的列入，NON_DEFAULT--除去DEFAULT的值的非空的列入,NON_EMPTY--非空的结果显示
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);

        //取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS,false);

        //忽略空Bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);

        //所有的日期都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //忽略在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public static <T> String obj2String(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse object to String error",e);
            return null;
        }
    }

    public static <T> String obj2StringPretty(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj :
                                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse object to String error",e);
            return null;
        }
    }

    //将字符串转换为java对象
    public static <T> T string2Obj(String str, Class<T> clazz){

        /*//下面的语句在执行时，序列化为Json时，涉及三个对象，反序列化的时候，如何接收三个对象是一个问题
        Map<User, Category> map = new HashMap<>();*/

        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T)str : objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str,typeReference));
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);

        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }


    public static void main(String[] args) {
        /*
        //测试目的是展示以及理解
        //忽略在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        //objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        TestPojo testPojo = new TestPojo();
        testPojo.setName("leaf");
        testPojo.setId(666);
        String json = "{\n" +
                "  \"name\" : \"leaf\",\n" +
                "  \"id\" : 666,\n" + "\"blue\":\"blue\"\n" +
                "}";
        TestPojo testPojoObj = JsonUtil.string2Obj(json,TestPojo.class);*/
        log.info("end");

/*
//忽略空Bean转json的错误
//objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
//展示理解
//        String testPojoJson = JsonUtil.obj2StringPretty(testPojo);
//        log.info("testPojoJson:{}",testPojoJson);
*/

//        User u1 = new User();
//        u1.setId(2);
//        u1.setEmail("leaf@happymall.com");
//        u1.setCreateTime(new Date());
//        String user1JsonPretty = JsonUtil.obj2StringPretty(u1);
//        log.info("user1JsonPretty:{}",user1JsonPretty);

//        User u2 = new User();
//        u2.setId(2);
//        u2.setEmail("leaf2@happymall.com");
//
//        String user1Json = JsonUtil.obj2String(u1);
//
//        String user1JsonPretty = JsonUtil.obj2StringPretty(u1);
//
//        log.info("user1Json:{}",user1Json);
//        log.info("user1JsonPretty:{}",user1JsonPretty);
//
//        //反序列化后，是一个User但是和u1不是同一个对象，判断是否为同一个对象可以观察其内存地址
//        User user = JsonUtil.string2Obj(user1Json,User.class);
//
//        List<User> userList = Lists.newArrayList();
//        userList.add(u1);
//        userList.add(u2);
//
//        String userListStr = JsonUtil.obj2String(userList);
//        log.info("===========");
//        log.info(userListStr);
//
//        //反序列化后是一个LinkedHashMap，反序列化后会出现的问题是：userListObj并没有getId()方法，因为userListObj是一个LinkedHashMap
//        List<User> userListObj = JsonUtil.string2Obj(userListStr,List.class);
//        List<User> userListObj1 = JsonUtil.string2Obj(userListStr, new TypeReference<List<User>>() {
//        });
//
//        List<User> userListObj2 = JsonUtil.string2Obj(userListStr, List.class, User.class);

        System.out.println("end");
    }
}
