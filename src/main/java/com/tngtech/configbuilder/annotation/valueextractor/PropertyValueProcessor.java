package com.tngtech.configbuilder.annotation.valueextractor;

import com.tngtech.configbuilder.configuration.BuilderConfiguration;

import java.lang.annotation.Annotation;

/**
 * Processes PropertyValue annotations, implements IValueExtractorProcessor
 */
public class PropertyValueProcessor implements IValueExtractorProcessor {

    public String getValue(Annotation annotation, BuilderConfiguration builderConfiguration) {
        return builderConfiguration.getProperties().getProperty(((PropertyValue) annotation).value());
    }
}
