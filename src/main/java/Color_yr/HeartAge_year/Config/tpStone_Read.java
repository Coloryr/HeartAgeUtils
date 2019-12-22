package Color_yr.HeartAge_year.Config;

import Color_yr.HeartAge_year.Obj.tpStone_save_Obj;
import Color_yr.HeartAge_year.HeartAge_year;
import Color_yr.HeartAge_year.tpStone.tpStone_do;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class tpStone_Read {
    private static File File_local;

    public void init() {
        //文件初始化
        File_local = new File(HeartAge_year.plugin.getDataFolder().getParent() + "/HeartAge_year/tpStone");
        try {
            if (!File_local.exists())
                File_local.mkdirs();
            File[] file_list = File_local.listFiles((dir, name) -> name.endsWith(".json"));
            if (file_list != null) {
                InputStreamReader reader;
                BufferedReader bfreader;
                for (File temp : file_list) {
                    Gson read_gson = new Gson();
                    reader = new InputStreamReader(new FileInputStream(temp), StandardCharsets.UTF_8);
                    bfreader = new BufferedReader(reader);
                    tpStone_save_Obj obj = read_gson.fromJson(bfreader, tpStone_save_Obj.class);
                    tpStone_do.toStone_save.clear();
                    if (obj != null)
                        tpStone_do.toStone_save.put(temp.getName().replace(".json", ""), obj);
                    reader.close();
                    bfreader.close();
                }
            }
        } catch (Exception e) {
            HeartAge_year.log.warning("§d[HeartAge_year]§c传送石配置文件初始化失败" + e);
        }
    }

    public void save(tpStone_save_Obj stone, String uuid) {
        //保存传送石储存
        try {
            File save_file = new File(File_local, uuid + ".json");
            if (!save_file.exists())
                save_file.createNewFile();
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(save_file), StandardCharsets.UTF_8);
            write.write(new Gson().toJson(stone));
            write.close();
        } catch (Exception e) {
            HeartAge_year.log.warning("§d[HeartAge_year]§c传送石内容保存失败" + e);
        }
    }
}