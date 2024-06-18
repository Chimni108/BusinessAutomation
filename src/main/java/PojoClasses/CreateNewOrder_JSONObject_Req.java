package PojoClasses;

import java.util.List;

public class CreateNewOrder_JSONObject_Req {
    private List<Sections> section;
    private List<Seller> seller;
    private List<Buyer> buyer;
    private List<Commercial> commercial;
    private String displayName;
    private String approvalStatus;
    private String status;

    public List<Sections> getSection() {
        return section;
    }

    public void setSection(List<Sections> section) {
        this.section = section;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Sections {
        private String orderSectionId;
        private List<OrderLines> orderLines;

        public List<OrderLines> getOrderLines() {
            return orderLines;
        }

        public void setOrderLines(List<OrderLines> orderLines) {
            this.orderLines = orderLines;
        }

        public String getOrderSectionId() {
            return orderSectionId;
        }

        public void setOrderSectionId(String orderSectionId) {
            this.orderSectionId = orderSectionId;
        }

        public static class OrderLines {
        private List<Configs> configs;
        private String sku;
        private String OrderLinestatus;
        private int[] charges;

            public List<Configs> getConfigs() {
                return configs;
            }

            public void setConfigs(List<Configs> configs) {
                this.configs = configs;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public String getOrderLinestatus() {
                return OrderLinestatus;
            }

            public void setOrderLinestatus(String orderLinestatus) {
                OrderLinestatus = orderLinestatus;
            }

            public int[] getCharges() {
                return charges;
            }

            public void setCharges(int[] charges) {
                this.charges = charges;
            }


            //============Congfigs class ==================
            public static class Configs{
                private List<innerconfig> innerconfig;
                private int quantity;

                public List<Configs.innerconfig> getInnerconfig() {
                    return innerconfig;
                }

                public void setInnerconfig(List<Configs.innerconfig> innerconfig) {
                    this.innerconfig = innerconfig;
                }

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public static class innerconfig {
                    private String notes;
                    private int priceStability;
                    private String product;

                    public String getNotes() {
                        return notes;
                    }

                    public void setNotes(String notes) {
                        this.notes = notes;
                    }

                    public int getPriceStability() {
                        return priceStability;
                    }

                    public void setPriceStability(int priceStability) {
                        this.priceStability = priceStability;
                    }

                    public String getProduct() {
                        return product;
                    }

                    public void setProduct(String product) {
                        this.product = product;
                    }
                }
            }
        }
    }

    public static class Seller {
    }

    public static class Buyer {
    }

    public static class Commercial {

    }


}
