package com.tngtech.configbuilder.annotation.valueextractor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify a property value for the annotated field.<br>
 * <b>Usage:</b> <code>@PropertyValue(value = "propertyKey")</code>
 */
@ValueExtractorAnnotation(PropertyValueProcessor.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyValue {
    String value();
}
