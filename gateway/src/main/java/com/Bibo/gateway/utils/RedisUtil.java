//package com.xdh.traffic_getway.utils;
//
//import com.Bibo.common.pojo.LoginUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class RedisUtil {
//
//    private static RedisTemplate<String, Object> MyRedisTemplate;
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    /**
//     * 令牌自定义标识
//     */
//    private static final String AUTHENTICATION = "RZZX-USERTOKEN";
//
//    /**
//     * 登录用户 redis key
//     */
//    public static final String LOGIN_TOKEN_KEY = "user_token:";
//
//    public static final long EXPIRE = 2 * 36000;
//
//    @PostConstruct
//    public void init() {
//        MyRedisTemplate = redisTemplate;
//    }
//    // =============================common============================
//
//    /**
//     * 指定缓存失效时间
//     * @param key  键
//     * @param time 时间(秒)
//     */
//    public static boolean expire(String key, long time) {
//
//        try {
//
//            if (time > 0) {
//
//                MyRedisTemplate.expire(key, time, TimeUnit.SECONDS);
//
//            }
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 根据key 获取过期时间
//     *
//     * @param key 键 不能为null
//     * @return 时间(秒) 返回0代表为永久有效
//     */
//    public static long getExpire(String key) {
//
//        return MyRedisTemplate.getExpire(key, TimeUnit.SECONDS);
//
//    }
//
//    /**
//     * 判断key是否存在
//     *
//     * @param key 键
//     * @return true 存在 false不存在
//     */
//    public static boolean hasKey(String key) {
//
//        try {
//
//            return MyRedisTemplate.hasKey(key);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 删除缓存
//     * @param key 可以传一个值 或多个
//     */
//    @SuppressWarnings("unchecked")
//    public static void del(String... key) {
//
//        if (key != null && key.length > 0) {
//
//            if (key.length == 1) {
//
//                MyRedisTemplate.delete(key[0]);
//
//            } else {
//
//                MyRedisTemplate.delete(String.valueOf(CollectionUtils.arrayToList(key)));
//
//            }
//
//        }
//
//    }
//
//    // ============================String=============================
//
//    /**
//     * 普通缓存获取
//     *
//     * @param key 键
//     * @return 值
//     */
//
//    public static Object get(String key) {
//
//        return key == null ? null : MyRedisTemplate.opsForValue().get(key);
//
//    }
//
//    /**
//     * 普通缓存放入
//     *
//     * @param key   键
//     * @param value 值
//     * @return true成功 false失败
//     */
//    public static boolean set(String key, Object value) {
//
//        try {
//
//            MyRedisTemplate.opsForValue().set(key, value);
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 普通缓存放入并设置时间
//     *
//     * @param key   键
//     * @param value 值
//     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
//     * @return true成功 false 失败
//     */
//    public static boolean set(String key, Object value, long time) {
//
//        try {
//
//            if (time > 0) {
//
//                MyRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
//
//            } else {
//
//                set(key, value);
//
//            }
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 递增
//     * @param key   键
//     * @param delta 要增加几(大于0)
//     * @return 134
//     */
//    public static long incr(String key, long delta) {
//
//        if (delta < 0) {
//
//            throw new RuntimeException("递增因子必须大于0");
//
//        }
//
//        return MyRedisTemplate.opsForValue().increment(key, delta);
//
//    }
//
//    /**
//     * 递减
//     *
//     * @param key   键
//     * @param delta 要减少几(小于0)
//     * @return 147
//     */
//    public static long decr(String key, long delta) {
//
//        if (delta < 0) {
//
//            throw new RuntimeException("递减因子必须大于0");
//
//        }
//
//        return MyRedisTemplate.opsForValue().increment(key, -delta);
//
//    }
//
//    // ================================Map=================================
//
//    /**
//     * HashGet
//     *
//     * @param key  键 不能为null
//     * @param item 项 不能为null
//     * @return 值
//     */
//    public static Object hget(String key, String item) {
//
//        return MyRedisTemplate.opsForHash().get(key, item);
//
//    }
//
//    /**
//     * 获取hashKey对应的所有键值
//     * @param key 键
//     * @return 对应的多个键值
//     */
//    public static Map<Object, Object> hmget(String key) {
//
//        return MyRedisTemplate.opsForHash().entries(key);
//
//    }
//
//    /**
//     * HashSet
//     * @param key 键
//     * @param map 对应多个键值
//     * @return true 成功 false 失败
//     */
//    public static boolean hmset(String key, Map<String, Object> map) {
//
//        try {
//
//            MyRedisTemplate.opsForHash().putAll(key, map);
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * HashSet 并设置时间
//     * @param key  键
//     * @param map  对应多个键值
//     * @param time 时间(秒)
//     * @return true成功 false失败
//     */
//    public static boolean hmset(String key, Map<String, Object> map, long time) {
//
//        try {
//
//            MyRedisTemplate.opsForHash().putAll(key, map);
//
//            if (time > 0) {
//
//                expire(key, time);
//
//            }
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 向一张hash表中放入数据,如果不存在将创建
//     * @param key   键
//     * @param item  项
//     * @param value 值
//     * @return true 成功 false失败
//     */
//
//    public static boolean hset(String key, String item, Object value) {
//
//        try {
//
//            MyRedisTemplate.opsForHash().put(key, item, value);
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 向一张hash表中放入数据,如果不存在将创建
//     * @param key   键
//     * @param item  项
//     * @param value 值
//     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
//     * @return true 成功 false失败
//     */
//    public static boolean hset(String key, String item, Object value, long time) {
//
//        try {
//
//            MyRedisTemplate.opsForHash().put(key, item, value);
//
//            if (time > 0) {
//
//                expire(key, time);
//
//            }
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 删除hash表中的值
//     *
//     * @param key  键 不能为null
//     * @param item 项 可以使多个 不能为null
//     */
//    public static void hdel(String key, Object... item) {
//
//        MyRedisTemplate.opsForHash().delete(key, item);
//
//    }
//
//    /**
//     * 判断hash表中是否有该项的值
//     *
//     * @param key  键 不能为null
//     * @param item 项 不能为null
//     * @return true 存在 false不存在
//     */
//    public static boolean hHasKey(String key, String item) {
//
//        return MyRedisTemplate.opsForHash().hasKey(key, item);
//
//    }
//
//    /**
//     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
//     * @param key  键
//     * @param item 项
//     * @param by   要增加几(大于0)
//     * @return
//     */
//    public static double hincr(String key, String item, double by) {
//
//        return MyRedisTemplate.opsForHash().increment(key, item, by);
//
//    }
//
//    /**
//     * hash递减
//     *
//     * @param key  键
//     * @param item 项
//     * @param by   要减少记(小于0)
//     */
//    public static double hdecr(String key, String item, double by) {
//
//        return MyRedisTemplate.opsForHash().increment(key, item, -by);
//
//    }
//
//    // ============================set=============================
//
//    /**
//     * 根据key获取Set中的所有值
//     * @param key 键
//     * @return
//     */
//    public static Set<Object> sGet(String key) {
//
//        try {
//
//            return MyRedisTemplate.opsForSet().members(key);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return null;
//
//        }
//
//    }
//
//    /**
//     * 根据value从一个set中查询,是否存在
//     *
//     * @param key   键
//     * @param value 值
//     * @return true 存在 false不存在
//     */
//    public static boolean sHasKey(String key, Object value) {
//
//        try {
//
//            return MyRedisTemplate.opsForSet().isMember(key, value);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 将数据放入set缓存
//     * @param key    键
//     * @param values 值 可以是多个
//     * @return 成功个数
//     */
//    public static long sSet(String key, Object... values) {
//
//        try {
//
//            return MyRedisTemplate.opsForSet().add(key, values);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return 0;
//
//        }
//
//    }
//
//    /**
//     * 将set数据放入缓存
//     *
//     * @param key    键
//     * @param time   时间(秒)
//     * @param values 值 可以是多个
//     * @return 成功个数
//     */
//    public static long sSetAndTime(String key, long time, Object... values) {
//
//        try {
//
//            Long count = MyRedisTemplate.opsForSet().add(key, values);
//
//            if (time > 0)
//
//                expire(key, time);
//
//            return count;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return 0;
//
//        }
//
//    }
//
//    /**
//     * 获取set缓存的长度
//     * @param key 键
//     */
//    public static long sGetSetSize(String key) {
//
//        try {
//
//            return MyRedisTemplate.opsForSet().size(key);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return 0;
//
//        }
//
//    }
//
//    /**
//     * 移除值为value的
//     * @param key    键
//     * @param values 值 可以是多个
//     * @return 移除的个数
//     */
//    public static long setRemove(String key, Object... values) {
//
//        try {
//
//            Long count = MyRedisTemplate.opsForSet().remove(key, values);
//
//            return count;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return 0;
//
//        }
//
//    }
//
//    // ===============================list=================================
//
//    /**
//     * 获取list缓存的内容
//     *
//     * @param key   键
//     * @param start 开始
//     * @param end   结束 0 到 -1代表所有值
//     */
//    public static List<Object> lGet(String key, long start, long end) {
//
//        try {
//
//            return MyRedisTemplate.opsForList().range(key, start, end);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return null;
//
//        }
//
//    }
//
//    /**
//     * 获取list缓存的长度
//     * @param key 键
//     */
//    public static long lGetListSize(String key) {
//
//        try {
//
//            return MyRedisTemplate.opsForList().size(key);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return 0;
//
//        }
//
//    }
//
//    /**
//     * 通过索引 获取list中的值
//     *
//     * @param key   键
//     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
//     */
//
//    public static Object lGetIndex(String key, long index) {
//
//        try {
//
//            return MyRedisTemplate.opsForList().index(key, index);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return null;
//
//        }
//
//    }
//
//    /**
//     * 将list放入缓存
//     *
//     * @param key   键
//     * @param value 值
//     */
//    public static boolean lSet(String key, Object value) {
//
//        try {
//
//            MyRedisTemplate.opsForList().rightPush(key, value);
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 将list放入缓存
//     *
//     * @param key   键
//     * @param value 值
//     * @param time  时间(秒)
//     * @return
//     */
//    public static boolean lSet(String key, Object value, long time) {
//
//        try {
//
//            MyRedisTemplate.opsForList().rightPush(key, value);
//
//            if (time > 0)
//
//                expire(key, time);
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 将list放入缓存
//     * @param key   键
//     * @param value 值
//     */
//    public static boolean lSet(String key, List<Object> value) {
//
//        try {
//
//            MyRedisTemplate.opsForList().rightPushAll(key, value);
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 将list放入缓存
//     *
//     * @param key   键
//     * @param value 值
//     * @param time  时间(秒)
//     */
//    public static boolean lSet(String key, List<Object> value, long time) {
//
//        try {
//
//            MyRedisTemplate.opsForList().rightPushAll(key, value);
//
//            if (time > 0)
//
//                expire(key, time);
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 根据索引修改list中的某条数据
//     * @param key   键
//     * @param index 索引
//     * @param value 值
//     */
//    public static boolean lUpdateIndex(String key, long index, Object value) {
//
//        try {
//
//            MyRedisTemplate.opsForList().set(key, index, value);
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return false;
//
//        }
//
//    }
//
//    /**
//     * 移除N个值为value
//     *
//     * @param key   键
//     * @param count 移除多少个
//     * @param value 值
//     * @return 移除的个数
//     */
//
//    public static long lRemove(String key, long count, Object value) {
//
//        try {
//
//            Long remove = MyRedisTemplate.opsForList().remove(key, count, value);
//
//            return remove;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return 0;
//
//        }
//
//    }
//
//
//
//    /**
//     * 获取redis里的用户信息
//     * @param
//     * @return
//     */
//    public static LoginUser checkUser(String token) {
//        // 获取请求携带的令牌
//        LoginUser loginUser = (LoginUser) RedisUtil.get(LOGIN_TOKEN_KEY + token);
//        if (loginUser!=null){
//            return loginUser;
//        }
//        return null;
//    }
//    /**
//     * 存放用户信息到redis
//     * @param
//     * @return
//     */
//    public static void setUser(LoginUser loginUser,String token) {
//        String userKey = LOGIN_TOKEN_KEY + token;
//
//        RedisUtil.set(userKey, loginUser, EXPIRE);
//    }
//
//}
