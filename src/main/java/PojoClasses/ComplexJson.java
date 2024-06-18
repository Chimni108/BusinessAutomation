package PojoClasses;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ComplexJson {
    public static void main(String[] args) {
        System.out.println("here i am");
        CreateNewOrder_JSONObject_Req.Sections orderSectionIdObj = new CreateNewOrder_JSONObject_Req.Sections();
        orderSectionIdObj.setOrderSectionId(null);

//quantity object
        CreateNewOrder_JSONObject_Req.Sections.OrderLines.Configs quantity = null;
//Section list object
        List<CreateNewOrder_JSONObject_Req.Sections> sectionsListObj= new ArrayList<CreateNewOrder_JSONObject_Req.Sections>();
//OrderLineListOject
        List<CreateNewOrder_JSONObject_Req.Sections.OrderLines> orderLineListObj= new ArrayList<CreateNewOrder_JSONObject_Req.Sections.OrderLines>();
//ConfigListObject
        List<CreateNewOrder_JSONObject_Req.Sections.OrderLines.Configs> ConfigsListObj = new ArrayList<CreateNewOrder_JSONObject_Req.Sections.OrderLines.Configs>();
//object of inner config list
        List<CreateNewOrder_JSONObject_Req.Sections.OrderLines.Configs.innerconfig> innerconfList = null;//= new List<CreateNewOrder_JSONObject_Req.Sections.OrderLines.Configs.innerconfig>();

//object of inner config class and setting value for each field
        CreateNewOrder_JSONObject_Req.Sections.OrderLines.Configs.innerconfig innerconfparam = new CreateNewOrder_JSONObject_Req.Sections.OrderLines.Configs.innerconfig();
        innerconfparam.setNotes("sms - mo inbound");
        innerconfparam.setPriceStability(1);
        innerconfparam.setProduct("Argentina Free To End User Short Code 2-Way MO");

//setting other param value of OrderLines
        CreateNewOrder_JSONObject_Req.Sections.OrderLines orderLineOBject= new CreateNewOrder_JSONObject_Req.Sections.OrderLines();
        orderLineOBject.setCharges(null);
        orderLineOBject.setSku("SMS.NEW-PRICELIST-MO-INBOUND-LEGACY.NOVA");
        orderLineOBject.setOrderLinestatus("NEW");
//setting value of quantity
        quantity = new CreateNewOrder_JSONObject_Req.Sections.OrderLines.Configs();
        quantity.setQuantity(1);

//adding all child array to parent array
        innerconfList.add(innerconfparam); //adding inner config parameter object to innerConfigList
        ConfigsListObj.add((CreateNewOrder_JSONObject_Req.Sections.OrderLines.Configs) innerconfList);
        ConfigsListObj.add(quantity);
        orderLineListObj.add((CreateNewOrder_JSONObject_Req.Sections.OrderLines) ConfigsListObj);
        orderLineListObj.add(orderLineOBject); //adding orderline object to OrderLineList object
        sectionsListObj.add((CreateNewOrder_JSONObject_Req.Sections) orderLineListObj);
        sectionsListObj.add(orderSectionIdObj);

        System.out.println(" trying to print Section array: " +sectionsListObj);

        CreateNewOrder_JSONObject_Req createOrdObj = new CreateNewOrder_JSONObject_Req();
        createOrdObj.setDisplayName("Nam_First_auto_createOrder");
        createOrdObj.setApprovalStatus("UNDECIDED");
        createOrdObj.setStatus("IN_DRAFT");

    }


}
