/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.infinispan.hibernate.cache.collection;

import org.hibernate.cache.CacheException;
import org.infinispan.hibernate.cache.InfinispanRegionFactory;
import org.infinispan.hibernate.cache.access.AccessDelegate;
import org.infinispan.hibernate.cache.impl.BaseTransactionalDataRegion;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.CacheKeysFactory;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.infinispan.AdvancedCache;

import javax.transaction.TransactionManager;

/**
 * Collection region implementation
 *
 * @author Chris Bredesen
 * @author Galder Zamarreño
 * @since 3.5
 */
public class CollectionRegionImpl extends BaseTransactionalDataRegion implements CollectionRegion {
	/**
	 * Construct a collection region
	 *  @param cache instance to store collection instances
	 * @param name of collection type
	 * @param transactionManager
	 * @param metadata for the collection type
	 * @param factory for the region
	 * @param cacheKeysFactory factory for cache keys
	 */
	public CollectionRegionImpl(
			AdvancedCache cache, String name, TransactionManager transactionManager,
			CacheDataDescription metadata, InfinispanRegionFactory factory, CacheKeysFactory cacheKeysFactory) {
		super( cache, name, transactionManager, metadata, factory, cacheKeysFactory );
	}

	@Override
	public CollectionRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		checkAccessType( accessType );
		AccessDelegate accessDelegate = createAccessDelegate(accessType);
		return new CollectionAccess( this, accessDelegate );
	}
}
