public class Process {
    private int pid;
    private String name;
    private int arrivalTime;
    private int priority;
    private int burstTime;
    private int quantum;
    private int currentBurstStart;
    private int currentBurstDuration;
    private int waitingTime;
    private int agFactor;
    private int lastRunTime;
    private boolean dead;
    private boolean arrived;

    public Process(int pid, String name, int arrivalTime, int priority, int burstTime, int quantum) {
        this.pid = pid;
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.burstTime = burstTime;
        this.quantum = quantum;
        this.currentBurstStart = 0;
        this.currentBurstDuration = 0;
        this.waitingTime = 0;
        this.agFactor = -1;
        this.lastRunTime = 0;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void decrementBurstTime() {
        this.burstTime--;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getCurrentBurstStart() {
        return currentBurstStart;
    }

    public void setCurrentBurstStart(int currentBurstStart) {
        this.currentBurstStart = currentBurstStart;
    }

    public int getCurrentBurstDuration() {
        return currentBurstDuration;
    }

    public void resetCurrentBurstDuration() {
        this.currentBurstDuration = 0;
    }

    public void incrementCurrentBurstDuration() {
        this.currentBurstDuration++;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getAgFactor() {
        return agFactor;
    }

    public void setAgFactor(int agFactor) {
        this.agFactor = agFactor;
    }

    public int getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(int lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }
}
