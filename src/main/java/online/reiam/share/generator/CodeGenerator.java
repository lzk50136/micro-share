package online.reiam.share.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class CodeGenerator {

    private static String[] tables = {"at_me"};

    public static void main(String[] args) {
        // 1. 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc
                // 生成文件的输出目录
                .setOutputDir(System.getProperty("user.dir") + "/src/main/java")
                // 是否覆盖已有文件
                .setFileOverride(true)
                // 是否打开输出目录
                .setOpen(false)
                // 是否在xml中添加二级缓存配置
                //.setEnableCache(false)
                // 开发人员
                .setAuthor("Lzk")
                // 开启 Kotlin 模式
                //.setKotlin(false)
                // 开启 swagger2 模式
                //.setSwagger2(false)
                // 开启 ActiveRecord 模式
                //.setActiveRecord(false)
                // 开启 BaseResultMap
                //.setBaseResultMap(false)
                // 时间类型对应策略
                //.setDateType(DateType.TIME_PACK)
                // 开启 BaseColumnList
                //.setBaseColumnList(false)
                // 各层文件名称方式
                // 默认值：null 例如：%sEntity 生成 UserEntity
                //.setEntityName("%sEntity")
                // 默认值：null 例如：%sDao 生成 UserDao
                //.setMapperName("%sDao")
                // 默认值：null 例如：%sDao 生成 UserDao.xml
                //.setXmlName("%sDao")
                // 设置生成的service接口的名字的首字母是否为I(默认Service类前面有一个I)
                .setServiceName("%sService")
                // 默认值：null 例如：%sBusinessImpl 生成 UserBusinessImpl
                //.setServiceImplName("%sBusinessImpl")
                // 默认值：null 例如：%sAction 生成 UserAction
                //.setControllerName("%sAction")
                // 指定生成的主键的ID类型
                .setIdType(IdType.AUTO)
        ;

        // 2. 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc
                // 数据库信息查询
                .setDbQuery(new MySqlQuery())
                // 数据库类型
                .setDbType(DbType.MYSQL)
                // 类型转换
                .setTypeConvert(new MySqlTypeConvert())
                // 驱动连接的URL
                .setUrl("jdbc:mysql://localhost:3306/micro_share?useUnicode=true&useSSL=false&useAffectedRows=true")
                // 驱动名称
                .setDriverName("com.mysql.jdbc.Driver")
                // 数据库连接用户名
                .setUsername("root")
                // 数据库连接密码
                .setPassword("123456")
        ;

        // 3. 包名策略配置
        PackageConfig pc = new PackageConfig();
        pc
                // 设置父包模块名
                //.setModuleName(null)
                // Entity包名
                //.setEntity("entity")
                // Service包名
                //.setService("service")
                // Service Impl包名
                //.setServiceImpl("service.impl")
                // Mapper包名
                //.setMapper("mapper")
                // Mapper XML包名
                //.setXml("mapper")
                // 路径配置信息
                //.setPathInfo(null)
                // Controller包名
                //.setController("controller")
                // 设置父包名
                .setParent("online.reiam.share")
        ;

        // 4. 策略配置项
        StrategyConfig sc = new StrategyConfig();
        sc
                // 数据库表映射到实体的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                // 表字段映射到实体的命名策略,未指定按照 naming 执行
                //.setColumnNaming(null)
                // 是否大写命名
                //.setCapitalMode(false)
                // 是否跳过视图
                //.setSkipView(false)
                // 表前缀
                //.setTablePrefix()
                // 字段前缀
                //.setFieldPrefix()
                // 自定义继承的Entity类全称，带包名
                //.setSuperEntityClass("online.reiam.share.base.BaseEntity")
                // 自定义基础的Entity类，公共字段
                //.setSuperEntityColumns("createTime", "modifiedTime")
                // 自定义继承的Mapper类全称，带包名
                //.setSuperMapperClass("online.reiam.share.mapper.BaseMapper")
                // 自定义继承的Service类全称，带包名
                // .setSuperServiceClass("online.reiam.share.TestService")
                // 自定义继承的ServiceImpl类全称，带包名
                // .setSuperServiceImplClass("online.reiam.share.TestServiceImpl")
                // 自定义继承的Controller类全称，带包名
                //.setSuperControllerClass("online.reiam.share.controller.AbstractController")
                // 需要包含的表名，允许正则表达式（与exclude二选一配置）
                .setInclude(tables)
                // 需要排除的表名，允许正则表达式
                //.setExclude("")
                // 实体是否生成字段常量（默认 false）
                //.setEntityColumnConstant(false)
                // 实体是否为构建者模型（默认 false）
                // .setEntityBuilderModel(false)
                // 实体是否为lombok模型（默认 false）
                .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀（默认 false）
                //.setEntityBooleanColumnRemoveIsPrefix(false)
                // 是否生成实体时，生成字段注解
                //.entityTableFieldAnnotationEnable(false)
                // 乐观锁属性名称
                .setVersionFieldName("version")
                // 设置逻辑删除字段
                .setLogicDeleteFieldName("deleted")
                // 表填充字段
                //.setTableFillList(null)
                // 生成RestController控制器
                .setRestControllerStyle(true)
                // 驼峰转连字符
                .setControllerMappingHyphenStyle(true)
        ;

        // 自定义配置
        /*InjectionConfig ic = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义文件输出位置（非必须）
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return System.getProperty("user.dir") + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        ic.setFileOutConfigList(focList);*/

        // 5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag
                // 查看源码可知无需设置此项
                //.setConfig()
                // 注入自定义配置
                //.setCfg(ic)
                // 数据源配置
                .setDataSource(dsc)
                // 数据库表配置
                .setStrategy(sc)
                // 包 相关配置
                .setPackageInfo(pc)
                //模板 相关配置
                //.setTemplate(new TemplateConfig().setController(null))
                .setTemplate(new TemplateConfig().setController(null).setService(null).setServiceImpl(null).setMapper(null).setXml(null))
                // 全局 相关配置
                .setGlobalConfig(gc)
                // Spring Boot1.5后不支持Velocity，手动设置为Freemarker
                .setTemplateEngine(new FreemarkerTemplateEngine())
        ;

        // 6. 生成文件
        ag.execute();
    }

}
