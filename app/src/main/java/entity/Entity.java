package entity;

import java.io.Serializable;

/**
 * Created by Jack on 2015/6/2.
 */
public class Entity implements Serializable {

    private int id;
    private int msgcode;

    public int getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(int msgcode) {
        this.msgcode = msgcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", msgcode=" + msgcode +
                '}';
    }
}
