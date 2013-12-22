package net.trajano.app;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Qualifier.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualified {

}
