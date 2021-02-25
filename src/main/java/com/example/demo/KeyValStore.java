package com.example.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class KeyValStore {
    final static File dbFile = new File(System.getenv("TEMP")+ File.separator + "key_val_store.db");
    private Map<String, String> keyValMap = new HashMap<>();
    private boolean append = false;

    @Override
    public String toString() {
        return "KeyValStore{" +
                "keyValMap=" + keyValMap +
                '}';
    }

    public KeyValStore() {
        if(dbFile.exists()){
            init();
        }else{
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        try{

            for(String line :Files.readAllLines(dbFile.toPath())){
                String key=line.split("=")[0].strip();
                String value=line.split("=")[1].strip();

                this.add(key,value);
            }
        }catch (IOException ioe){
            System.out.println("Some problem reading " + dbFile.getPath() + " Error " + ioe.getCause());
            System.out.println("Key Values will be empty for this time");
            append = true;
        }

    }

    public String add(String key, String value) {
        System.out.println("adding " + key + " and " + value) ;
        return keyValMap.put(key,value);
    }
    public String get(String key) {
        return keyValMap.get(key);
    }
    public boolean delete(String key) {
        if (keyValMap.remove(key) == null){
            return false;
        }
        return true;
    }

    public void close() {
        //Clear file content
        Charset charset = Charset.forName("UTF8");
        try (BufferedWriter writer = Files.newBufferedWriter(dbFile.toPath(), charset)) {
            if(!append){
                writer.write("");
            }
            for(String key : keyValMap.keySet()){
                writer.append(key + "="+ keyValMap.get(key) + "\n");
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
