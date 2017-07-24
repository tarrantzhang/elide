/*
 * Copyright 2017, Oath Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */
package com.yahoo.elide.core.exceptions;
import com.yahoo.elide.core.HttpStatus;

/**
 * Invalid predicate negation error.
 *
 * {@link com.yahoo.elide.core.HttpStatus#SC_FORBIDDEN invalid}
 */
public class InvalidOperatorNegationException extends HttpStatusException {
    public InvalidOperatorNegationException() {
        super(HttpStatus.SC_FORBIDDEN, "Invalid Usage of NOT in FilterExpression.");
    }
}
