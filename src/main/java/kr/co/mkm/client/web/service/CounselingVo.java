package kr.co.mkm.client.web.service;

public class CounselingVo {
    private String company;
    private String name;
    private String phone;
    private String email;
    private String email1;
    private String email2;
    private String[] product;
    private String productStr;
    private String content;
    private boolean agree;
    private String agreeYN;

    public String getAgreeYN() {
        return agreeYN;
    }

    public void setAgreeYN(String agreeYN) {
        this.agreeYN = agreeYN;
    }

    public String getProductStr() {
        return productStr;
    }

    public void setProductStr(String productStr) {
        this.productStr = productStr;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getProduct() {
        return product;
    }

    public void setProduct(String[] product) {
        this.product = product;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }
}
