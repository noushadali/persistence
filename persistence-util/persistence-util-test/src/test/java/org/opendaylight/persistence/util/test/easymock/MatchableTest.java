/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.test.easymock;

import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.test.ThrowableTester;
import org.opendaylight.persistence.util.test.ThrowableTester.Instruction;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class MatchableTest {

    @Test
    public void testConstruction() {
        Matchable<String> matchedPropertyA = Matchable.valueOf("matchedPropertyA", "value", "value");
        Matchable<String> matchedPropertyB = Matchable.valueOf("matchedPropertyB", null, null);
        Matchable<String> mismatchedPropertyA = Matchable.valueOf("mismatchedPropertyA", "value", "different value");
        Matchable<String> mismatchedPropertyB = Matchable.valueOf("mismatchedPropertyB", null, "different value");
        Matchable<String> mismatchedPropertyC = Matchable.valueOf("mismatchedPropertyB", "value", null);

        Assert.assertTrue(matchedPropertyA.matches());
        Assert.assertTrue(matchedPropertyB.matches());
        Assert.assertFalse(mismatchedPropertyA.matches());
        Assert.assertFalse(mismatchedPropertyB.matches());
        Assert.assertFalse(mismatchedPropertyC.matches());
    }

    @Test
    public void testInvalidConstruction() {
        final String invalidNameNull = null;
        final String invalidNameEmpty = "";

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                Matchable.valueOf(invalidNameNull, null, null);
            }
        });

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                Matchable.valueOf(invalidNameEmpty, null, null);
            }
        });
    }

    @Test
    public void testToString() {
        final String name = "property name";
        final String expected = "property value";
        final String actual = "property different value";
        Matchable<String> matchable = Matchable.valueOf(name, expected, actual);
        Assert.assertFalse(matchable.toString().isEmpty());
    }
}
