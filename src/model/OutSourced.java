package model;

/**
 *
 * @author Tyler Sanders
 */
public class OutSourced extends Part {

    private String companyName;

    public OutSourced(int id, String name, int stock, double price, int min, int max, String companyName) {
        super(id, name, stock, price, min, max);
        this.companyName = companyName;
    }
    /**
     * @return the company name
     */
    public String getCompanyName() {
        return  companyName;
    }
    /**
     * @param companyName sets the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
