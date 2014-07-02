// not shown
package com.tngtech.test.localetransformer;

import com.tngtech.configbuilder.ConfigBuilder;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertiesFiles;
import com.tngtech.configbuilder.annotation.typetransformer.TypeTransformer;
import com.tngtech.configbuilder.annotation.typetransformer.TypeTransformers;
import com.tngtech.configbuilder.annotation.valueextractor.SystemPropertyValue;
import com.tngtech.test.common.JSONHelper;

import java.util.Locale;

@PropertiesFiles("config")
// shown
public class Config {
    // not shown
    public static void main(String[] args) throws Exception {
        Config config = new ConfigBuilder<>(Config.class).build();
        JSONHelper.printJSON(config);
    }

    // shown
    @SystemPropertyValue("user.language")
    @TypeTransformers({StringToLocaleTransformer.class})
    private Locale language;

    // ...
    // not shown
    public Locale getLanguage() {
        return language;
    }
    // shown

    public class StringToLocaleTransformer extends TypeTransformer<String, Locale> {
        @Override
        public Locale transform(String localeText) {
            return Locale.forLanguageTag(localeText);
        }
    }
}