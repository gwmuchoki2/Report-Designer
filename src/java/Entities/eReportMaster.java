/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author gerald
 */
@Entity
@Table(name="smw_ReportMaster")
public class eReportMaster implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private long ReportID;
    
    private String ModuleID;
    private String ReportName;
    private String ReportType;
    private String FontName;
    private String HeaderFontColor;
    private String DetailFontColor;
    private String DataSourceName;
    private String PaperType;
    private String Orientation;
    private int GroupFontSize;
    private int HeaderFontSize;
    private int DetailFontSize;
    private boolean HasLogo;
    private String LogoPath;
    private String CreatedBy;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date CreatedOn;
    private String ModifiedBy;
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date ModifiedOn;
    private boolean IsActive;   

    public String getModuleID() {
        return ModuleID;
    }

    public void setModuleID(String ModuleID) {
        this.ModuleID = ModuleID;
    }

    public long getReportID() {
        return ReportID;
    }

    public void setReportID(long ReportID) {
        this.ReportID = ReportID;
    }
 

    public String getReportName() {
        return ReportName;
    }

    public void setReportName(String ReportName) {
        this.ReportName = ReportName;
    }

    public String getReportType() {
        return ReportType;
    }

    public void setReportType(String ReportType) {
        this.ReportType = ReportType;
    }

    public String getFontName() {
        return FontName;
    }

    public void setFontName(String FontName) {
        this.FontName = FontName;
    }

    public String getHeaderFontColor() {
        return HeaderFontColor;
    }

    public void setHeaderFontColor(String HeaderFontColor) {
        this.HeaderFontColor = HeaderFontColor;
    }

    public String getDetailFontColor() {
        return DetailFontColor;
    }

    public void setDetailFontColor(String DetailFontColor) {
        this.DetailFontColor = DetailFontColor;
    }

    public String getDataSourceName() {
        return DataSourceName;
    }

    public void setDataSourceName(String DataSourceName) {
        this.DataSourceName = DataSourceName;
    }

    public String getPaperType() {
        return PaperType;
    }

    public void setPaperType(String PaperType) {
        this.PaperType = PaperType;
    }

    public String getOrientation() {
        return Orientation;
    }

    public void setOrientation(String Orientation) {
        this.Orientation = Orientation;
    }

    public int getGroupFontSize() {
        return GroupFontSize;
    }

    public void setGroupFontSize(int GroupFontSize) {
        this.GroupFontSize = GroupFontSize;
    }

    public int getHeaderFontSize() {
        return HeaderFontSize;
    }

    public void setHeaderFontSize(int HeaderFontSize) {
        this.HeaderFontSize = HeaderFontSize;
    }

    public int getDetailFontSize() {
        return DetailFontSize;
    }

    public void setDetailFontSize(int DetailFontSize) {
        this.DetailFontSize = DetailFontSize;
    }

    public boolean isHasLogo() {
        return HasLogo;
    }

    public void setHasLogo(boolean HasLogo) {
        this.HasLogo = HasLogo;
    }

    public String getLogoPath() {
        return LogoPath;
    }

    public void setLogoPath(String LogoPath) {
        this.LogoPath = LogoPath;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Date CreatedOn) {
        this.CreatedOn = CreatedOn;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }

    public Date getModifiedOn() {
        return ModifiedOn;
    }

    public void setModifiedOn(Date ModifiedOn) {
        this.ModifiedOn = ModifiedOn;
    }

    public boolean isIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean IsActive) {
        this.IsActive = IsActive;
    }
    
    
}
