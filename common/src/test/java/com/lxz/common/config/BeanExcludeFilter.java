package com.lxz.common.config;

import com.google.common.collect.Sets;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.util.Set;

/**
 * @ClassName BeanExcludeFilter
 * @Description 过滤指定bean，集成测试时不注入容器
 * @Author 86184
 * @Date 2025/7/8 09:27
 */
public class BeanExcludeFilter extends TypeExcludeFilter {
    private static Set<String> excludes = Sets.newHashSet("com.aoi.core.service.ExternalService");

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        String className = metadataReader.getClassMetadata().getClassName();
        if (excludes.contains(className)) {
            return true;
        }
        return super.match(metadataReader, metadataReaderFactory);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
