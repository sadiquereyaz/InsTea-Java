package in.instea.instea;

public class ModelClassRec {
    String icon;
    String title;       // reolace imageLink
    String nlink;
    String pylink;
    String pllink;
    String subject;
    String type;
    String venue;
    String teacher;
    String from;
    String to;
    String sub;
    String publishDate;
    String subtitle;
    String credit;
    String shortSub;
    String subtitle1, subtitle2;

    public ModelClassRec() {
    }

    public ModelClassRec(String icon, String title, String nlink, String pylink, String pllink, String subject, String type, String venue, String teacher, String from, String to, String sub, String publishDate, String subtitle, String credit, String shortSub) {
        this.icon = icon;
        this.title = title;
        this.nlink = nlink;
        this.pylink = pylink;
        this.pllink = pllink;
        this.subject = subject;
        this.type = type;
        this.venue = venue;
        this.teacher = teacher;
        this.from = from;
        this.to = to;
        this.sub = sub;
        this.publishDate = publishDate;
        this.subtitle = subtitle;
        this.subtitle1 = subtitle1;
        this.subtitle2 = subtitle2;
        this.credit = credit;
        this.shortSub = shortSub;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNlink() {
        return nlink;
    }

    public void setNlink(String nlink) {
        this.nlink = nlink;
    }

    public String getPylink() {
        return pylink;
    }

    public void setPylink(String pylink) {
        this.pylink = pylink;
    }

    public String getPllink() {
        return pllink;
    }

    public void setPllink(String pllink) {
        this.pllink = pllink;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type+" ";
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle1() {
        return subtitle1;
    }

    public void setSubtitle1(String subtitle1) {
        this.subtitle1 = subtitle1;
    }
    public String getSubtitle2() {
        return subtitle2;
    }
    public void setSubtitle2(String subtitle2) {
        this.subtitle2 = subtitle2;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getShortSub() {
        return shortSub;
    }

    public void setShortSub(String shortSub) {
        this.shortSub = shortSub;
    }
}
