
package ru.javafx.dolphin.process.monitor.model;

import com.canoo.dolphin.mapping.DolphinBean;
import com.canoo.dolphin.mapping.Property;

@DolphinBean
public class ProcessBean {

    private Property<String> processID;
    private Property<String> name;
    private Property<String> cpuPercentage;
    private Property<String> memoryPercentage;

    public Property<String> processIDProperty() {
        return processID;
    }

    public Property<String> nameProperty() {
        return name;
    }

    public Property<String> cpuPercentageProperty() {
        return cpuPercentage;
    }

    public Property<String> memoryPercentageProperty() {
        return memoryPercentage;
    }

    public void setProcessID(String processID) {
        this.processID.set(processID);
    }

    public void setCpuPercentage(String cpuPercentage) {
        this.cpuPercentage.set(cpuPercentage);
    }

    public void setMemoryPercentage(String memoryPercentage) {
        this.memoryPercentage.set(memoryPercentage);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getProcessID() {
        return processID.get();
    }

    public String getName() {
        return name.get();
    }

    public String getCpuPercentage() {
        return cpuPercentage.get();
    }

    public String getMemoryPercentage() {
        return memoryPercentage.get();
    }
}
