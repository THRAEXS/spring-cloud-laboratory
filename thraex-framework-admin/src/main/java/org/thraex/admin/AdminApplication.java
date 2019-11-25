package org.thraex.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
     *
     * Banner:
     *
     * banner.txt优先级高于以下方式
     *
     * <PRE>
     * ResourceLoader resourceLoader = new DefaultResourceLoader();
     * Resource resource = resourceLoader.getResource("public/sublime.jpg");
     * app.setBanner(new ImageBanner(resource));
     *
     * // or
     *
     * Resource resource = resourceLoader.getResource("public/app-5.svg");
     * app.setBanner(new ResourceBanner(resource));
     * </PRE>
     */
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
