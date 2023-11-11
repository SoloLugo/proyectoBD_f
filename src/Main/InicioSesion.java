package Main;
public class InicioSesion {
    
    private String url;
    private String driver;
    private String user;
    private String password;
    
    public InicioSesion(String URLS, String DRIVERs, String USERs, String PASSWORDs) {
        this.url = URLS;
        this.driver = DRIVERs;
        this.user = USERs;
        this.password = PASSWORDs;
    }

    public InicioSesion() {
        this.url = "";
        this.driver = "";
        this.user = "";
        this.password = "";
    }

    public String getUrl() {
        return url;
    }

    public String getDriver() {
        return driver;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    @Override
    public String toString() {
    return url+","+driver+","+user+","+password;
    }
}
