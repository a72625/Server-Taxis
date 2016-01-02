/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @author rcamposinhos atencao aos locks de leitura e escrita
 */
public class BD extends HashMap<String, User> implements Serializable {

    private String bdFilepath;
    private ReentrantLock l;

    public BD() {
        super();
        this.bdFilepath = "bd";
        this.l = new ReentrantLock();
    }

    public Boolean login(String username, String password) {
        boolean flag = false;
        l.lock();
        User u = this.get(username);
        if (u.getUser().equals(username) && u.getPass().equals(password)) {
            flag = true;
        }
        l.unlock();
        return flag;
    }

    public Boolean registar(String username, String password) throws IOException {
        boolean flag = true;
        l.lock();
        this.put(username, new User(username, password));
        if (!this.containsKey(username)) {
            flag = false;
        } else {
            //persistencia de dados
            this.save(this.bdFilepath);
        }
        l.unlock();
        return flag;
    }

    public String getBdFilepath() {
        return bdFilepath;
    }

    public void setBdFilepath(String bdFilepath) {
        this.bdFilepath = bdFilepath;
    }

    public void save(String fileName) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileName));
        out.writeObject(this);
        out.flush();
        out.close();
    }

    public void loadSample() throws IOException {
        User u1 = new User("rui", "123");
        this.put("rui", u1);
        User u2 = new User("miguel", "123");
        this.put("miguel", u2);
        User u3 = new User("diogo", "123");
        this.put("diogo", u3);
        this.save(this.bdFilepath);
    }
    


    @Override
    public Object clone() {
        l.lock();
        Object o = super.clone();
        l.unlock();
        return o; 
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super User, ? extends User> function) {
        l.lock();
        super.replaceAll(function); 
        l.unlock();
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super User> action) {
        l.lock();
        super.forEach(action); 
        l.unlock();
    }

    @Override
    public User merge(String key, User value, BiFunction<? super User, ? super User, ? extends User> remappingFunction) {
        l.lock();
        User u = super.merge(key, value, remappingFunction); 
        l.unlock();
        return u;
    }

    @Override
    public User compute(String key, BiFunction<? super String, ? super User, ? extends User> remappingFunction) {
        l.lock();
        User u = super.compute(key, remappingFunction); 
        l.unlock();
        return u;
    }

    @Override
    public User computeIfPresent(String key, BiFunction<? super String, ? super User, ? extends User> remappingFunction) {
        l.lock();
        User u = super.computeIfPresent(key, remappingFunction); 
        l.unlock();
        return u;
    }

    @Override
    public User computeIfAbsent(String key, Function<? super String, ? extends User> mappingFunction) {
        l.lock();
        User u = super.computeIfAbsent(key, mappingFunction); 
        l.unlock();
        return u;
    }

    @Override
    public User replace(String key, User value) {
        l.lock();
        User u = super.replace(key, value); 
        l.unlock();
        return u;
    }

    @Override
    public boolean replace(String key, User oldValue, User newValue) {
        l.lock();
        boolean b = super.replace(key, oldValue, newValue); 
        l.unlock();
        return b;
    }

    @Override
    public boolean remove(Object key, Object value) {
        l.lock();
        boolean b = super.remove(key, value); 
        l.unlock();
        return b;
    }

    @Override
    public User putIfAbsent(String key, User value) {
        l.lock();
        User u = super.putIfAbsent(key, value); 
        l.unlock();
        return u;
    }

    @Override
    public User getOrDefault(Object key, User defaultValue) {
        l.lock();
        User u = super.getOrDefault(key, defaultValue); 
        l.unlock();
        return u;
    }

    @Override
    public Set<Entry<String, User>> entrySet() {
        l.lock();
        Set<Entry<String, User>> s = super.entrySet(); 
        l.unlock();
        return s;
    }

    @Override
    public Collection<User> values() {
        l.lock();
        Collection<User> col = super.values(); 
        l.unlock();
        return col;
    }

    @Override
    public Set<String> keySet() {
        l.lock();
        Set<String> s = super.keySet(); 
        l.unlock();
        return s;
    }

    @Override
    public boolean containsValue(Object value) {
        l.lock();
        boolean b = super.containsValue(value); 
        l.unlock();
        return b;
    }

    @Override
    public void clear() {
        l.lock();
        super.clear(); 
        l.unlock();
    }

    @Override
    public User remove(Object key) {
        l.lock();
        User u = super.remove(key);
        l.unlock();
        return u;
    }

    @Override
    public void putAll(Map<? extends String, ? extends User> m) {
        l.lock();
        super.putAll(m); 
        l.unlock();
    }

    @Override
    public User put(String key, User value) {
        l.lock();
        User u = super.put(key, value); 
        l.unlock();
        return u;
    }

    @Override
    public boolean containsKey(Object key) {
        l.lock();
        boolean b = super.containsKey(key); 
        l.unlock();
        return b;
    }

    @Override
    public User get(Object key) {
        l.lock();
        User u = super.get(key); 
        l.unlock();
        return u;
    }

    @Override
    public boolean isEmpty() {
        l.lock();
        boolean b = super.isEmpty(); 
        l.unlock();
        return b;
    }

    @Override
    public int size() {
        l.lock();
        int n = super.size(); 
        l.unlock();
        return n;
    }
    
    
}
