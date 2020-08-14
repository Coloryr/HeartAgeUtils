package Color_yr.HeartAgeUtils.Config;

import Color_yr.HeartAgeUtils.HeartAgeUtils;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class saveTaskDo {
    private final List<saveTaskObj> Tasks = new ArrayList<>();
    private boolean isSave;
    private ScheduledExecutorService service;

    public saveTaskDo() {
        service = Executors.newSingleThreadScheduledExecutor();
        Runnable runnable = () -> {
            try {
                save();
            } catch (Exception e) {
                HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c数据保存失败");
                e.printStackTrace();
            }
        };
        service.scheduleAtFixedRate(runnable, 0, 2, TimeUnit.MINUTES);
    }

    public void closeTimer() {
        save();
        if (service != null) {
            service.shutdown();
            service = null;
        }
    }

    public void addTask(saveTaskObj save) {
        while (isSave) ;
        for (saveTaskObj item : Tasks) {
            if (item.getFile().equals(save.getFile())) {
                Tasks.remove(item);
                break;
            }
        }
        Tasks.add(save);
    }

    public void save() {
        isSave = true;
        if (Tasks.size() != 0) {
            for (saveTaskObj save : Tasks) {
                try {
                    if (!save.getFile().exists())
                        save.getFile().createNewFile();
                    OutputStreamWriter write = new OutputStreamWriter(
                            new FileOutputStream(save.getFile()), StandardCharsets.UTF_8);
                    write.write(new Gson().toJson(save.getSaveobj()));
                    write.close();
                } catch (Exception e) {
                    HeartAgeUtils.log.warning("§d[HeartAgeUtils]§c数据保存失败");
                    e.printStackTrace();
                }
            }
        }
        isSave = false;
    }
}
