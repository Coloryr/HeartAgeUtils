package Color_yr.HeartAge_year.Config.tpStone;

import Color_yr.HeartAge_year.Config.tpStone.Obj.tpStone;
import Color_yr.HeartAge_year.HeartAge_year;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class tpStone_Read {
    private static File FileName;

    public void init() {
        //文件初始化
        FileName = new File(HeartAge_year.plugin.getDataFolder().getParent() + "/HeartAge_year/toStone");
        try {
            if (!HeartAge_year.plugin.getDataFolder().exists())
                HeartAge_year.plugin.getDataFolder().mkdirs();
            File[] file_list = FileName.listFiles((dir, name) -> name.endsWith(".json"));
            if (file_list != null) {
                InputStreamReader reader;
                BufferedReader bfreader;
                for (File temp : file_list) {
                    Gson read_gson = new Gson();
                    reader = new InputStreamReader(new FileInputStream(temp), StandardCharsets.UTF_8);
                    bfreader = new BufferedReader(reader);
                    tpStone obj = read_gson.fromJson(bfreader, tpStone.class);
                    if (obj != null)
                        tpStone_do.toStone_save.put(temp.getName().replace(".json", ""), obj);
                }
            }
        } catch (Exception e) {
            HeartAge_year.log.warning("§d[HeartAge_year]§c传送石配置文件初始化失败" + e);
        }
    }

    public void save(tpStone stone, String uuid) {
        //保存传送石储存
        try {
            File save_file = new File(FileName.getParent() + "/" + uuid + ".json");
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