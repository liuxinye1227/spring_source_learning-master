package com.spring_mvc.service;

import java.util.List;

/**
 * @author pengYongQiang
 * @date 2021/2/7 13:42
 */
public interface IService<T>  {

	boolean save(T entity);
	List<T> list();
}
