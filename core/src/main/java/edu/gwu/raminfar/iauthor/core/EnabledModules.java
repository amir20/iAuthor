package edu.gwu.raminfar.iauthor.core;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author amir.raminfar
 */
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface EnabledModules {
}
