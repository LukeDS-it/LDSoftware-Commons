package it.ldsoftware.primavera.i18n;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * Created by luca on 19/04/16.
 * This MessageSource will load all resource files under specified paths
 * created with wildcard expressions.
 */
public class WildcardMessageSource extends ReloadableResourceBundleMessageSource {
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    @Override
    public void setBasenames(String... names) {
        if (names != null) {
            List<String> baseNames = new ArrayList<>();
            for (String name : names) {
                String basename = trimToEmpty(name);
                if (isNotBlank(basename)) {
                    try {
                        Resource[] resources = resourcePatternResolver.getResources(basename);
                        for (Resource resource : resources) {
                            String uri = resource.getURI().toString();
                            String baseName = null;
                            if (resource instanceof FileSystemResource) {
                                baseName = "classpath:" + substringBetween(uri, "/classes/", ".properties");
                            } else if (resource instanceof ClassPathResource) {
                                baseName = substringBefore(uri, ".properties");
                            } else if (resource instanceof UrlResource) {
                                baseName = "classpath:" + substringBetween(uri, ".jar!/", ".properties");
                            }
                            if (baseName != null) {
                                String fullName = processBasename(baseName);
                                baseNames.add(fullName);
                            }
                        }
                    } catch (IOException e) {
                        logger.error("No message source files found for basename " + basename + ".");
                    }
                }
                String[] resourceBasenames = baseNames.toArray(new String[baseNames.size()]);
                super.setBasenames(resourceBasenames);
            }
        }
    }

    private String processBasename(String baseName) {
        String prefix = substringBeforeLast(baseName, "/");
        String name = substringAfterLast(baseName, "/");
        do {
            name = substringBeforeLast(name, "_");
        } while (name.contains("_"));
        return prefix + "/" + name;
    }
}
