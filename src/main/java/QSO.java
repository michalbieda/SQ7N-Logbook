import java.util.Date;
import java.util.Locale;

public class QSO {
    private String callSign;        // station callsign
    private String qsoDate;         // date of radio contact
    private String qsoTime;         // time of radio contact
    private double qsoFrequency;    // frequency of radio contact
    private String qsoMode;         // mode of radio contact

    public QSO(String callSign, String qsoDate, String qsoTime, double qsoFrequency, String qsoMode) {
        this.callSign = callSign.toUpperCase(Locale.ROOT);
        this.qsoDate = qsoDate;
        this.qsoTime = qsoTime;
        this.qsoFrequency = qsoFrequency;
        this.qsoMode = qsoMode.toUpperCase(Locale.ROOT);
    }

    public String getCallSign() {
        return callSign;
    }

    public String getQsoDate() {
        return qsoDate;
    }

    public String getQsoTime() {
        return qsoTime;
    }

    public double getQsoFrequency() {
        return qsoFrequency;
    }

    public String getQsoMode() {
        return qsoMode;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign.toUpperCase(Locale.ROOT);
    }

    public void setQsoDate(String qsoDate) {
        this.qsoDate = qsoDate;
    }

    public void setQsoTime(String qsoTime) {
        this.qsoTime = qsoTime;
    }

    public void setQsoFrequency(double qsoFrequency) {
        this.qsoFrequency = qsoFrequency;
    }

    public void setQsoMode(String qsoMode) {
        this.qsoMode = qsoMode.toUpperCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return "callSign: " + callSign + " | qsoDate: " + qsoDate + " | qsoTime: " + qsoTime + " | qsoFrequency: " + qsoFrequency + " | qsoMode: " + qsoMode;
    }
}
