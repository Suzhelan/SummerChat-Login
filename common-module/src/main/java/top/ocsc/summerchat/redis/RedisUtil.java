package top.ocsc.summerchat.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil<K, V> {

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */

    public V getCacheValue(K key) {
        ValueOperations<K, V> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 获取简单的key:value键值对
     *
     * @param key          缓存key
     * @param defaultValue 如果没有查询到缓存则返回此值
     */

    public V getCacheValue(K key, V defaultValue) {
        ValueOperations<K, V> operations = redisTemplate.opsForValue();
        V value = operations.get(key);
        if (value == null) return defaultValue;
        return value;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */

    public void setCacheValue(K key, final V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */

    public void setCacheValue(final K key, final V value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }


    public Boolean hasKey(final K key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取key过期时间
     *
     * @param key key
     * @return 为-1没设置时间 -2为没有此key 为0永久缓存 其他返回时间秒
     */

    public Long getExpire(K key) {
        return redisTemplate.getExpire(key);
    }

    public Long getExpire(K key,TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }
    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间 单位秒
     * @return true=设置成功；false=设置失败
     */

    public boolean expire(final K key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */

    public boolean expire(final K key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 删除单个对象
     *
     * @param key key
     * @return 删除是否成功
     */

    public boolean delete(final K key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return 成功数
     */

    public long delete(final Collection<K> collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */

    public long setCacheList(final K key, final List<V> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List<V> getCacheList(final K key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     */

    public final void addToSetCache(final K key, V... values) {
        SetOperations<K, V> operations = redisTemplate.opsForSet();
        operations.add(key, values);
    }

    /**
     * 获得缓存的set
     *
     */

    public Set<V> getCacheSet(final K key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     */
    public <HK, HV> void setCacheMap(final K key, final Map<HK, HV> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key key
     * @return map
     */
    public <HK, HV> Map<HK, HV> getCacheMap(final K key) {
        HashOperations<K, HK, HV> opsForHash = redisTemplate.opsForHash();
        return opsForHash.entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hash  Hash键
     * @param value 值
     */
    public <HK, HV> void setCacheMapValue(final K key, final HK hash, final HV value) {
        redisTemplate.opsForHash().put(key, hash, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hash Hash键
     * @return Hash中的对象
     */
    public <HK, HV> HV getCacheMapValue(final K key, final HK hash) {
        HashOperations<K, HK, HV> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hash);
    }


    public void incrementCacheMapValue(K key, K hKey, int v) {
        redisTemplate.opsForHash().increment(key, hKey, v);
    }

    /**
     * 删除Hash中的数据
     *
     */
    public <HK, HV> void deleteCacheMapValue(final K key, final HK hash) {
        HashOperations<K, HK, HV> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hash);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key      Redis键
     * @param hashKeys Hash键集合
     * @return HashMap内value合集
     */
    public <HK, HV> List<HV> getMultiCacheMapValue(final K key, final Collection<HK> hashKeys) {
        HashOperations<K, HK, HV> opsForHash = redisTemplate.opsForHash();
        return opsForHash.multiGet(key, hashKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<K> keys(final K pattern) {
        return redisTemplate.keys((K) (pattern+"*"));
    }

}
