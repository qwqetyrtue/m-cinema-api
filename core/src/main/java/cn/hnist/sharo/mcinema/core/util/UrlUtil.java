package cn.hnist.sharo.mcinema.core.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h3>url 处理工具</h3>
 */
public class UrlUtil {
    public static Url getUrl(String url) {
        return new Url(url, "/");
    }

    public static Url getUrl(String url, String split) {
        return new Url(url, split);
    }

    public static class Url {
        private final LinkedList<String> value;
        private final String split;

        private Url(String url, String split) {
            this.split = split;
            this.value = new LinkedList<>();
            this.resolve(url);
        }

        public Url resolve(String path) {
            String[] urls = path.split(this.split);
            List<String> list = Arrays.stream(urls)
                    .filter(this::checkEmpty)
                    .collect(Collectors.toList());
            this.value.addAll(list);
            return this;
        }

        public List<String> toList() {
            return this.value;
        }

        public String toUrl() {
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < this.value.size(); i++) {
                res.append(value.get(i));
                if (i != this.value.size() - 1)
                    res.append(this.split);
            }
            return res.toString();
        }


        private boolean checkEmpty(String target) {
            return target != null && target.trim().length() != 0;
        }
    }
}
