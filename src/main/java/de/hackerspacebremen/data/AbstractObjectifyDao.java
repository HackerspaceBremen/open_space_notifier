package de.hackerspacebremen.data;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;

import de.hackerspacebremen.data.entity.BasicEntity;

public abstract class AbstractObjectifyDao<T extends BasicEntity> {

	protected abstract Class<T> getAccessedEntity();
	
	public void delete(final T entity) {
		ofy().delete().entity(entity);
	}

	public T persist(final T entity) {
		final Key<T> keyStatus = ofy().save().entity(entity).now();
		return ofy().load().key(keyStatus).now();
	}

	public T findById(Long id) {
		return ofy().load().type(getAccessedEntity()).id(id).now();
	}

	public List<T> findAll() {
		return ofy().load().type(getAccessedEntity()).list();
	}
}