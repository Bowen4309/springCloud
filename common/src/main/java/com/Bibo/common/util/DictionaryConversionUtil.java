package com.Bibo.common.util;

import com.Bibo.common.annotation.FiledConverted;
import com.Bibo.common.annotation.ObjConverted;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @program:
 * @description: 字段字典转化
 * @author: yuanbo
 * @create: 2021-11-24 15:42
 **/

public class DictionaryConversionUtil {

    private static String OBJECT_STR = "java.lang.object";

    public static Map<String,Map<String,String>> redisData = null;

    public static void converter(Object object) {
        try {
            if (object != null) {
                if (object instanceof List) {
                    //listObjectConverter(object.getClass(), (List<?>) object);
                    List<?> list = (List<?>)object;
                    list.forEach(childObj ->{
                        try {
                            singleObjectConverter(childObj.getClass(), childObj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    singleObjectConverter(object.getClass(), object);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归转换List集合
     */
    private static void listObjectConverter(Class<?> clazz, List<?> beanList) throws Exception {
        if (CollectionUtils.isNotEmpty(beanList)) {
            beanList.forEach(t ->{
                Class<?> aclass= t.getClass();
                Field[] fields = aclass.getFields();
            });
            //本类及其父类的属性集合
            List<Field> fieldList = new ArrayList<>();
            //递归获取本身及父类的属性
            //当父类不是object并且为null的时候说明到达了最上层的父类(form继承的Entity类).
            while (clazz != null && !OBJECT_STR.equals(clazz.getName().toLowerCase())) {
                //反射本类所有属性，包括私有属性
                fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
                //得到父类,然后赋给自己
                clazz = clazz.getSuperclass();
            }
            //带有有@Merge注解的字段集合
            List<Field> filedConverts = new ArrayList<>();
            // 获取的字典Map
            Map<String, Map<String, String>> invokes = new HashMap<>(8);
            //注解中的参数集合
            Map<String, String> annoParms = new HashMap<>(8);
            // 获取注解属性
            filedFilter(true, fieldList, null, beanList, filedConverts, invokes, annoParms);
            // 替换字段字典值
            for (Object object : beanList) {
                if (object != null) {
                    replaceFieldValue(object, filedConverts, invokes, annoParms);
                }
            }
        }
    }

    /**
     * 递归转换单个对象
     */
    private static void singleObjectConverter(Class<?> clazz, Object beanObj) throws Exception {
        //本类及其父类的属性集合
        List<Field> fieldList = new ArrayList<>();
        //递归获取本身及父类的属性
        //当父类不是object并且为null的时候说明到达了最上层的父类(form继承的Entity类).
        while (clazz != null && !OBJECT_STR.equals(clazz.getName().toLowerCase())) {
            //反射本类所有属性，包括私有属性
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            //得到父类,然后赋给自己
            clazz = clazz.getSuperclass();
        }
        // 带有有@filedConverted注解的字段集合
        List<Field> filedConverts = new ArrayList<>();
        // 需要转换的字段的字典Map
        Map<String, Map<String, String>> invokes = new HashMap<>(8);
        // 注解中的参数集合
        Map<String, String> annoParms = new HashMap<>(8);
        // 获取属性
        filedFilter(false, fieldList, beanObj, null, filedConverts, invokes, annoParms);
        // 替换字段字典值
        replaceFieldValue(beanObj, filedConverts, invokes, annoParms);
    }

    /**
     * 循环转换字段
     */
    private static void filedFilter(Boolean flag, List<Field> fields, Object beanObj, List<?> beanList, List<Field> filedConverts,
                                    Map<String, Map<String, String>> invokes, Map<String, String> annoParms)
            throws Exception {
        for (Field field : fields) {
            //如果包含集合属性，递归调用合并集合里对象的值
            //例如:当前字段是一个List<T>
            if (field.getGenericType() instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) field.getGenericType();
                //判断具体类的类型
                if (pt.getRawType().equals(List.class)) {
                    // 判断泛型类的类型
                    String className = pt.getActualTypeArguments()[0].getTypeName();
                    if (className.contains("java.util") || className.contains("java.lang")) {
                        continue;
                    }
                    try {
                        if ("T".equals(className)){
                            List<?> sonList = (List<?>) field.get(beanList.get(0));
                        }
                        System.out.println();
                        Class<?> c = Class.forName(className);
                        field.setAccessible(true);
                        //如果是转换集合，并且该集合有子List，先循环替换全部子集合
                        if (flag && beanList != null) {
                            for (Object object : beanList) {
                                if (object != null) {
                                    List<?> sonList = (List<?>) field.get(object);
                                    listObjectConverter(c, sonList);
                                }
                            }
                        }
                        //如果是转换单个对象，先替换子集合
                        else {
                            List<?> sonList = (List<?>) field.get(beanObj);
                            listObjectConverter(c, sonList);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            //对象中包含另一对象
            //例如当前字段是一个XxxxVo,并标注上了@ObjConverted
            ObjConverted objConverted = field.getAnnotation(ObjConverted.class);
            if (objConverted != null) {
                if (CollectionUtils.isNotEmpty(beanList)) {
                    for (Object object : beanList) {
                        if (object != null) {
                            field.setAccessible(true);
                            Object o = field.get(object);
                            singleObjectConverter(o.getClass(), o);
                        }
                    }
                } else {
                    field.setAccessible(true);
                    Object o = field.get(beanObj);
                    if (o != null) {
                        singleObjectConverter(o.getClass(), o);
                    }
                }
            }
            //合并字典值标志注解
            FiledConverted filedConverted = field.getAnnotation(FiledConverted.class);
            //有@FiledConverted注解的字段进行的操作
            if (filedConverted != null) {
                filedConverts.add(field);
                String fieldName = field.getName();
                String mapKey = filedConverted.dictType();
                if (StringUtils.isNotBlank(mapKey)) {
                    // DictionaryCache.dictMap 是我提前从数据库中查询后缓存在内存中的数据字典。
                    // 刷新获取字典缓存,每次都查询一下最近更新时间，如果和上次不一样，就更新缓存。
                    //DictionaryCache.initialize();
                    //Map<String, Map<String, String>> dictMap = (Map<String, Map<String, String>>)RedisUtil.get("state");//DictionaryCache.dictMap;
                    Map<String, String> value =redisData.get(mapKey); //new HashMap<String, String>();//(Map<String, String>) RedisUtil.get(mapKey);// dictMap.get(mapKey);
                    invokes.put(fieldName, value);
                    annoParms.put(fieldName, filedConverted.defaultValue() + ":"
                            + filedConverted.getCodeOrName() + ":" + filedConverted.isRequired());
                } else {
                    throw new RuntimeException("数据属性加工失败,字典类型不能为空:" + field);
                }
            }
        }
    }

    /**
     * 替换字段字典值
     */
    private static void replaceFieldValue(Object beanObj, List<Field> filedConverts,
                                          Map<String, Map<String, String>> invokes, Map<String, String> annoParms) {
        if (invokes != null && invokes.size() > 0) {
            //替换字典值
            for (Field field : filedConverts) {
                field.setAccessible(true);
                Object o;
                boolean isReplace = false;
                String defautValue = "";
                String getCodeOrName = "0";
                boolean isRequired = false;
                if (annoParms != null && annoParms.size() > 0) {
                    String annoParm = annoParms.get(field.getName());
                    if (StringUtils.isNotBlank(annoParm)) {
                        String[] params = annoParm.split(":");
                        defautValue = params[0];
                        getCodeOrName = params[1];
                        isRequired = Boolean.parseBoolean(params[2]);
                    }
                }
                try {
                    o = field.get(beanObj);
                    Map<String, String> beanList;
                    beanList = invokes.get(field.getName());
                    if (beanList != null) {
                        //需要对照的字段是否有值
                        if (o != null) {
                            //判断该字段能否找到对照项，如果找到替换赋值
                            if (beanList.containsKey(String.valueOf(o))) {
                                String[] values = beanList.get(o.toString()).split(";");
                                field.set(beanObj, values[Integer.parseInt(getCodeOrName)]);
                            } else {
                                //没找到对照项，判断是否为必须转换的字段
                                if (isRequired) {
                                    throw new RuntimeException("属性" + field.getName() + "=" + o + "没找到对照项，请核对是否输入有误或缺少对照数据");
                                    //如果不是必须转换的字段，判断是否需要设置默认值
                                } else if (isReplace) {
                                    field.set(beanObj, defautValue);
                                }
                            }
                            //需要对照的字段为null，判断该字段是否为必须转换的字段
                        } else if (isRequired) {
                            throw new RuntimeException("属性" + field.getName() + "没找到对照项，请核对是否输入有误或缺少对照数据");
                            //如果不是必须转换的字段，判断是否需要设置默认值
                        } else if (isReplace) {
                            field.set(beanObj, defautValue);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("数据对照失败:" + field, e);
                }
            }
        }
    }


    public static void main(String[] args) {
       /* Test test = new Test();
        test.setStatus("1");
        List<Test> tests = new ArrayList<Test>();
        tests.add(test);
        // 转换单实体
        TestConversionVo testConversionVo = new TestConversionVo();
        testConversionVo.setTest("1");
        testConversionVo.setTestTempVo(test);
        testConversionVo.setTestTempVoList(tests);
        DictionaryConversionUtil.converter(testConversionVo);

        // 转换实体List
        List<TestConversionVo> testConversionVoList = new ArrayList<>();
        DictionaryConversionUtil.converter(testConversionVoList);*/

    }
}
