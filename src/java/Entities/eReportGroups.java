/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author gerald
 */
@Entity
@Table(name="smw_ReportGroups")
public class eReportGroups implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String ModuleID;
    @Id
    private String FieldName;
    private String GroupingSortFunction;//A
    private int GroupOrder;

    public String getModuleID() {
        return ModuleID;
    }

    public void setModuleID(String ModuleID) {
        this.ModuleID = ModuleID;
    }

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String FieldName) {
        this.FieldName = FieldName;
    }

    public String getGroupingSortFunction() {
        return GroupingSortFunction;
    }

    public void setGroupingSortFunction(String GroupingSortFunction) {
        this.GroupingSortFunction = GroupingSortFunction;
    }

  

    public int getGroupOrder() {
        return GroupOrder;
    }

    public void setGroupOrder(int GroupOrder) {
        this.GroupOrder = GroupOrder;
    }
    
    
}
