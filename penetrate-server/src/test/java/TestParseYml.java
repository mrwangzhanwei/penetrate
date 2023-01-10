import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.scanner.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TestParseYml {
    public static void main(String[] args) throws FileNotFoundException {
        Yaml yaml = new Yaml();
//        InputStream resourceAsStream = Constant.class.getClassLoader().getResourceAsStream("server.yml");
        InputStream resourceAsStream = new FileInputStream(".\\penetrate-server\\src\\main\\resources\\server.yml");
        Map obj = yaml.load(resourceAsStream);
        Map<String,Object> param = new HashMap<>();
        //需要注意，此类加载器只能得到它的最顶层的key的值
        Map<String,Object> params= (Map) obj.get("redis");
        param.putAll(params);
        System.out.println("params = " + params );
        Map<String,Object> params2= (Map) obj.get("server");
        param.putAll(params2);
        System.out.println("params2 = " + params2 );
        System.out.println("param = " + param );
    }
}
