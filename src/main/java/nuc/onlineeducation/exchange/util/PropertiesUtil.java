package nuc.onlineeducation.exchange.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Ji YongGuang.
 * @date 19:14 2018/1/9.
 * 读取配置文件的工具类，如果要读取多properties文件可参考Alipay的Configs类进行优化
 */
@Log4j2
public class PropertiesUtil {

    private static Properties props;

    // 静态代码块加载配置文件
    static {
        String fileName = "exchange.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),
                    "UTF-8"));
        } catch (IOException e) {
            log.error("配置文件读取异常", e);
        }
    }

    public static String getProperty(String key) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            // 避免无异议的返回值，k-v都要做trim处理
            return null;
        }
        return value.trim();
    }

    /**
     * 扩展方法
     * @param key K
     * @param defaultValue default - V
     * @return
     */
    public static String getProperty(String key, String defaultValue) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            // 如果配置文件中忘记配置这个key，代码中也有默认值来兜底
            value = defaultValue;
        }
        return value.trim();
    }

}
