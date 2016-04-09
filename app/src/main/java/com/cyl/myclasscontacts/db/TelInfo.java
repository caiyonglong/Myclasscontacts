package com.cyl.myclasscontacts.db;

/**
 * Created by 永龙 on 2015/12/2.
 */
public class TelInfo {
    private Long id;
    private String name;
    private String number;
    private String tel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public TelInfo(Long id, String name, String number,String tel) {
        super();
        this.id = id;
        this.name = name;
        this.number = number;
        this.tel = tel;

    }

    public TelInfo(String name, String number, String tel) {
        super();
        this.name = name;
        this.number = number;
        this.tel = tel;
    }
    public TelInfo(){
        super();
    }

    public String toString(){

        return "[姓名:"+name+"学号："+number+"电话号码"+tel+"]";
    }

}
