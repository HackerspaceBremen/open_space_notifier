package de.hackerspacebremen.data.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;

import de.hackerspacebremen.data.api.BasicDAO;
import de.hackerspacebremen.data.entities.BasicEntity;

public abstract class AbstractObjectifyDB<T extends BasicEntity> implements BasicDAO<T>{

	protected abstract Class<T> getAccessedEntity();
	
	@Override
	public void delete(final T entity) {
		ofy().delete().entity(entity);
	}

	@Override
	public T persist(final T entity) {
		final Key<T> keyStatus = ofy().save().entity(entity).now();
		return ofy().load().key(keyStatus).now();
	}

	@Override
	public T findById(Long id) {
		return ofy().load().type(getAccessedEntity()).id(id).now();
	}

	@Override
	public List<T> findAll() {
		return ofy().load().type(getAccessedEntity()).list();
	}
}