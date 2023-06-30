package cn.hnist.sharo.mcinema.core.model;


public class FileInfo {
    private String name;
    private String suffix;
    private String contentType;
    private Long contentLength;

    public String getFullName(){
        return name + "." + suffix;
    }

    public String getFullKeyName(String key){
        return key + "." + suffix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }
}
