package org.m3f5.web.ehcache;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.core.spi.service.StatisticsService;
import org.ehcache.core.statistics.CacheStatistics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@Slf4j
public class TestController {

    private final CacheManager cacheManager;
    private final StatisticsService statisticsService;


    @GetMapping("/get-value")
    public Object getValueOfCache(String name) {

        var cacheName = "myCache";

        final Cache<String, String> myCache =
                cacheManager.getCache("myCache", String.class, String.class);

        myCache.put(name, name);

        final CacheStatistics statistics = statisticsService.getCacheStatistics(cacheName);
        final long cacheHits = statistics.getCacheHits();

        // Number of entries currently in this tier
        final long nums = statistics.getTierStatistics().get("OnHeap").getMappings();


        log.info("Cache hits: {}|entry nums in OnHeap:{}", cacheHits, nums);
        return myCache.get(name);
    }
}
