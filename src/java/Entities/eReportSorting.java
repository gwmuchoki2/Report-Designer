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
@Table(name="smw_ReportSort")
public class eReportSorting implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String ModuleID;
    @Id
    private String FieldName;
    private int SortOrder;
    private String SortType;

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

    public int getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(int SortOrder) {
        this.SortOrder = SortOrder;
    }

    public String getSortType() {
        return SortType;
    }

    public void setSortType(String SortType) {
        this.SortType = SortType;
    }
    
    
    
}
