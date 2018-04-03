package com.commafeed.backend.dao.newstorage;

import com.commafeed.backend.model.User;
import com.commafeed.backend.model.UserSettings;

import java.util.Objects;

public class UserStorage implements
        IStorageModelDAO<User> {

    private GenericStorage<Long, User> storage;
    private static UserStorage instance;

    private UserStorage(){
        this.storage = new GenericStorage<Long, User>("User");
    }

    public static UserStorage getInstance(){
        if(instance == null){
            instance = new UserStorage();
        }
        return instance;
    }

    public static UserStorage getTestInstance(){
        return new UserStorage();
    }

    @Override
    public boolean exists(User model) {
        return this.storage.exists(model.getId());
    }

    @Override
    public void create(User model) {
        this.storage.create(model.getId(), model);
    }

    @Override
    public User read(User model) {
        return this.storage.read(model.getId());
    }

    @Override
    public User read(Long id) {
        return this.storage.read(id);
    }

    @Override
    public User update(User model) {
        return this.storage.update(model.getId(), model);
    }

    @Override
    public User delete(User model) {
        return this.storage.delete(model.getId(), model);
    }

    @Override
    public void serialize() {
        this.storage.saveStorage();
    }

    @Override
    public void deserialize() {
        this.storage.loadStorage();
    }

    @Override
    public int hashCode() {

        return Objects.hash(storage);
    }

    /**
     * This method will act as a consistency checker
     * @param model
     * @return true -> if consistency is ok or was corrected, false if failure to fix
     */
    @Override
    public boolean isModelConsistent(User model) {
        User modelImported = read(model);
        if(model.equals(modelImported)){
            return true;
        }else{
            update(model);
            vefication(model, modelImported);
            return false;
        }
    }

    /**
     * This method willa act as our
     * @param model
     * @param modelImported
     */
    private void vefication(User model, User modelImported) {
        System.out.println("Inconsistency found!\n\nObject in real database: " +
                "" + model.toString() +
                "\n\nObject found in new storage: " + modelImported.toString());
    }
}
