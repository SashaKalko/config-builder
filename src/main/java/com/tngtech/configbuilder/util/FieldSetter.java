package com.tngtech.configbuilder.util;

import com.tngtech.configbuilder.annotation.valueextractor.ValueExtractorAnnotation;
import com.tngtech.configbuilder.configuration.BuilderConfiguration;
import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.exception.ConfigBuilderException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class FieldSetter<T> {

    private final static Logger log = Logger.getLogger(FieldSetter.class);

    private final FieldValueExtractor fieldValueExtractor;
    private final ErrorMessageSetup errorMessageSetup;
    private final AnnotationHelper annotationHelper;

    @Autowired
    public FieldSetter(FieldValueExtractor fieldValueExtractor, ErrorMessageSetup errorMessageSetup, AnnotationHelper annotationHelper) {
        this.fieldValueExtractor = fieldValueExtractor;
        this.errorMessageSetup = errorMessageSetup;
        this.annotationHelper = annotationHelper;
    }

    public void setFields(T instanceOfConfigClass, BuilderConfiguration builderConfiguration) {

        for (Field field : instanceOfConfigClass.getClass().getDeclaredFields()) {
            if(!annotationHelper.fieldHasAnnotationAnnotatedWith(field, ValueExtractorAnnotation.class)) {
                log.debug(String.format("field %s is not annotated with any ValueExtractorAnnotation", field.getName()));
                continue;
            }
            Object value = fieldValueExtractor.extractValue(field, builderConfiguration);
            setField(instanceOfConfigClass, field, value);
        }
    }

    private void setField(T instanceOfConfigClass, Field field, Object value) {
        try{
            field.setAccessible(true);
            field.set(instanceOfConfigClass, value);
            log.info(String.format("set field %s of type %s to a value of type %s", field.getName(), field.getType().getName(), value == null ? "null" : value.getClass().getName()));
        } catch(Exception e){
            throw new ConfigBuilderException(errorMessageSetup.getErrorMessage(e, field.getName(), field.getType().getName(), value == null ? "null" : value.toString()), e);
        }
    }
}