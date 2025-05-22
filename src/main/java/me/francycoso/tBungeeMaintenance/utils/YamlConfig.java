package me.francycoso.tBungeeMaintenance.utils;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class YamlConfig {

    private final File file;
    private Configuration config;

    public YamlConfig(File file) {
        this.file = file;
        try {
            if (!file.exists()) file.createNewFile();
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);
    }

    public void setList(String path, String... values) {
        config.set(path, Arrays.asList(values));
    }

    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
