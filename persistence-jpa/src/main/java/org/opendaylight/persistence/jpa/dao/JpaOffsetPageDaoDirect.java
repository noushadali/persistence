/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.jpa.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.dao.OffsetPageDao;
import org.opendaylight.persistence.jpa.JpaContext;
import org.opendaylight.persistence.jpa.dao.JpaUtil.PredicateProvider;
import org.opendaylight.persistence.util.common.type.Sort;
import org.opendaylight.persistence.util.common.type.page.OffsetPage;
import org.opendaylight.persistence.util.common.type.page.OffsetPageRequest;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * {@link OffsetPageDao} where the data transfer object pattern is not used and thus the {@link Identifiable} and the
 * entity (the object annotated with {@link javax.persistence.Entity}) are the same object.
 * <p>
 * This class must remain state-less so it is thread safe.
 * <p>
 * A DAO should be used by {@link Query queries}.
 * 
 * @param <I>
 *            type of the identifiable object's id. This type should be immutable and it is critical it implements
 *            {@link Object#equals(Object)} and {@link Object#hashCode()} correctly.
 * @param <T>
 *            type of the identifiable and entity object (object to store in the data store annotated with
 *            {@link javax.persistence.Entity})
 * @param <F>
 *            type of the associated filter. A DAO is responsible for translating this filter to any mechanism
 *            understood by the underlying data store or database technology. For example, predicates in JPA-based
 *            implementations, or WHERE clauses in SQL-base implementations.
 * @param <S>
 *            type of the associated sort attribute or sort key used to construct sort specifications. A DAO is
 *            responsible for translating this specification to any mechanism understood by the underlying data store or
 *            database technology. For example, ORDER BY clauses in SQL-based implementations.
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public abstract class JpaOffsetPageDaoDirect<I extends Serializable, T extends Identifiable<I>, F, S>
        extends JpaDaoDirect<I, T, F, S> implements
        OffsetPageDao<I, T, F, S, JpaContext> {

    /**
     * Construct a DAO.
     * 
     * @param entityClass
     *            class of the object annotated with {@link javax.persistence.Entity}
     */
    protected JpaOffsetPageDaoDirect(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public OffsetPage<T> find(final F filter, List<Sort<S>> sortSpecification,
            OffsetPageRequest pageRequest, JpaContext context)
            throws IndexOutOfBoundsException, PersistenceException {
        PredicateProvider<T> predicateProvider = new PredicateProvider<T>() {
            @Override
            public Predicate getPredicate(CriteriaBuilder criteriaBuilder,
                    Root<T> root) {
                return getQueryPredicate(filter, criteriaBuilder, root);
            }
        };
        return JpaUtil.find(getEntityClass(), predicateProvider,
                convertSortSpecification(sortSpecification), pageRequest,
                context);
    }
}
