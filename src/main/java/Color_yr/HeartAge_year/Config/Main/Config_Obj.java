package Color_yr.HeartAge_year.Config.Main;

public class Config_Obj {

    private String vision;
    private Item item;
    public void setVision(String vision) {
        this.vision = vision;
    }
    public String getVision() {
        return vision;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    public Item getItem() {
        return item;
    }
}
class Item {

    private String hand;
    private String updata;
    public void setHand(String hand) {
        this.hand = hand;
    }
    public String getHand() {
        return hand;
    }

    public void setUpdata(String updata) {
        this.updata = updata;
    }
    public String getUpdata() {
        return updata;
    }

}
