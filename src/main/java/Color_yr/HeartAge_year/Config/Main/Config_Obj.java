package Color_yr.HeartAge_year.Config.Main;

public class Config_Obj {

    private String vision;
    private tpStone tpStone;

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public tpStone getItem() {
        return tpStone;
    }

    public void setItem(tpStone item) {
        this.tpStone = tpStone;
    }
}

class tpStone {

    private String item;
    private String updata;

    public String getHand() {
        return item;
    }

    public void setHand(String hand) {
        this.item = hand;
    }

    public String getUpdata() {
        return updata;
    }

    public void setUpdata(String updata) {
        this.updata = updata;
    }

}
