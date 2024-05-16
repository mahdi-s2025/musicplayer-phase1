package model;

public class Report {
    private UserAccount reporter;
    private Artist reportedArtist;
    private String reportMassage;

    public Report(UserAccount reporter, Artist reportedArtist, String reportMassage) {
        this.reporter = reporter;
        this.reportedArtist = reportedArtist;
        this.reportMassage = reportMassage;
    }

    public UserAccount getReporter() {
        return reporter;
    }

    public void setReporter(UserAccount reporter) {
        this.reporter = reporter;
    }

    public Artist getReportedArtist() {
        return reportedArtist;
    }

    public void setReportedArtist(Artist reportedArtist) {
        this.reportedArtist = reportedArtist;
    }

    public String getReportMassage() {
        return reportMassage;
    }

    public void setReportMassage(String reportMassage) {
        this.reportMassage = reportMassage;
    }
}
