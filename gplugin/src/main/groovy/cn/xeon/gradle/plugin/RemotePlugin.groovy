package cn.xeon.gradle.plugin;
import org.gradle.api.Plugin
import org.gradle.api.Project

public class RemotePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        System.out.println("========================");
        System.out.println(" hello gradle plugin! ");
        System.out.println("========================");

    }
}