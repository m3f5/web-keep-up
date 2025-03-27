package org.m3f5.web.ehcache;


import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.internal.statistics.DefaultStatisticsService;
import org.ehcache.core.spi.service.StatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EhCacheConfig {

    @Bean
    public StatisticsService statisticsService() {
        return new DefaultStatisticsService();
    }


    @Bean
    public CacheManager cacheManager() {
        final CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .using(statisticsService())
                .build();
        cacheManager.init();

        // 创建一个缓存
        Cache<String, String> myCache = cacheManager.createCache("myCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        String.class, String.class,
                        ResourcePoolsBuilder.heap(100) // 堆中最多存储100个条目
                ));
        return cacheManager;
    }
}
