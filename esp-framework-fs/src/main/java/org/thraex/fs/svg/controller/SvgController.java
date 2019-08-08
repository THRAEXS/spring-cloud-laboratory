package org.thraex.fs.svg.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thraex.fs.svg.entity.SvgInfo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author 鬼王
 * @Date 2019/08/07 17:23
 */
@RestController
@RequestMapping("svg")
public class SvgController {

    @GetMapping("list")
    public List<SvgInfo> list() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        final String path = "static/svg/*";

        //Function<InputStream, String> getContent = is -> {
        //    String result = null;
        //    try {
        //        result = IOUtils.toString(is, Charset.defaultCharset());
        //    } catch (IOException e) {
        //        e.printStackTrace();
        //    }
        //
        //    return result;
        //};

        return Arrays.stream(resolver.getResources(path)).map(resource -> {
            String content = null;
            try {
                content = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new SvgInfo(UUID.randomUUID().toString(), resource.getFilename(), content);
        }).sorted(Comparator.comparing(SvgInfo::getName)).collect(Collectors.toList());
    }

}
