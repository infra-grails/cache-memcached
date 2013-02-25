package grails.plugin.cachememcached;

/**
 * @author : prostohz
 * @since : 2/25/13 5:23 PM
 */
public class MemcachedStatistics {
    private int cmdGet;
    private int cmdSet;
    private int cmdEvct;

    public void incCmdGet() {
        cmdGet++;
    }

    public void incCmdSet() {
        cmdSet++;
    }

    public void incCmdEvct() {
        cmdEvct++;
    }

    public int getCmdGet() { return cmdGet; }

    public void setCmdGet(int cmdGet) { this.cmdGet = cmdGet; }

    public int getCmdSet() { return cmdSet; }

    public void setCmdSet(int cmdSet) { this.cmdSet = cmdSet; }

    public int getCmdEvct() { return cmdEvct; }

    public void setCmdEvct(int cmdEvct) { this.cmdEvct = cmdEvct; }

}
