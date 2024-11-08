package ir.moke.mbp;

import java.util.Properties;

public class Configuration {

    private String host;
    private int port;
    private int timeout;
    private Boolean auth;
    private String username;
    private String password;

    private Configuration() {
    }

    public Configuration(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.auth = builder.auth;
        this.timeout = builder.timeout;
        this.username = builder.username;
        this.password = builder.password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }

    public Boolean getAuth() {
        return auth;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public static class Builder {
        private String host;
        private int port;
        private int timeout = 36000;
        private Boolean auth = true;
        private String username;
        private String password;

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setAuth(Boolean auth) {
            this.auth = auth;
            return this;
        }

        public Builder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Properties buildImap() {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.starttls.enable", "true");
            properties.put("mail.imaps.ssl.trust", "*");
            properties.put("mail.imaps.usesocketchannels", "true");
            properties.put("mail.imaps.host", host);
            properties.put("mail.imaps.port", String.valueOf(port));
            properties.put("mail.imaps.timeout", String.valueOf(timeout));
            properties.put("mail.imaps.auth", String.valueOf(auth));
            properties.put("mail.username", username);
            properties.put("mail.password", password);

            return properties;
        }

        public Properties buildSmtp() {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.ssl.trust", "*");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.username", username);
            properties.put("mail.password", password);
            return properties;
        }
    }
}
