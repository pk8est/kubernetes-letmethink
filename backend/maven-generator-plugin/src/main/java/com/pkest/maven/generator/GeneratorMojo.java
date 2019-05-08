package com.pkest.maven.generator;

import com.pkest.maven.generator.bean.Property;
import com.pkest.maven.generator.bean.Var;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Mojo( name = "generator")
public class GeneratorMojo extends AbstractMojo{

	@Parameter( property = "generator.model")
	private String modelName;

	@Parameter( property = "generator.name")
	private String name;

	@Parameter( property = "generator.table")
	private String table;

	@Parameter( property = "generator.url")
	private String url;

	@Parameter( property = "generator.username")
	private String username;

	@Parameter( property = "generator.password")
	private String password;

	private String tplRoot = System.getProperty("user.dir") + "/../maven-generator-plugin/src/main/java/com/pkest/maven/generator/template";

	private String modelRoot = System.getProperty("user.dir") + "/../core-repository/src/main/java/com/pkest/repo/model";
	private String mapperRoot = System.getProperty("user.dir") + "/../core-repository/src/main/java/com/pkest/repo/mapper";

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info( "Start...");

		try {
			if(StringUtils.isBlank(modelName)){
				modelName = Utils.initcap(Utils.toCamel(table));
			}
			if(StringUtils.isBlank(name)){
				name = Utils.initcap(Utils.toCamel(table));
			}
			FileResourceLoader resourceLoader = new FileResourceLoader(tplRoot,"utf-8");
			Configuration cfg = Configuration.defaultConfiguration();
			GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
			generateCode(gt);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void generateCode(GroupTemplate gt) throws Exception{

		ModelPropertyHandler generatorModel = new ModelPropertyHandler(url, username, password);
		List<Property> properties = generatorModel.getProperties(table);

		Var var = new Var();
		var.setName(name);
		var.setModelName(modelName);
		var.setTable(table);

		generateMapper(gt, var, properties);
		generateModel(gt, var, properties);

	}

	private void generateMapper(GroupTemplate gt, Var var, List<Property> properties) throws Exception{
		Template modelTemplate = gt.getTemplate("mapper.btl");

		modelTemplate.binding("data", var);
		modelTemplate.renderTo(new FileOutputStream(new File(mapperRoot + "/" + var.getModelName() + "Mapper.java" )));
	}

	private void generateModel(GroupTemplate gt, Var var, List<Property> properties) throws Exception{

		Template modelTemplate = gt.getTemplate("model.btl");

		modelTemplate.binding("data", var);
		modelTemplate.binding("properties", properties);

		String modelPath = modelRoot + "/" + var.getModelName() + "Model.java";
		File modelFile = new File(modelPath);
		File entityDir = modelFile.getParentFile();
		if (!entityDir.exists()) {
			entityDir.mkdirs();
		}
		if(!modelFile.exists()){
			getLog().info(modelTemplate.render());
			modelFile.createNewFile();
			modelTemplate.renderTo(new FileOutputStream(modelFile));
		}else{
			getLog().warn("Model " + var.getModelName() + " is exist!");
		}


	}
}
