/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type.page;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * Data page.
 * <P>
 * Note: D is not forced to be serializable, so in order to properly serialize a Page the type of
 * data must be serializable. D was not marked as Serializable on purpose so the page can hold non
 * serializable objects.
 *
 * @param <R> type of the page request.
 * @param <D> type of the data
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class Page<R extends PageRequest, D> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final R request;
    private final List<D> data;

    /**
     * Creates a data page.
     *
     * @param pageRequest request that generated this page
     * @param data page's data
     */

    public Page(@Nonnull R pageRequest, @Nonnull List<D> data) {
        this.request = Preconditions.checkNotNull(pageRequest, "pageRequest cannot be null");
        Preconditions.checkNotNull(data, "data cannot be null");
        this.data = ImmutableList.copyOf(data);
    }

    /**
     * Creates a data page
     * @param pageRequest request that generated this page
     * @param data page's data as varargs
     */
    @SafeVarargs
    public Page (R pageRequest, D... data) {
        this(pageRequest, Arrays.asList(data));
    }

    /**
     * Gets the page data.
     *
     * @return the page data
     */
    public List<D> getData() {
        return this.data;
    }

    /**
     * Gets the request that generated this page.
     *
     * @return the request that generated this page
     */
    public R getRequest() {
        return this.request;
    }

    /**
     * Returns {@code true} if the page is empty, {@code false} otherwise.
     *
     * @return {@code true} if the page is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return this.data.size() <= 0;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).
                add("request", this.request).
                add("data", this.data).toString();
    }
}
