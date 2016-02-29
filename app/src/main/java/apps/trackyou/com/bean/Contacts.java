package apps.trackyou.com.bean;

/**
 * Created by abhaym on 9/24/2015.
 */
public class Contacts implements Comparable {
    private String name;
    private String contactNumber;
    private boolean hasTrackyouApp;
    private boolean isAddedToTrack;

    public Contacts(String name, String phNumber) {
        this.name = name;
        this.contactNumber = phNumber;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean isHasTrackyouApp() {
        return hasTrackyouApp;
    }

    public void setHasTrackyouApp(boolean hasTrackyouApp) {
        this.hasTrackyouApp = hasTrackyouApp;
    }

    public boolean isAddedToTrack() {
        return isAddedToTrack;
    }

    public void setIsAddedToTrack(boolean isAddedToTrack) {
        this.isAddedToTrack = isAddedToTrack;
    }


    @Override
    public int compareTo(Object obj) {

        if (obj instanceof Contacts) {
            Contacts another = (Contacts) obj;

            if (this.getName().toLowerCase().compareTo(another.getName().toLowerCase()) > 0) {
                return 1;
            } else if (this.getName().toLowerCase().compareTo(another.getName().toLowerCase()) < 0) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return getName() + getContactNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contacts)) return false;

        Contacts contacts = (Contacts) o;

        if (!this.getName().equalsIgnoreCase(contacts.getName())) return false;
        return getContactNumber().equalsIgnoreCase(contacts.getContactNumber());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getContactNumber().hashCode();
        return result;
    }

}
