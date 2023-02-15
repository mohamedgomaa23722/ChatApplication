package gov.iti.model;

import java.io.Serializable;

import gov.iti.utils.TextStyle;

public class MessageStyle implements Serializable {
    private String font = "Arial";
    private TextStyle textStyle = TextStyle.REGULAR;
    private boolean isUnderLine;
    private String textBackColor = "#F84D43";
    private String textColor = "#ffffff";
    private int textSize = 18;

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public TextStyle getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(TextStyle textStyle) {
        this.textStyle = textStyle;
    }

    public boolean isUnderLine() {
        return isUnderLine;
    }

    public void setUnderLine(boolean isUnderLine) {
        this.isUnderLine = isUnderLine;
    }

    public String getTextBackColor() {
        return textBackColor;
    }

    public void setTextBackColor(String textBackColor) {
        this.textBackColor = textBackColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

}
