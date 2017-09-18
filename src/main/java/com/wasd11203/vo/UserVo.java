package com.wasd11203.vo;

/**
 * @author AA
 * @date 2017-09-15 17:01
 */
public class UserVo {

    private String id;
    private String name;

    public UserVo() {
    }

    public UserVo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

