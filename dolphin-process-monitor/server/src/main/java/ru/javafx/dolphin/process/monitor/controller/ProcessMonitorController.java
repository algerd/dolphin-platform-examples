
package ru.javafx.dolphin.process.monitor.controller;

import com.canoo.dolphin.BeanManager;
import com.canoo.dolphin.server.DolphinController;
import com.canoo.dolphin.server.DolphinModel;
import com.canoo.dolphin.server.DolphinSession;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javafx.dolphin.process.monitor.AsyncServerRunner;
import static ru.javafx.dolphin.process.monitor.Constants.CONTROLLER_NAME;
import ru.javafx.dolphin.process.monitor.model.ProcessBean;
import ru.javafx.dolphin.process.monitor.model.ProcessListBean;

// ClientSessionExecutor is in new version
import com.canoo.dolphin.server.ClientSessionExecutor;
//import com.canoo.dolphin.server.BackgroundRunner;

@DolphinController(CONTROLLER_NAME)
public class ProcessMonitorController {

    private static DecimalFormat format = new DecimalFormat("0.00");

    @Autowired
    private BeanManager beanManager;

    @Autowired
    private DolphinSession session;

    private ClientSessionExecutor sessionExecutor;

    @Autowired
    private AsyncServerRunner asyncServerRunner;

    @DolphinModel
    private ProcessListBean processList;
    
    private Future<Void> executor;
    private OperatingSystem os;
    private GlobalMemory memory;

    @PostConstruct
    public void onInit() {
        SystemInfo si = new SystemInfo();
        os = si.getOperatingSystem();
        memory = si.getHardware().getMemory();
        update();
    }

    @PreDestroy
    public void onDestroy() {
        Optional.ofNullable(executor).ifPresent(e -> e.cancel(true));
    }


    private void update() {
        List<OSProcess> procs = Arrays.asList(os.getProcesses(10, OperatingSystem.ProcessSort.CPU));
        for (OSProcess process : procs) {
            ProcessBean bean = null;
            if(processList.getItems().size() <= procs.indexOf(process)) {
                bean = beanManager.create(ProcessBean.class);
                processList.getItems().add(bean);
            } else {
                bean = processList.getItems().get(procs.indexOf(process));
            }
            bean.setProcessID(new Integer(process.getProcessID()).toString());
            bean.setCpuPercentage(format.format(100d * (process.getKernelTime() + process.getUserTime()) / process.getUpTime()));
            bean.setMemoryPercentage(format.format(100d * process.getResidentSetSize() / memory.getTotal()));
            bean.setName(process.getName());
        }
        asyncServerRunner.execute(new Runnable() {
            @Override
            public void run() {
                sessionExecutor.runLaterInClientSession(() -> update());
            }
        });
    }

}
