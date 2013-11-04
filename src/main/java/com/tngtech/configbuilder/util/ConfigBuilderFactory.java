package com.tngtech.configbuilder.util;

import com.google.common.collect.Maps;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertiesFilesProcessor;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyExtensionProcessor;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLocationsProcessor;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertySuffixProcessor;
import com.tngtech.configbuilder.annotation.valueextractor.*;
import com.tngtech.configbuilder.annotation.valuetransformer.ValueTransformerProcessor;
import com.tngtech.configbuilder.configuration.BuilderConfiguration;
import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.util.*;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Map;

public class ConfigBuilderFactory {

    private Map<Class,Object> singletonMap = Maps.newHashMap();

    public <T> void initialize() {
        ErrorMessageSetup errorMessageSetup = new ErrorMessageSetup();

        //configuration
        singletonMap.put(BuilderConfiguration.class, new BuilderConfiguration());
        singletonMap.put(ErrorMessageSetup.class, errorMessageSetup);

        //util
        singletonMap.put(AnnotationHelper.class, new AnnotationHelper());
        singletonMap.put(FieldValueExtractor.class, new FieldValueExtractor(this));
        singletonMap.put(PropertyLoaderConfigurator.class, new PropertyLoaderConfigurator(this));
        singletonMap.put(ConstructionHelper.class, new ConstructionHelper<T>(errorMessageSetup));
        singletonMap.put(FieldSetter.class, new FieldSetter<T>(this));
        singletonMap.put(ConfigValidator.class, new ConfigValidator<T>(this));
        singletonMap.put(CommandLineHelper.class, new CommandLineHelper(this));

        //AnnotationProcessors
        singletonMap.put(ValueTransformerProcessor.class, new ValueTransformerProcessor());
        singletonMap.put(SystemPropertyProcessor.class, new SystemPropertyProcessor());
        singletonMap.put(PropertyValueProcessor.class, new PropertyValueProcessor());
        singletonMap.put(EnvironmentVariableProcessor.class, new EnvironmentVariableProcessor());
        singletonMap.put(CommandLineValueProcessor.class, new CommandLineValueProcessor());
        singletonMap.put(PropertySuffixProcessor.class, new PropertySuffixProcessor());
        singletonMap.put(PropertyLocationsProcessor.class, new PropertyLocationsProcessor());
        singletonMap.put(PropertyExtensionProcessor.class, new PropertyExtensionProcessor());
        singletonMap.put(PropertiesFilesProcessor.class, new PropertiesFilesProcessor());
        singletonMap.put(DefaultValueProcessor.class, new DefaultValueProcessor());
    }

    public <K> K getInstance(Class<K> clazz) {
        return (K)singletonMap.get(clazz);
    }

    public <K> K createInstance(Class<K> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }
}
