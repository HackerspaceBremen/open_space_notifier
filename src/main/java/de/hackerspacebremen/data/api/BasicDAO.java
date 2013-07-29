package de.hackerspacebremen.data.api;

import java.util.List;

import de.hackerspacebremen.data.entities.BasicEntity;

public interface BasicDAO<T extends BasicEntity> {

	void delete(T entity);

	T persist(T entity);

	T findById(Long id);

	List<T> findAll();
}
