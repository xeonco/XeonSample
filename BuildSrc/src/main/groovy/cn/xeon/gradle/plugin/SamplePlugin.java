package cn.xeon.gradle.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * on 2017/11/15.
 *
 *
 * @author LinZaixiong
 */

public class SamplePlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {

		System.out.println("========================");
		System.out.println(" this is a sample for gradle plugin ");
		System.out.println("========================");

	}
}
