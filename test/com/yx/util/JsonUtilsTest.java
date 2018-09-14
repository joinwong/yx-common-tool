package com.yx.util;

import com.fasterxml.jackson.core.type.TypeReference;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by join on 2018/9/14.
 */
public class JsonUtilsTest extends TestCase {
    String strJson = "{\"name\":\"cat\",\"age\":12}";

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testDeserialize() throws Exception {
        Animal animal = JsonUtils.getInstance().deserialize(strJson,Animal.class);

        Assert.assertNotNull(animal);
        Assert.assertEquals("cat",animal.getName());
        Assert.assertEquals(12,animal.getAge());
    }

    public void testDeserializeBytes() throws Exception {
    }

    public void testString2Object() throws Exception {
        Animal animal = JsonUtils.getInstance().string2Object(strJson,Animal.class);

        Assert.assertNotNull(animal);
        Assert.assertEquals("cat",animal.getName());
        Assert.assertEquals(12,animal.getAge());
    }

    public void testDeserialize1() throws Exception {
        List<Animal> list = new ArrayList<>();

        Animal animal = new Animal();
        animal.setName("cat");
        animal.setAge(12);
        list.add(animal);

        animal = new Animal();
        animal.setName("dog");
        animal.setAge(8);

        list.add(animal);

        String strJson = JsonUtils.getInstance().object2String(list);

        List<Animal> actual = JsonUtils.getInstance().deserialize(strJson, new TypeReference<List<Animal>>() {
        });

        Assert.assertNotNull(actual);
        Assert.assertEquals(list.size(),actual.size());
        Assert.assertEquals(list.get(0).getName(),actual.get(0).getName());
        Assert.assertEquals(list.get(0).getAge(),actual.get(0).getAge());
        Assert.assertEquals(list.get(1).getName(),actual.get(1).getName());
        Assert.assertEquals(list.get(1).getAge(),actual.get(1).getAge());
    }

    public void testSerialize() throws Exception {
        Animal animal = new Animal();
        animal.setName("cat");
        animal.setAge(12);

        String json = JsonUtils.getInstance().serialize(animal);

        Assert.assertNotNull(json);
        Assert.assertEquals(strJson,json);
    }

    public void testString2Object1() throws Exception {
    }

    public void testObject2String() throws Exception {
        Animal animal = new Animal();
        animal.setName("cat");
        animal.setAge(12);

        String json = JsonUtils.getInstance().object2String(animal);

        Assert.assertNotNull(json);
        Assert.assertEquals(strJson,json);
    }

    public void testConvertObject() throws Exception {
        Map<Object,Object> dataMap = new HashMap<>();
        dataMap.put("name","cat");
        dataMap.put("age",12);

        Animal animal = new Animal();
        animal.setName("cat");
        animal.setAge(12);

        Animal actual = JsonUtils.getInstance().convertObject(dataMap,Animal.class);

        Assert.assertNotNull(actual);
        Assert.assertEquals(animal.getName(),actual.getName());
        Assert.assertEquals(animal.getAge(),actual.getAge());
    }
}

class Animal {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}