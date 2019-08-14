package org.thraex.admin;

import org.springframework.boot.ImageBanner;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * @Author 鬼王
 * @Date 2018/11/22 09:57
 */
@SpringBootApplication
public class AdminApplication {

    /**
     * Way 1:
     * <PRE>
     * SpringApplication.run(AdminApplication.class, args);
     * </PRE>
     *
     * Way 2:
     *
     * <PRE>
     * SpringApplication app = new SpringApplication(AdminApplication.class);
     * app.run(args);
     * </PRE>
     *
     * Way 3:
     *
     * <PRE>
     * SpringApplicationBuilder().sources(AdminApplication.class).run(args);
     *
     * // or
     *
     * // 创建多层次的ApplicationContext
     * new SpringApplicationBuilder()
     *     .sources(AdminParent.class)
     *     .child(AdminApplication.class)
     *     .run(args);
     * </PRE>
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AdminApplication.class);

        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("public/sublime.jpg");
        app.setBanner(new ImageBanner(resource));

        //Resource resource = resourceLoader.getResource("public/app-5.svg");
        //app.setBanner(new ResourceBanner(resource));

        app.run(args);
    }

}
