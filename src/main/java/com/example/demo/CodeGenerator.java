package com.example.demo;

import com.example.demo.core.constant.ProjectConstant;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**

 * @author  Pre_fantasy

 * @create  2018-06-06 22:50

 * @desc    代码生成器，根据数据表名称生成对应的Model、Mapper简化开发。

 **/
public class CodeGenerator {
    /*JDBC链接信息*/
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    /*java文件路径*/
    private static final String JAVA_PATH = "src/main/java";
    /*资源文件路径*/
    private static final String RESOURCES_PATH = "src/main/resources";

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/6 22:53
     *  @param  {String[] args}
     *  @return void
     *  @desc   java执行的入口
     */
    public static void main(String[] args) {
        genCode("system_log");
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/6 22:54
     *  @param  {String... tableNames}
     *  @return 
     *  @desc    通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。 如输入表名称 "t_user_detail" 将生成TUserDetail
     */
    public static void genCode(String... tableNames) {
        for (String tableName : tableNames) {
            /*逐个执行生成model，这里是依据表名称*/
            genCode(tableName, null);
        }
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/6 22:57
     *  @param  {String tableName, String modelName}
     *  @return void
     *  @desc   过数据表名称，和自定义的 Model 名称生成代码 如输入表名称 "t_user_detail" 和自定义的 Model 名称 "User"
     */
    public static void genCode(String tableName, String modelName) {
        genModelAndMapper(tableName, modelName);
    }

    public static void genModelAndMapper(String tableName, String modelName) {

        Context context = getContext();

        JDBCConnectionConfiguration jdbcConnectionConfiguration = getJDBCConnectionConfiguration();
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        PluginConfiguration pluginConfiguration = getPluginConfiguration();
        context.addPluginConfiguration(pluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = getJavaModelGeneratorConfiguration();
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = getSqlMapGeneratorConfiguration();
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration =getJavaClientGeneratorConfiguration();
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        tableConfiguration.setDomainObjectName(modelName);
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;

        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();
            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }


        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        if (StringUtils.isEmpty(modelName)){
            modelName = tableNameConvertUpperCamel(tableName);
        }
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");


    }

    private static Context getContext(){
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
        return context;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/7 0:02
     *  @param  {void}
     *  @return JDBCConnectionConfiguration
     *  @desc   获取jdbc链接对象
     */
    private static JDBCConnectionConfiguration getJDBCConnectionConfiguration(){
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        return jdbcConnectionConfiguration;
    }

    private static PluginConfiguration getPluginConfiguration(){
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", ProjectConstant.MAPPER_INTERFACE_REFERENCE);
        return pluginConfiguration;
    }

    private static JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration(){
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(ProjectConstant.MODEL_PACKAGE);
        javaModelGeneratorConfiguration.addProperty("enableSubPackages","true");
        javaModelGeneratorConfiguration.addProperty("trimStrings","true");
        return javaModelGeneratorConfiguration;
    }

    private static SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration(){
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        return sqlMapGeneratorConfiguration;
    }

    private static JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration(){
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(ProjectConstant.MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        return javaClientGeneratorConfiguration;
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    }


}
