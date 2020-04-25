package Color_yr.HeartAgeUtils.Config;

import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.tpStoneSaveObj;
import Color_yr.HeartAgeUtils.tpStone.tpStoneDo;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class tpStoneRead {
    private static File File_local;

    public void init() {
        //文件初始化
        File_local = new File(HeartAgeUtils.plugin.getDataFolder().getParent() + "/HeartAgeUtils/tpStone");
        try {
            if (!File_local.exists())
                File_local.mkdirs();
            File[] file_list = File_local.listFiles((dir, name) -> name.endsWith(".json"));
            if (file_list != null) {
                InputStreamReader reader;
                BufferedReader bfreader;
                for (File temp : file_list) {
                    Gson json = new Gson();
                    reader = new InputStreamReader(new FileInputStream(temp), StandardCharsets.UTF_8);
                    bfreader = new BufferedReader(reader);
                    tpStoneSaveObj obj = json.fromJson(bfreader, tpStoneSaveObj.class);
                    tpStoneDo.toStoneSave.clear();
                    if (obj != null)
                        tpStoneDo.toStoneSave.put(temp.getName().replace(".json", ""), obj);
                    reader.close();
                    bfreader.close();
                }
            }
        } catch (Exception e) {
            HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c传送石配置文件初始化失败");
            e.printStackTrace();
        }
    }

    public void save(tpStoneSaveObj stone, String uuid) {
        //保存传送石储存
        try {
            File save_file = new File(File_local, uuid + ".json");
            if (!save_file.exists())
                save_file.createNewFile();
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(save_file), StandardCharsets.UTF_8);
            write.write(new Gson().toJson(stone));
            write.close();
        } catch (Exception e) {
            HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c传送石内容保存失败");
            e.printStackTrace();
        }
    }
}