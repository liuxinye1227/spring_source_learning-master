package com.spring_cache;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.annotation.ProxyCachingConfiguration;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author pengYongQiang
 * @date 2022/5/30 20:05
 */
public class CacheDemo {


	public static void main(String[] args) {
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(CacheDemoConfig.class);
		CacheTestService bean = app.getBean(CacheTestService.class);
		User user = new User();
		String name = bean.getName(user);
		System.out.println(name);
		String name1 = bean.getName(user);
		System.out.println(name1);

	}

	@Service
	public static class CacheTestService implements InitializingBean {

		@Cacheable(key = "#user.name", cacheNames = "aaa")
		public String getName(User user) {
			System.out.println("执行了业务  ....");
			return "username: " + user.getName();
		}

		@PostConstruct
		public void inti(){
			System.out.println("PostConstruct");
		}
		@Override
		public void afterPropertiesSet() throws Exception {
			System.out.println("afterPropertiesSet");
		}
	}


	@Configuration
	@EnableCaching
	@ComponentScan("com.spring_cache")
	public static class CacheDemoConfig {
		@Bean
		public ProxyCachingConfiguration proxyCachingConfiguration(){
			return new ProxyCachingConfiguration();
		}
		@Bean
		public CacheManager simpleCacheManager(){
			return new ConcurrentMapCacheManager();
		}
	}
}
