package msh.myonlineshop.models;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

import msh.myonlineshop.enums.UserRole;

public class User implements Serializable {
    public static String key_id = "id";
    public static String key_firstname = "first_name";
    public static String key_lastname = "last_name";
    public static String key_username = "username";
    public static String key_token = "token";
    public static String key_customerId = "customer_id";

    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String newPassword;
    private String email;
    private UserRole role;
    private boolean enable;
    private String token;
    private long customerId;
    private Customer customer;

    public User() {
    }

    public User(Cursor cursor)
    {
        setId(cursor.getLong(0));
        setCustomerId(cursor.getLong(1));
        setUsername(cursor.getString(2));
        setFirstName(cursor.getString(3));
        setLastName(cursor.getString(4));
        setToken(cursor.getString(5));
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(User.key_id, getId());
        values.put(User.key_customerId, getCustomerId());
        values.put(User.key_firstname, getFirstName());
        values.put(User.key_lastname, getLastName());
        values.put(User.key_username, getUsername());
        values.put(User.key_token, getToken());
        return values;
    }

}
