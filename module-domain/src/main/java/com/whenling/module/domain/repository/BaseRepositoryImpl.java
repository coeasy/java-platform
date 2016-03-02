package com.whenling.module.domain.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.whenling.module.domain.exception.DeleteLockedEntityException;
import com.whenling.module.domain.model.Lockedable;

/**
 * 基础仓库实现类
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:03:10
 * @param <T>
 * @param <I>
 */
public class BaseRepositoryImpl<T, I extends Serializable> extends SimpleJpaRepository<T, I>
		implements BaseRepository<T, I> {

	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
	}

	@Override
	public void delete(T entity) {
		if (entity instanceof Lockedable) {
			if (((Lockedable) entity).getLocked()) {
				throw new DeleteLockedEntityException();
			}
		}

		super.delete(entity);
	}

}