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
@Table(name="smw_ReportFields")
public class eReportFields implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String ModuleID;
    @Id
    private String FieldName;
    private String SummaryFunction;
    private int ColumnWidth;
    private int ColumnOrder;
    private String DisplayText;

    public String getDisplayText() {
        return DisplayText;
    }

    public void setDisplayText(String DisplayText) {
        this.DisplayText = DisplayText;
    }
    
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

    public String getSummaryFunction() {
        return SummaryFunction;
    }

    public void setSummaryFunction(String SummaryFunction) {
        this.SummaryFunction = SummaryFunction;
    }

    public int getColumnWidth() {
        return ColumnWidth;
    }

    public void setColumnWidth(int ColumnWidth) {
        this.ColumnWidth = ColumnWidth;
    }

    public int getColumnOrder() {
        return ColumnOrder;
    }

    public void setColumnOrder(int ColumnOrder) {
        this.ColumnOrder = ColumnOrder;
    }
    
}
