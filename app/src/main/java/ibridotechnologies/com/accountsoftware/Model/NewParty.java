package ibridotechnologies.com.accountsoftware.Model;

/**
 * Created by sushil on 17/08/18.
 */

public class NewParty {
    private static String party_name,party_address,state_id,district_id,party_mobile,party_email,party_telephone;
    private static String districtListURL = "http://www.acmecreations.co.in/api/AcmeQuotation/GetDistrictByStateId/";


    public static String getDistrictListURL() {
        return districtListURL;
    }

    public String getParty_name() {
        return party_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }

    public String getParty_address() {
        return party_address;
    }

    public void setParty_address(String party_address) {
        this.party_address = party_address;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getParty_mobile() {
        return party_mobile;
    }

    public void setParty_mobile(String party_mobile) {
        this.party_mobile = party_mobile;
    }

    public String getParty_email() {
        return party_email;
    }

    public void setParty_email(String party_email) {
        this.party_email = party_email;
    }

    public String getParty_telephone() {
        return party_telephone;
    }

    public void setParty_telephone(String party_telephone) {
        this.party_telephone = party_telephone;
    }
}
