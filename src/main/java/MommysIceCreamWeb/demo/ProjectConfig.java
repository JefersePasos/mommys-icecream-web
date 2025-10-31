
package MommysIceCreamWeb.demo;

public class ProjectConfig implements WebMvcConfigurer {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("inicio");
    }
}
