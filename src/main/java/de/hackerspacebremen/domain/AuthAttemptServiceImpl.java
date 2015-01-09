package de.hackerspacebremen.domain;

import java.util.logging.Level;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import de.hackerspacebremen.domain.api.AuthAttemptService;

public class AuthAttemptServiceImpl implements AuthAttemptService{

	private MemcacheService cache;
	
	/**{@inheritDoc} **/
	@Override
	public boolean checkAttemptMax(final String userName) {
		boolean result = false;
			if(userName != null){
			if(this.cache==null){
				this.cache = this.createCache();
			}
		    final String key = "attempt_"+userName;
		    final Integer amount = (Integer) this.cache.get(key);
		    
		    
		    if (amount == null) {
		    	this.cache.put(key, Integer.valueOf(1)); 
		    }else if(amount.intValue()>=50){
		    	result = true;
		    }else{
		    	final int newAmount = amount.intValue()+1;
		    	this.cache.put(key, Integer.valueOf(newAmount));
		    }
		}
		return result;
	}

	private MemcacheService createCache(){
		final MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService("auth_attempt");
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));	    
	    return syncCache;
	}

	/**{@inheritDoc} **/
	@Override
	public void clearAttemptAmounts() {
		if(this.cache==null){
			this.cache = this.createCache();
		}
		this.cache.clearAll();
	}
}