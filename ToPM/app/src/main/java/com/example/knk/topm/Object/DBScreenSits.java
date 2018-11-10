package com.example.knk.topm.Object;

public class DBScreenSits {

    public int DBScreenHall;

    public int DBButtonID;
    public int DBrow;
    public int DBcol;

    public boolean isAbled;
    public boolean isBooked;
    public boolean isSpecial;



    public DBScreenSits(){
    }

    public DBScreenSits(int DBScreenHall,int DBButtonID,int DBrow,int DBcol,
                        boolean isAbled,boolean isBooked,boolean isSpecial){
        this.DBButtonID=DBScreenHall;
        this.DBButtonID=DBButtonID;
        this.DBrow=DBrow;
        this.DBcol=DBcol;
        this.isAbled=isAbled;
        this.isBooked=isBooked;
        this.isSpecial=isSpecial;

    }


}
